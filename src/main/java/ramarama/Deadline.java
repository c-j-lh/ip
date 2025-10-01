package ramarama;

import java.time.LocalDate;

/**
 * Deadline class, with an additional dateAt compared to Task.
 */
class Deadline extends Task {
    private LocalDate dateAt;

    Deadline(boolean done, String desc, LocalDate dateAt) {
        super(done, desc);
        this.dateAt = dateAt;
    }

    public LocalDate getDateAt() {
        return dateAt;
    }

    /**
     * One character symbol for this task type.
     */
    @Override
    public String getType() {
        return "D";
    }

    /**
     * Returns a well-formatted String for rendering in UIs.
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + OUT.format(getDateAt()) + ")";
    }
}
