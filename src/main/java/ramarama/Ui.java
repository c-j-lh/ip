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
     * Prints standard welcome message.
     */
    void showWelcome() {
        System.out.println(m
                + "\n"
                + "     Hello! I'm Rama2\n"
                + "     What can I do for you?\n"
                + m);
    }

    /*
     * Prints border. To be called before and after every Rama2 output.
     */
    void line() {
        System.out.println(m);
    }

    /*
     * readLine() wrapper.
     */
    String read() {
        return in.nextLine();
    }

    /*
     * Prints farewell message.
     */
    void showBye() {
        System.out.println("     Bye. Hope to see you again soon!");
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
     * Prints a whole list of Tasks. A convenient function.
     */
    void printList(TaskList tl) {
        System.out.println("     Here are the tasks in your list:");
        for (int i = 0; i < tl.size(); i++) {
            Task t = tl.get(i);
            System.out.printf("     %d.%s\n", i + 1, render(t));
        }
    }
}
