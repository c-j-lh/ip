import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        String logo =   "     Hello! I'm Rama2\n" +
                        "     What can I do for you?\n";
        String end =    "     Bye. Hope to see you again soon!\n";
        String m = "    ____________________________________________________________";

        ArrayList<String> al = new ArrayList<>();          // task descriptions
        ArrayList<Boolean> al2 = new ArrayList<>();         // done flags
        ArrayList<Character> typ = new ArrayList<>();       // 'T', 'D', 'E'
        ArrayList<String> det = new ArrayList<>();          // details e.g. " (by: Sunday)"

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
                    System.out.printf("     %d.[%c][%s] %s%s\n", i + 1,
                            typ.get(i),
                            al2.get(i) ? "X" : " ",
                            al.get(i),
                            det.get(i));
                }

            } else if (in.startsWith("mark ")) {
                int n = Integer.parseInt(in.split(" ")[1]) - 1;
                al2.set(n, true);
                System.out.println("     Nice! I've marked this task as done:");
                System.out.printf("       [%c][X] %s%s\n", typ.get(n), al.get(n), det.get(n));

            } else if (in.startsWith("unmark ")) {
                int n = Integer.parseInt(in.split(" ")[1]) - 1;
                al2.set(n, false);
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.printf("       [%c][ ] %s%s\n", typ.get(n), al.get(n), det.get(n));

            } else if (in.startsWith("todo ")) {
                String desc = in.substring(5).trim();
                al.add(desc);
                al2.add(false);
                typ.add('T');
                det.add("");
                System.out.println("     Got it. I've added this task:");
                System.out.printf("       [T][ ] %s\n", desc);
                System.out.printf("     Now you have %d tasks in the list.\n", al.size());

            } else if (in.startsWith("deadline ")) {
                String rest = in.substring(9);
                int idx = rest.indexOf(" /by ");
                String desc = (idx == -1) ? rest.trim() : rest.substring(0, idx).trim();
                String by = (idx == -1) ? "" : rest.substring(idx + 5).trim();
                al.add(desc);
                al2.add(false);
                typ.add('D');
                det.add(by.isEmpty() ? "" : " (by: " + by + ")");
                System.out.println("     Got it. I've added this task:");
                System.out.printf("       [D][ ] %s%s\n", desc, by.isEmpty() ? "" : " (by: " + by + ")");
                System.out.printf("     Now you have %d tasks in the list.\n", al.size());

            } else if (in.startsWith("event ")) {
                String rest = in.substring(6);
                int fromIdx = rest.indexOf(" /from ");
                int toIdx = rest.indexOf(" /to ");
                String desc = (fromIdx == -1) ? rest.trim() : rest.substring(0, fromIdx).trim();
                String from = (fromIdx == -1) ? "" : rest.substring(fromIdx + 7, (toIdx == -1 ? rest.length() : toIdx)).trim();
                String to = (toIdx == -1) ? "" : rest.substring(toIdx + 5).trim();
                al.add(desc);
                al2.add(false);
                typ.add('E');
                String d = "";
                if (!from.isEmpty() || !to.isEmpty()) {
                    d = " (from: " + (from.isEmpty() ? "?" : from) + " to: " + (to.isEmpty() ? "?" : to) + ")";
                }
                det.add(d);
                System.out.println("     Got it. I've added this task:");
                System.out.printf("       [E][ ] %s%s\n", desc, d);
                System.out.printf("     Now you have %d tasks in the list.\n", al.size());

            } else {
                // fallback: treat as a simple ToDo (keeps prior behavior minimal)
                al.add(in);
                al2.add(false);
                typ.add('T');
                det.add("");
                System.out.println("     Got it. I've added this task:");
                System.out.printf("       [T][ ] %s\n", in);
                System.out.printf("     Now you have %d tasks in the list.\n", al.size());
            }
            System.out.println(m);
        }
    }
}
