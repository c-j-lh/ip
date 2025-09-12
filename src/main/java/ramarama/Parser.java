package ramarama;

import java.time.LocalDate;

/*
 * Parses  single raw input String into Cmd (with a,b,c fields).
 */
class Parser {
    static class Cmd {
        String name; // list, bye, todo, deadline, event, mark, unmark, delete, unknown
        String a;
        String b;
        String c; // generic slots

        Cmd(String name) {
            this.name = name;
        }
    }

    static Cmd parse(String in) {
        String cmd = in.split(" ")[0].toLowerCase();
        if (cmd.equals("b") || cmd.equals("bye")) {
            return new Cmd("bye");
        }
        if (cmd.equals("l") || cmd.equals("list")) {
            return new Cmd("list");
        }
        if (cmd.equals("m") || cmd.equals("mark")) {
            Cmd c = new Cmd("mark");
            String[] splits = in.split(" ", 2);
            c.a = splits.length > 1 ? splits[1] : "";
            return c;
        }
        if (cmd.equals("u") || cmd.equals("unmark")) {
            Cmd c = new Cmd("unmark");
            String[] splits = in.split(" ", 2);
            c.a = splits.length > 1 ? splits[1] : "";
            return c;
        }
        if (cmd.startsWith("delete ")) {
            Cmd c = new Cmd("delete");
            String[] splits = in.split(" ", 2);
            c.a = splits.length > 1 ? splits[1] : "";
            return c;
        }
        if (cmd.equals("t") || cmd.equals("todo")) {
            Cmd c = new Cmd("todo");
            c.a = in.length() >= 5 ? in.substring(5).trim() : "";
            return c;
        }
        if (cmd.startsWith("deadline")) {
            Cmd c = new Cmd("deadline");
            String rest = in.length() >= 9 ? in.substring(9) : "";
            int idx = rest.indexOf(" /by ");
            c.a = rest.trim();
            c.b = idx == -1 ? null : rest.substring(0, idx).trim();
            c.c = idx == -1 ? null : rest.substring(idx + 5).trim();
            return c;
        }
        if (cmd.equals("e") || cmd.equals("event")) {
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
        if (cmd.equals("f") || cmd.equals("find")) {
            Cmd c = new Cmd("find");
            String rest = in.length() >= 5 ? in.substring(5) : "";
            c.a = rest.trim();
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
