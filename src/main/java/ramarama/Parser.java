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
        String first = firstToken(in).toLowerCase();

        switch (first) {
            case "b":
            case "bye":
                return new Cmd("bye");

            case "l":
            case "list":
                return new Cmd("list");

            case "m":
            case "mark":
                return cmdWithA("mark", in);

            case "u":
            case "unmark":
                return cmdWithA("unmark", in);

            case "delete":
                return cmdWithA("delete", in);

            case "t":
            case "todo": {
                return cmdWithA("todo", in);
            }

            case "deadline": {
                Cmd c = new Cmd("deadline");
                // keep original behavior: substring(9) (after "deadline")
                String rest = in.length() >= 9 ? in.substring(9) : "";
                int idx = rest.indexOf(" /by ");
                c.a = rest.trim();
                c.b = idx == -1 ? null : rest.substring(0, idx).trim();
                c.c = idx == -1 ? null : rest.substring(idx + 5).trim();
                return c;
            }

            case "event": {
                Cmd c = new Cmd("event");
                // keep original behavior: substring(6) (after "event")
                String rest = in.length() >= 6 ? in.substring(6) : "";
                int fromIdx = rest.indexOf(" /from ");
                int toIdx = rest.indexOf(" /to ");
                c.a = rest.trim();
                c.b = fromIdx == -1 ? null : rest.substring(0, fromIdx).trim();
                c.c = (fromIdx == -1 || toIdx == -1 || toIdx <= fromIdx)
                        ? null
                        : rest.substring(fromIdx + 7, toIdx).trim() + "|" + rest.substring(toIdx + 5).trim();
                return c;
            }

            case "f":
            case "find": {
                return cmdWithA("find", in);
            }

            default:
                return new Cmd("unknown");
        }
    }

    /* --- tiny helpers to reduce duplication --- */

    private static String firstToken(String in) {
        String[] parts = in.split(" ", 2);
        return parts.length == 0 ? "" : parts[0];
    }

    private static Cmd cmdWithA(String name, String in) {
        Cmd c = new Cmd(name);
        String[] splits = in.split(" ", 2);
        c.a = splits.length > 1 ? splits[1] : "";
        return c;
    }


    static LocalDate tryParseDate(String s) {
        try {
            return LocalDate.parse(s);
        } catch (Exception e) {
            return null;
        }
    }
}
