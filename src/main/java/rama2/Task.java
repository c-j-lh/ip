package rama2;
import java.time.LocalDate;

/*
 * Task, with type, markedness, description, and either date-as-string or date-as LocalDate
 */
class Task {
    //char type;          // 'T','D','E'
    enum TaskType { T, D, E };
    TaskType type;
    boolean done;
    String desc;
    String extra;       // for E and non-date D: "(from: ... to: ...)" or " (by: Sunday)"
    LocalDate due;      // for date-based D; else null

    Task(TaskType type, boolean done, String desc, String extra, LocalDate due) {
        this.type = type; this.done = done; this.desc = desc; this.extra = extra; this.due = due;
    }
}
