package ramarama;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/*
 * Stores various standard output strings.
 */
class Ui {
    static final DateTimeFormatter OUT = DateTimeFormatter.ofPattern("MMM d yyyy");
    final Scanner in = new Scanner(System.in);
    final String m = "    ____________________________________________________________";

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
        if (t.getType() == Task.TaskType.D) {
            if (t.getDue() != null) {
                extra = " (by: " + OUT.format(t.getDue()) + ")";
            } else {
                extra = t.getExtra() == null ? "" : t.getExtra();
            }
        } else if (t.getType() == Task.TaskType.E) {
            extra = t.getExtra() == null ? "" : t.getExtra();
        }
        return String.format("[%c][%s] %s%s", (
                t.getType() == Task.TaskType.D) ? 'D' : (t.getType() == Task.TaskType.T) ? 'T' : 'E',
                t.isDone() ? "X" : " ", t.getDesc(), extra);
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
