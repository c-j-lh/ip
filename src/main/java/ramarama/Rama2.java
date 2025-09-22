package ramarama;

import java.time.LocalDate;

/*
 * Main class.
 */
public class Rama2 {
    static final String ERROR_STRING = "     OOPS!!! I'm sorry, but I don't know what that means :-(\n";
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
        Parser.Cmd c = Parser.parse(input);
        switch (c.name) {
        case "bye":
            return ui.showBye();
        case "list":
            return ui.printList(tasks);
        case "mark":
            return handleMark(c);
        case "unmark":
            return handleUnmark(c);
        case "delete":
            return handleDelete(c);
        case "todo":
            return handleTodo(c);
        case "deadline":
            return handleDeadline(c);
        case "event":
            return handleEvent(input);
        case "find":
            return handleFind(c);
        case "unknown":
            return ERROR_STRING;
        default:
            assert false : ("cmd.name should be 'unknown' if invalid."
                    + "Something is wrong with the code");
            return ERROR_STRING;
        }
    }

    private String handleMark(Parser.Cmd c) {
        Integer idx = parseIndex(c.a);
        if (idx == null) {
            return "     OOPS!!! Please provide a valid task number to mark.\n";
        }
        Task t = tasks.get(idx);
        t.done = true;
        storageSave();
        return "     Nice! I've marked this task as done:\n       " + ui.render(t) + "\n";
    }

    private String handleUnmark(Parser.Cmd c) {
        Integer idx = parseIndex(c.a);
        if (idx == null) {
            return "     OOPS!!! Please provide a valid task number to unmark.\n";
        }
        Task t = tasks.get(idx);
        t.done = false;
        storageSave();
        return "     OK, I've marked this task as not done yet:\n       " + ui.render(t) + "\n";
    }

    private String handleDelete(Parser.Cmd c) {
        Integer idx = parseIndex(c.a);
        if (idx == null) {
            return "     OOPS!!! Please provide a valid task number to delete.\n";
        }
        Task t = tasks.get(idx);
        tasks.remove((int) idx);
        storageSave();
        return "     Noted. I've removed this task:\n       " + ui.render(t) + "\n"
                + String.format("     Now you have %d tasks in the list.\n", tasks.size());
    }

    private String handleTodo(Parser.Cmd c) {
        if (c.a == null || c.a.isEmpty()) {
            return "     OOPS!!! The description of a todo cannot be empty.\n";
        }
        Task t = new Task(Task.TaskType.T, false, c.a, "", null);
        tasks.add(t);
        storageSave();
        return addedMsg(t);
    }

    private String handleDeadline(Parser.Cmd c) {
        if (c.a == null || c.b == null || c.c == null || c.b.isEmpty() || c.c.isEmpty()) {
            return "     OOPS!!! Use: deadline <desc> /by <when>\n";
        }
        LocalDate d = Parser.tryParseDate(c.c);
        Task t = (d != null)
                ? new Task(Task.TaskType.D, false, c.b, "", d)
                : new Task(Task.TaskType.D, false, c.b, " (by: " + c.c + ")", null);
        tasks.add(t);
        storageSave();
        return addedMsg(t);
    }

    private String handleEvent(String rawInput) {
        String trimmed = rawInput.trim();
        int sp = trimmed.indexOf(' ');
        String rest = sp == -1 ? "" : trimmed.substring(sp + 1);
        String[] argsForPico = rest.isBlank() ? new String[0] : rest.trim().split("\\s+");

        EventOptions opts = new EventOptions();
        try {
            new picocli.CommandLine(opts).parseArgs(argsForPico);
        } catch (picocli.CommandLine.ParameterException ex) {
            return "     OOPS!!! Use: event <desc> /from <start> /to <end>\n";
        }

        if (opts.desc().isEmpty() || opts.from().isEmpty() || opts.to().isEmpty()) {
            return "     OOPS!!! Use: event <desc> /from <start> /to <end>\n";
        }

        String extra = " (from: " + opts.from() + " to: " + opts.to() + ")";
        Task t = new Task(Task.TaskType.E, false, opts.desc(), extra, null);
        tasks.add(t);
        storageSave();
        return addedMsg(t);
    }

    private String handleFind(Parser.Cmd c) {
        if (c.a == null) {
            return "     OOPS!!! Use: find <String>\n";
        }
        TaskList found = new TaskList();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).desc.contains(c.a)) {
                found.add(tasks.get(i));
            }
        }
        return ui.printList(found);
    }

    private Integer parseIndex(String a) {
        try {
            int n = Integer.parseInt(a) - 1;
            if (n < 0 || n >= tasks.size()) {
                return null;
            }
            return n;
        } catch (Exception e) {
            return null;
        }
    }

    private String addedMsg(Task t) {
        return "     Got it. I've added this task:\n"
                + "       " + ui.render(t) + "\n"
                + String.format("     Now you have %d tasks in the list.\n", tasks.size());
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
            System.err.println("Failed to save tasks: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Rama2("data/duke.txt").run();
    }

}
