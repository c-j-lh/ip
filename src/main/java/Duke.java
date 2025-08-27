import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.*;
import java.io.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Duke {
    static final Path SAVE_PATH = Paths.get("data", "duke.txt");
    static final DateTimeFormatter OUT = DateTimeFormatter.ofPattern("MMM d yyyy");

    public static void main(String[] args) {
        String logo =   "     Hello! I'm Rama2\n" +
                        "     What can I do for you?\n";
        String end =    "     Bye. Hope to see you again soon!\n";
        String m = "    ____________________________________________________________";

        ArrayList<String> al = new ArrayList<>();
        ArrayList<Boolean> al2 = new ArrayList<>();
        ArrayList<Character> typ = new ArrayList<>();
        ArrayList<String> det = new ArrayList<>();
        ArrayList<LocalDate> due = new ArrayList<>(); // only for 'D'; null otherwise

        loadFromDisk(al, al2, typ, det, due);

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
                    String extra = renderExtra(typ.get(i), det.get(i), due.get(i));
                    System.out.printf("     %d.[%c][%s] %s%s\n",
                            i + 1, typ.get(i), al2.get(i) ? "X" : " ", al.get(i), extra);
                }

            } else if (in.startsWith("mark ")) {
                try {
                    int n = Integer.parseInt(in.split(" ")[1]) - 1;
                    al2.set(n, true);
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.printf("       [%c][X] %s%s\n", typ.get(n), al.get(n), renderExtra(typ.get(n), det.get(n), due.get(n)));
                    saveAll(al, al2, typ, det, due);
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to mark.");
                }

            } else if (in.startsWith("unmark ")) {
                try {
                    int n = Integer.parseInt(in.split(" ")[1]) - 1;
                    al2.set(n, false);
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.printf("       [%c][ ] %s%s\n", typ.get(n), al.get(n), renderExtra(typ.get(n), det.get(n), due.get(n)));
                    saveAll(al, al2, typ, det, due);
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to unmark.");
                }

            } else if (in.startsWith("delete ")) {
                try {
                    int n = Integer.parseInt(in.split(" ")[1]) - 1;
                    System.out.println("     Noted. I've removed this task:");
                    System.out.printf("       [%c][%s] %s%s\n",
                            typ.get(n), al2.get(n) ? "X" : " ", al.get(n), renderExtra(typ.get(n), det.get(n), due.get(n)));
                    al.remove(n); al2.remove(n); typ.remove(n); det.remove(n); due.remove(n);
                    System.out.printf("     Now you have %d tasks in the list.\n", al.size());
                    saveAll(al, al2, typ, det, due);
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to delete.");
                }

            } else if (in.startsWith("todo")) {
                String desc = in.length() >= 5 ? in.substring(5).trim() : "";
                if (desc.isEmpty()) {
                    System.out.println("     OOPS!!! The description of a todo cannot be empty.");
                } else {
                    al.add(desc); al2.add(false); typ.add('T'); det.add(""); due.add(null);
                    System.out.println("     Got it. I've added this task:");
                    System.out.printf("       [T][ ] %s\n", desc);
                    System.out.printf("     Now you have %d tasks in the list.\n", al.size());
                    saveAll(al, al2, typ, det, due);
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
                        LocalDate parsed = tryParseDate(by);
                        al.add(desc); al2.add(false); typ.add('D');
                        if (parsed != null) {
                            due.add(parsed);
                            det.add(""); // date lives in 'due'
                            System.out.println("     Got it. I've added this task:");
                            System.out.printf("       [D][ ] %s (by: %s)\n", desc, OUT.format(parsed));
                        } else {
                            due.add(null);
                            det.add(" (by: " + by + ")");
                            System.out.println("     Got it. I've added this task:");
                            System.out.printf("       [D][ ] %s (by: %s)\n", desc, by);
                        }
                        System.out.printf("     Now you have %d tasks in the list.\n", al.size());
                        saveAll(al, al2, typ, det, due);
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
                        det.add(d); due.add(null);
                        System.out.println("     Got it. I've added this task:");
                        System.out.printf("       [E][ ] %s%s\n", desc, d);
                        System.out.printf("     Now you have %d tasks in the list.\n", al.size());
                        saveAll(al, al2, typ, det, due);
                    }
                }

            } else {
                System.out.println("     OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

            System.out.println(m);
        }
    }

    static String renderExtra(char t, String det, LocalDate due) {
        if (t == 'D') {
            if (due != null) return " (by: " + OUT.format(due) + ")";
            return det == null ? "" : det;
        }
        return det == null ? "" : det;
    }

    static LocalDate tryParseDate(String s) {
        try {
            return LocalDate.parse(s); // expects yyyy-MM-dd
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    static void loadFromDisk(ArrayList<String> al, ArrayList<Boolean> al2,
                             ArrayList<Character> typ, ArrayList<String> det,
                             ArrayList<LocalDate> due) {
        try {
            if (!Files.exists(SAVE_PATH)) return;
            List<String> lines = Files.readAllLines(SAVE_PATH);
            for (String ln : lines) {
                String[] parts = ln.split("\\|", -1);
                //for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
                if (parts.length < 3) continue;
                char t = parts[0].charAt(0);
                boolean done = "1".equals(parts[1]);
                String desc = parts[2];
                String extra = parts.length >= 4 ? parts[3] : "";
                typ.add(t);
                al2.add(done);
                al.add(desc);
                if (t == 'D') {
                    LocalDate d = tryParseDate(extra);
                    if (d != null) {
                        due.add(d);
                        det.add("");
                    } else {
                        due.add(null);
                        det.add(extra.isEmpty() ? "" : " (by: " + extra + ")");
                    }
                } else if (t == 'E') {
                    due.add(null);
                    det.add(extra);
                } else {
                    due.add(null);
                    det.add("");
                }
            }
        } catch (IOException e) { }
    }

    static void saveAll(ArrayList<String> al, ArrayList<Boolean> al2,
                        ArrayList<Character> typ, ArrayList<String> det,
                        ArrayList<LocalDate> due) {
        try {
            if (SAVE_PATH.getParent() != null) Files.createDirectories(SAVE_PATH.getParent());
            try (BufferedWriter w = Files.newBufferedWriter(SAVE_PATH)) {
                for (int i = 0; i < al.size(); i++) {
                    String extra = "";
                    if (typ.get(i) == 'D') {
                        if (due.get(i) != null) extra = due.get(i).toString(); // ISO
                        else {
                            String d = det.get(i);
                            extra = d.startsWith(" (by: ") ? d.substring(6, d.length() - 1) : d;
                        }
                    } else if (typ.get(i) == 'E') {
                        extra = det.get(i);
                    }
                    String line = typ.get(i) + "|" + (al2.get(i) ? "1" : "0") + "|" + al.get(i);
                    if (!extra.isEmpty()) line += "|" + extra;
                    w.write(line);
                    w.newLine();
                }
            }
        } catch (IOException e) { }
    }
}
