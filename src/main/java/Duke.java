import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.*;
import java.io.*;
import java.util.List;

public class Duke {
    static final Path SAVE_PATH = Paths.get("data", "duke.txt");

    public static void main(String[] args) {
        String logo =   "     Hello! I'm Rama2\n" +
                        "     What can I do for you?\n";
        String end =    "     Bye. Hope to see you again soon!\n";
        String m = "    ____________________________________________________________";

        ArrayList<String> al = new ArrayList<>();
        ArrayList<Boolean> al2 = new ArrayList<>();
        ArrayList<Character> typ = new ArrayList<>();
        ArrayList<String> det = new ArrayList<>();

        loadFromDisk(al, al2, typ, det);

        Scanner s = new Scanner(System.in);
        System.out.println(m + "\n" + logo + m);
        while (true) {
            String in = s.nextLine();
            System.out.println(m);

            if (in.equals("bye")) {
                System.out.println(end);
                break;

            } else if (in.equals("list")) {
                System.out.println("     Here are the tasks in your list:");
                for (int i = 0; i < al.size(); i++) {
                    System.out.printf("     %d.[%c][%s] %s%s\n",
                            i + 1, typ.get(i), al2.get(i) ? "X" : " ", al.get(i), det.get(i));
                }

            } else if (in.startsWith("mark ")) {
                try {
                    int n = Integer.parseInt(in.split(" ")[1]) - 1;
                    al2.set(n, true);
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.printf("       [%c][X] %s%s\n", typ.get(n), al.get(n), det.get(n));
                    saveAll(al, al2, typ, det);
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to mark.");
                }

            } else if (in.startsWith("unmark ")) {
                try {
                    int n = Integer.parseInt(in.split(" ")[1]) - 1;
                    al2.set(n, false);
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.printf("       [%c][ ] %s%s\n", typ.get(n), al.get(n), det.get(n));
                    saveAll(al, al2, typ, det);
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to unmark.");
                }

            } else if (in.startsWith("delete ")) {
                try {
                    int n = Integer.parseInt(in.split(" ")[1]) - 1;
                    System.out.println("     Noted. I've removed this task:");
                    System.out.printf("       [%c][%s] %s%s\n",
                            typ.get(n), al2.get(n) ? "X" : " ", al.get(n), det.get(n));
                    al.remove(n); al2.remove(n); typ.remove(n); det.remove(n);
                    System.out.printf("     Now you have %d tasks in the list.\n", al.size());
                    saveAll(al, al2, typ, det);
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to delete.");
                }

            } else if (in.startsWith("todo")) {
                String desc = in.length() >= 5 ? in.substring(5).trim() : "";
                if (desc.isEmpty()) {
                    System.out.println("     OOPS!!! The description of a todo cannot be empty.");
                } else {
                    al.add(desc); al2.add(false); typ.add('T'); det.add("");
                    System.out.println("     Got it. I've added this task:");
                    System.out.printf("       [T][ ] %s\n", desc);
                    System.out.printf("     Now you have %d tasks in the list.\n", al.size());
                    saveAll(al, al2, typ, det);
                }

            } else if (in.startsWith("deadline")) {
                String rest = in.length() >= 9 ? in.substring(9) : "";
                int idx = rest.indexOf(" /by ");
                if (rest.trim().isEmpty() || idx == -1) {
                    System.out.println("     OOPS!!! Use: deadline <desc> /by <when>");
                } else {
                    String desc = rest.substring(0, idx).trim();
                    String by = rest.substring(idx + 5).trim();
                    if (desc.isEmpty() || by.isEmpty()) {
                        System.out.println("     OOPS!!! Deadline needs both description and /by.");
                    } else {
                        al.add(desc); al2.add(false); typ.add('D');
                        det.add(" (by: " + by + ")");
                        System.out.println("     Got it. I've added this task:");
                        System.out.printf("       [D][ ] %s (by: %s)\n", desc, by);
                        System.out.printf("     Now you have %d tasks in the list.\n", al.size());
                        saveAll(al, al2, typ, det);
                    }
                }

            } else if (in.startsWith("event")) {
                String rest = in.length() >= 6 ? in.substring(6) : "";
                int fromIdx = rest.indexOf(" /from ");
                int toIdx = rest.indexOf(" /to ");
                if (rest.trim().isEmpty() || fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx) {
                    System.out.println("     OOPS!!! Use: event <desc> /from <start> /to <end>");
                } else {
                    String desc = rest.substring(0, fromIdx).trim();
                    String from = rest.substring(fromIdx + 7, toIdx).trim();
                    String to = rest.substring(toIdx + 5).trim();
                    if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        System.out.println("     OOPS!!! Event needs description, /from and /to.");
                    } else {
                        al.add(desc); al2.add(false); typ.add('E');
                        String d = " (from: " + from + " to: " + to + ")";
                        det.add(d);
                        System.out.println("     Got it. I've added this task:");
                        System.out.printf("       [E][ ] %s%s\n", desc, d);
                        System.out.printf("     Now you have %d tasks in the list.\n", al.size());
                        saveAll(al, al2, typ, det);
                    }
                }

            } else {
                System.out.println("     OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

            System.out.println(m);
        }
    }

    static void loadFromDisk(ArrayList<String> al, ArrayList<Boolean> al2,
                             ArrayList<Character> typ, ArrayList<String> det) {
        try {
            if (!Files.exists(SAVE_PATH)) return;
            List<String> lines = Files.readAllLines(SAVE_PATH);
            for (String ln : lines) {
                String[] parts = ln.split("\\|", -1);
                //for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
                if (parts.length < 3) continue; // skip bad lines (basic corruption handling)
                char t = parts[0].charAt(0);
                boolean done = "1".equals(parts[1]);
                String desc = parts[2];
                String extra = parts.length >= 4 ? parts[3] : "";
                typ.add(t);
                al2.add(done);
                al.add(desc);
                det.add(extra.isEmpty() ? "" : (t == 'D' ? " (by: " + extra + ")"
                                                         : t == 'E' ? extra : ""));
            }
        } catch (IOException e) {
            // ignore loading errors per minimal requirement
        }
    }

    static void saveAll(ArrayList<String> al, ArrayList<Boolean> al2,
                        ArrayList<Character> typ, ArrayList<String> det) {
        try {
            if (SAVE_PATH.getParent() != null) Files.createDirectories(SAVE_PATH.getParent());
            try (BufferedWriter w = Files.newBufferedWriter(SAVE_PATH)) {
                for (int i = 0; i < al.size(); i++) {
                    String extra;
                    if (typ.get(i) == 'D') {
                        String d = det.get(i);
                        extra = d.startsWith(" (by: ") ? d.substring(6, d.length() - 1) : d;
                    } else if (typ.get(i) == 'E') {
                        extra = det.get(i);
                    } else {
                        extra = "";
                    }
                    String line = typ.get(i) + "|" + (al2.get(i) ? "1" : "0") + "|" + al.get(i);
                    if (!extra.isEmpty()) line += "|" + extra;
                    w.write(line);
                    w.newLine();
                }
            }
        } catch (IOException e) {
            // swallow per minimal requirement (could print a warning if you want)
        }
    }
}
