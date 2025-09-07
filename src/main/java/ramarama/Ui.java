package ramarama;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/*
 * Stores various standard output strings.
 */
class Ui {
    final Scanner in = new Scanner(System.in);
    final String m = "    ____________________________________________________________";
    static final DateTimeFormatter OUT = DateTimeFormatter.ofPattern("MMM d yyyy");

    /*
     * Returns standard welcome message.
     */
    String showWelcome() {
        return m
                + "\n"
                + "     Hello! I'm Rama2\n"
                + "     What can I do for you?\n"
                + m;
    }

    /*
     * Returns border. To be called before and after every Rama2 output.
     */
    String line() {
        return m;
    }

    /*
     * readLine() wrapper.
     */
    String read() {
        return in.nextLine();
    }

    /*
     * Returns farewell message.
     */
    String showBye() {
        return "     Bye. Hope to see you again soon!";
    }

    /*
     * Renders a Task to be used in lists or individually.
     */
    String render(Task t) {
        String extra = "";
        if (t.type == Task.TaskType.D) {
            if (t.due != null) {
                extra = " (by: " + OUT.format(t.due) + ")";
            } else {
                extra = t.extra == null ? "" : t.extra;
            }
        } else if (t.type == Task.TaskType.E) {
            extra = t.extra == null ? "" : t.extra;
        }
        return String.format("[%c][%s] %s%s",
                ((t.type == Task.TaskType.D) ? 'D' : (t.type == Task.TaskType.T) ? 'T' : 'E'), t.done ? "X" : " ",
                t.desc, extra);
    }

    /*
     * Returns a whole list of Tasks as a String. A convenient function.
     */
    String printList(TaskList tl) {
        StringBuilder sb = new StringBuilder();
        sb.append("     Here are the tasks in your list:\n");
        for (int i = 0; i < tl.size(); i++) {
            Task t = tl.get(i);
            sb.append(String.format("     %d.%s\n", i + 1, render(t)));
        }
        return sb.toString();
    }
}
