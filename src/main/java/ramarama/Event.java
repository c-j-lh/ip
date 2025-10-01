package ramarama;

import java.time.LocalDate;

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

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString()
                + " (from: " + OUT.format(getDateAt()) + " to: " + OUT.format(getEnd()) + ")";
    }
}
