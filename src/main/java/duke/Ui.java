package duke;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

class Ui {
    final Scanner in = new Scanner(System.in);
    final String m = "    ____________________________________________________________";
    final DateTimeFormatter OUT = DateTimeFormatter.ofPattern("MMM d yyyy");

    void showWelcome() {
        System.out.println(m + "\n" +
                "     Hello! I'm Rama2\n" +
                "     What can I do for you?\n" + m);
    }
    void line() { System.out.println(m); }
    String read() { return in.nextLine(); }
    void showBye() { System.out.println("     Bye. Hope to see you again soon!"); }

    String render(Task t) {
        String extra = "";
        if (t.type == Task.TaskType.D) {
            if (t.due != null) extra = " (by: " + OUT.format(t.due) + ")";
            else extra = t.extra == null ? "" : t.extra;
        } else if (t.type == Task.TaskType.E) {
            extra = t.extra == null ? "" : t.extra;
        }
        return String.format("[%c][%s] %s%s", t.type, t.done ? "X" : " ", t.desc, extra);
    }

    void printList(TaskList tl) {
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tl.size(); i++) {
            Task t = tl.get(i);
            System.out.printf("     %d.%s\n", i + 1, render(t));
        }
    }
}
