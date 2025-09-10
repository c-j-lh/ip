package ramarama;

import java.time.LocalDate;

import javafx.application.Platform;

/*
 * Main class.
 */
public class Rama2 {
    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Rama2(String filePath) {
        this.storage = new Storage();
        this.ui = new Ui();
        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (Exception e) {
            loaded = new TaskList();
        }
        this.tasks = loaded;
    }

    public String getResponse(String input) {
        StringBuilder output = new StringBuilder();
        Parser.Cmd c = Parser.parse(input);
        switch (c.name) {
        case "bye":
            output.append(ui.showBye());
            break;

        case "list":
            output.append(ui.printList(tasks));
            break;

        case "mark":
            try {
                int n = Integer.parseInt(c.a) - 1;
                Task t = tasks.get(n);
                t.done = true;
                output.append("     Nice! I've marked this task as done:\n");
                output.append("       ").append(ui.render(t)).append("\n");
                storageSave();
            } catch (Exception e) {
                output.append("     OOPS!!! Please provide a valid task number to mark.\n");
            }
            break;

        case "unmark":
            try {
                int n = Integer.parseInt(c.a) - 1;
                Task t = tasks.get(n);
                t.done = false;
                output.append("     OK, I've marked this task as not done yet:\n");
                output.append("       ").append(ui.render(t)).append("\n");
                storageSave();
            } catch (Exception e) {
                output.append("     OOPS!!! Please provide a valid task number to unmark.\n");
            }
            break;

        case "delete":
            try {
                int n = Integer.parseInt(c.a) - 1;
                Task t = tasks.get(n);
                output.append("     Noted. I've removed this task:\n");
                output.append("       ").append(ui.render(t)).append("\n");
                tasks.remove(n);
                output.append(String.format("     Now you have %d tasks in the list.\n", tasks.size()));
                storageSave();
            } catch (Exception e) {
                output.append("     OOPS!!! Please provide a valid task number to delete.\n");
            }
            break;

        case "todo":
            if (c.a == null || c.a.isEmpty()) {
                output.append("     OOPS!!! The description of a todo cannot be empty.\n");
            } else {
                Task t = new Task(Task.TaskType.T, false, c.a, "", null);
                tasks.add(t);
                output.append("     Got it. I've added this task:\n");
                output.append("       ").append(ui.render(t)).append("\n");
                output.append(String.format("     Now you have %d tasks in the list.\n", tasks.size()));
                storageSave();
            }
            break;

        case "deadline":
            if (c.a == null || c.b == null || c.c == null || c.b.isEmpty() || c.c.isEmpty()) {
                output.append("     OOPS!!! Use: deadline <desc> /by <when>\n");
            } else {
                LocalDate d = Parser.tryParseDate(c.c);
                Task t = (d != null)
                        ? new Task(Task.TaskType.D, false, c.b, "", d)
                        : new Task(Task.TaskType.D, false, c.b, " (by: " + c.c + ")", null);
                tasks.add(t);
                output.append("     Got it. I've added this task:\n");
                output.append("       ").append(ui.render(t)).append("\n");
                output.append(String.format("     Now you have %d tasks in the list.\n", tasks.size()));
                storageSave();
            }
            break;

        case "event":
            if (c.a == null || c.b == null || c.c == null) {
                output.append("     OOPS!!! Use: event <desc> /from <start> /to <end>\n");
            } else {
                int bar = c.c.indexOf('|');
                String from = c.c.substring(0, bar);
                String to = c.c.substring(bar + 1);
                if (c.b.isEmpty() || from.isEmpty() || to.isEmpty()) {
                    output.append("     OOPS!!! Event needs description, /from and /to.\n");
                } else {
                    String extra = " (from: " + from + " to: " + to + ")";
                    Task t = new Task(Task.TaskType.E, false, c.b, extra, null);
                    tasks.add(t);
                    output.append("     Got it. I've added this task:\n");
                    output.append("       ").append(ui.render(t)).append("\n");
                    output.append(String.format("     Now you have %d tasks in the list.\n", tasks.size()));
                    storageSave();
                }
            }
            break;
        case "find":
            if (c.a == null) {
                output.append("     OOPS!!! Use: find <String>\n");
            } else {
                TaskList found = new TaskList();
                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).desc.contains(c.a)) {
                        found.add(tasks.get(i));
                    }
                }
                output.append(ui.printList(found));
            }
            break;

        case "unknown":
            output.append("     OOPS!!! I'm sorry, but I don't know what that means :-(").append("\n");
            break;
        default:
            assert false : "cmd.name should be 'unknown' if invalid";
        }
        return output.toString();
    }

    /*
     * Main runner function
     */
    public void run() {
        ui.showWelcome();
        boolean exit = false;
        while (!exit) {
            String input = ui.read();
            ui.line();
            System.out.println(getResponse(input).toString());
            ui.line();
            if ("bye".equalsIgnoreCase(input.trim())) {
                break;
            }
        }
    }

    private void storageSave() {
        try {
            storage.save(tasks);
        } catch (Exception e) {
            /* ignore minimal */
        }
    }

    public static void main(String[] args) {
        new Rama2("data/duke.txt").run();
    }
    
}
