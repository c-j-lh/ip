package ramarama;

import java.time.LocalDate;

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

    /*
     * Main runner function
     */
    public void run() {
        ui.showWelcome();
        boolean exit = false;
        while (!exit) {
            String input = ui.read();
            ui.line();
            Parser.Cmd c = Parser.parse(input);

            switch (c.name) {
            case "bye":
                ui.showBye();
                exit = true;
                break;

            case "list":
                ui.printList(tasks);
                break;

            case "mark":
                try {
                    int n = Integer.parseInt(c.a) - 1;
                    Task t = tasks.get(n);
                    t.done = true;
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.println("       " + ui.render(t));
                    storageSave();
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to mark.");
                }
                break;

            case "unmark":
                try {
                    int n = Integer.parseInt(c.a) - 1;
                    Task t = tasks.get(n);
                    t.done = false;
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.println("       " + ui.render(t));
                    storageSave();
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to unmark.");
                }
                break;

            case "delete":
                try {
                    int n = Integer.parseInt(c.a) - 1;
                    Task t = tasks.get(n);
                    System.out.println("     Noted. I've removed this task:");
                    System.out.println("       " + ui.render(t));
                    tasks.remove(n);
                    System.out.printf("     Now you have %d tasks in the list.\n", tasks.size());
                    storageSave();
                } catch (Exception e) {
                    System.out.println("     OOPS!!! Please provide a valid task number to delete.");
                }
                break;

            case "todo":
                if (c.a == null || c.a.isEmpty()) {
                    System.out.println("     OOPS!!! The description of a todo cannot be empty.");
                } else {
                    Task t = new Task(Task.TaskType.T, false, c.a, "", null);
                    tasks.add(t);
                    System.out.println("     Got it. I've added this task:");
                    System.out.println("       " + ui.render(t));
                    System.out.printf("     Now you have %d tasks in the list.\n", tasks.size());
                    storageSave();
                }
                break;

            case "deadline":
                if (c.a == null || c.b == null || c.c == null || c.b.isEmpty() || c.c.isEmpty()) {
                    System.out.println("     OOPS!!! Use: deadline <desc> /by <when>");
                } else {
                    LocalDate d = Parser.tryParseDate(c.c);
                    Task t = (d != null)
                            ? new Task(Task.TaskType.D, false, c.b, "", d)
                            : new Task(Task.TaskType.D, false, c.b, " (by: " + c.c + ")", null);
                    tasks.add(t);
                    System.out.println("     Got it. I've added this task:");
                    System.out.println("       " + ui.render(t));
                    System.out.printf("     Now you have %d tasks in the list.\n", tasks.size());
                    storageSave();
                }
                break;

            case "event":
                if (c.a == null || c.b == null || c.c == null) {
                    System.out.println("     OOPS!!! Use: event <desc> /from <start> /to <end>");
                } else {
                    int bar = c.c.indexOf('|');
                    String from = c.c.substring(0, bar);
                    String to = c.c.substring(bar + 1);
                    if (c.b.isEmpty() || from.isEmpty() || to.isEmpty()) {
                        System.out.println("     OOPS!!! Event needs description, /from and /to.");
                    } else {
                        String extra = " (from: " + from + " to: " + to + ")";
                        Task t = new Task(Task.TaskType.E, false, c.b, extra, null);
                        tasks.add(t);
                        System.out.println("     Got it. I've added this task:");
                        System.out.println("       " + ui.render(t));
                        System.out.printf("     Now you have %d tasks in the list.\n", tasks.size());
                        storageSave();
                    }
                }
                break;
            case "find":
                if (c.a == null) {
                    System.out.println("     OOPS!!! Use: find <String>");
                } else {
                    TaskList found = new TaskList();
                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).desc.contains(c.a)) {
                            found.add(tasks.get(i));
                        }
                    }
                    ui.printList(found);
                }
                break;

            default:
                System.out.println("     OOPS!!! I'm sorry, but I don't know what that means :-(");
            }

            ui.line();
            if (exit) {
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
