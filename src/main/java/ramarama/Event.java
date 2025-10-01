package ramarama;

import java.time.LocalDate;

/**
 * Event class, with an additional dateAt and end compared to Task.
 */
class Event extends Task {
    private LocalDate dateAt; // start date
    private LocalDate end; // end date

    Event(boolean done, String desc, LocalDate dateAt, LocalDate end) {
        super(done, desc);
        this.dateAt = dateAt;
        this.end = end;
    }

    public LocalDate getDateAt() {
        return dateAt;
    }

    public LocalDate getEnd() {
        return end;
    }

    /**
     * One character symbol for this task type.
     */
    @Override
    public String getType() {
        return "E";
    }

    /**
     * Returns a well-formatted String for rendering in UIs.
     */
    @Override
    public String toString() {
        return super.toString()
                + " (from: " + OUT.format(getDateAt()) + " to: " + OUT.format(getEnd()) + ")";
    }
}
