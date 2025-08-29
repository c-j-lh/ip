package rama2;

import java.time.LocalDate;

class Parser {
    static class Cmd {
        String name; // list, bye, todo, deadline, event, mark, unmark, delete, unknown
        String a, b, c; // generic slots

        Cmd(String name) {
            this.name = name;
        }
    }

    static Cmd parse(String in) {
        if (in.equals("bye"))
            return new Cmd("bye");
        if (in.equals("list"))
            return new Cmd("list");
        if (in.startsWith("mark ")) {
            Cmd c = new Cmd("mark");
            c.a = in.split(" ")[1];
            return c;
        }
        if (in.startsWith("unmark ")) {
            Cmd c = new Cmd("unmark");
            c.a = in.split(" ")[1];
            return c;
        }
        if (in.startsWith("delete ")) {
            Cmd c = new Cmd("delete");
            c.a = in.split(" ")[1];
            return c;
        }
        if (in.startsWith("todo")) {
            Cmd c = new Cmd("todo");
            c.a = in.length() >= 5 ? in.substring(5).trim() : "";
            return c;
        }
        if (in.startsWith("deadline")) {
            Cmd c = new Cmd("deadline");
            String rest = in.length() >= 9 ? in.substring(9) : "";
            int idx = rest.indexOf(" /by ");
            c.a = rest.trim();
            c.b = idx == -1 ? null : rest.substring(0, idx).trim();
            c.c = idx == -1 ? null : rest.substring(idx + 5).trim();
            return c;
        }
        if (in.startsWith("event")) {
            Cmd c = new Cmd("event");
            String rest = in.length() >= 6 ? in.substring(6) : "";
            int fromIdx = rest.indexOf(" /from ");
            int toIdx = rest.indexOf(" /to ");
            c.a = rest.trim();
            c.b = fromIdx == -1 ? null : rest.substring(0, fromIdx).trim();
            c.c = (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx) ? null
                    : rest.substring(fromIdx + 7, toIdx).trim() + "|" + rest.substring(toIdx + 5).trim();
            return c;
        }
        return new Cmd("unknown");
    }

    static LocalDate tryParseDate(String s) {
        try {
            return LocalDate.parse(s);
        } catch (Exception e) {
            return null;
        }
    }
}
