package ramarama;

import java.time.LocalDate;

/*
 * Task, with type, markedness, description, and either date-as-string or date-as LocalDate
 */
class Task {
    enum TaskType {
        T, D, E
    };

    private boolean done;
    private String desc;
    private String extra; // for E and non-date D: "(from: ... to: ...)" or " (by: Sunday)"
    private LocalDate due; // for date-based D; else null
    private final TaskType type;

    Task(TaskType type, boolean done, String desc, String extra, LocalDate due) {
        this.type = type;
        this.setDone(done);
        this.desc = desc;
        this.extra = extra;
        this.due = due;
    }

    public TaskType getType() {
        return type;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDesc() {
        return desc;
    }

    public String getExtra() {
        return extra;
    }

    public LocalDate getDue() {
        return due;
    }
}
