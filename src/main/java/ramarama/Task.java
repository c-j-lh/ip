package ramarama;

import java.time.format.DateTimeFormatter;

/**
 * Task, with type, markedness, description fields.
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

    /**
     * One character symbol for this task type.
     */
    public abstract String getType();

    /**
     * Returns a well-formatted String for rendering in UIs.
     */
    @Override
    public String toString() {
        return String.format("[%s][%s] %s", getType(), isDone() ? "X" : " ", getDesc());
    }
}
