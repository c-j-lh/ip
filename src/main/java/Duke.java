import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    enum TaskType { T, D, E }

    public static void main(String[] args) {
        String logo =   "     Hello! I'm Rama2\n" +
                        "     What can I do for you?\n";
        String end =    "     Bye. Hope to see you again soon!";
        String m = "    ____________________________________________________________";

        ArrayList<String> al = new ArrayList<>();
        ArrayList<Boolean> al2 = new ArrayList<>();
        ArrayList<TaskType> typ = new ArrayList<>();
        ArrayList<String> det = new ArrayList<>();

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
                    char t = typ.get(i) == TaskType.T ? 'T' : typ.get(i) == TaskType.D ? 'D' : 'E';
                    System.out.printf("     %d.[%c][%s] %s%s\n",
                            i + 1, t, al2.get(i) ? "X" : " ", al.get(i), det.get(i));
                }

            } else if (in.startsWith("mark ")) {
                try {
                    int n = Integer.parseInt(in.split(" ")[1]) - 1;
                    if (n < 0 || n >= al.size()) throw new IndexOutOfBoundsException();
                    al2.set(n, true);
                    char t = typ.get(n) == TaskType.T ? 'T' : typ.get(n) == TaskType.D ? 'D' : 'E';
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.printf("       [%c][X] %s%s\n", t, al.get(n), det.get(n));
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to mark.");
                }

            } else if (in.startsWith("unmark ")) {
                try {
                    int n = Integer.parseInt(in.split(" ")[1]) - 1;
                    if (n < 0 || n >= al.size()) throw new IndexOutOfBoundsException();
                    al2.set(n, false);
                    char t = typ.get(n) == TaskType.T ? 'T' : typ.get(n) == TaskType.D ? 'D' : 'E';
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.printf("       [%c][ ] %s%s\n", t, al.get(n), det.get(n));
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to unmark.");
                }

            } else if (in.startsWith("delete ")) {
                try {
                    int n = Integer.parseInt(in.split(" ")[1]) - 1;
                    if (n < 0 || n >= al.size()) throw new IndexOutOfBoundsException();
                    char t = typ.get(n) == TaskType.T ? 'T' : typ.get(n) == TaskType.D ? 'D' : 'E';
                    System.out.println("     Noted. I've removed this task:");
                    System.out.printf("       [%c][%s] %s%s\n",
                            t, al2.get(n) ? "X" : " ", al.get(n), det.get(n));
                    al.remove(n); al2.remove(n); typ.remove(n); det.remove(n);
                    System.out.printf("     Now you have %d tasks in the list.\n", al.size());
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to delete.");
                }

            } else if (in.startsWith("todo")) {
                String desc = in.length() >= 5 ? in.substring(5).trim() : "";
                if (desc.isEmpty()) {
                    System.out.println("     OOPS!!! The description of a todo cannot be empty.");
                } else {
                    al.add(desc); al2.add(false); typ.add(TaskType.T); det.add("");
                    System.out.println("     Got it. I've added this task:");
                    System.out.printf("       [T][ ] %s\n", desc);
                    System.out.printf("     Now you have %d tasks in the list.\n", al.size());
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
                        al.add(desc); al2.add(false); typ.add(TaskType.D);
                        det.add(" (by: " + by + ")");
                        System.out.println("     Got it. I've added this task:");
                        System.out.printf("       [D][ ] %s (by: %s)\n", desc, by);
                        System.out.printf("     Now you have %d tasks in the list.\n", al.size());
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
                        al.add(desc); al2.add(false); typ.add(TaskType.E);
                        String d = " (from: " + from + " to: " + to + ")";
                        det.add(d);
                        System.out.println("     Got it. I've added this task:");
                        System.out.printf("       [E][ ] %s%s\n", desc, d);
                        System.out.printf("     Now you have %d tasks in the list.\n", al.size());
                    }
                }

            } else if (in.trim().isEmpty()) {
                System.out.println("     OOPS!!! I'm sorry, but I don't know what that means :-(");

            } else {
                System.out.println("     OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

            System.out.println(m);
        }
    }
}
