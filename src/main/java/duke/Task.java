package duke;
import java.time.LocalDate;

class Task {
    char type;          // 'T','D','E'
    boolean done;
    String desc;
    String extra;       // for E and non-date D: "(from: ... to: ...)" or " (by: Sunday)"
    LocalDate due;      // for date-based D; else null

    Task(char type, boolean done, String desc, String extra, LocalDate due) {
        this.type = type; this.done = done; this.desc = desc; this.extra = extra; this.due = due;
    }
}
