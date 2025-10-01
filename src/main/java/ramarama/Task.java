package ramarama;

import java.time.LocalDate;

/*
 * Task, with type, markedness, description, and either LocalDate fields.
 */
class Task {
    enum TaskType {
        T, D, E
    }

    private boolean done;
    private String desc;
    private LocalDate dateAt; // deadline for D, and start date and E
    private LocalDate end; // end date for E
    private final TaskType type;

    Task(TaskType type, boolean done, String desc, LocalDate dateAt, LocalDate end) {
        this.type = type;
        this.setDone(done);
        this.desc = desc;
        this.dateAt = dateAt;
        this.end = end;
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

    public LocalDate getDateAt() {
        return dateAt;
    }

    public LocalDate getEnd() {
        return end;
    }
}
