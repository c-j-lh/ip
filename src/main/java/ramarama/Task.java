package ramarama;

import java.time.format.DateTimeFormatter;

/*
 * Task, with type, markedness, description, and either LocalDate fields.
 */
abstract class Task {
    static final DateTimeFormatter OUT = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private boolean done;
    private String desc;

    Task(boolean done, String desc) {
        this.setDone(done);
        this.desc = desc;
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

    public abstract String getType();

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", getType(), isDone() ? "X" : " ", getDesc());
    }
}
