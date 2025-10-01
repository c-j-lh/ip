package ramarama;

import java.time.LocalDate;

class Deadline extends Task {
    private LocalDate dateAt;

    Deadline(boolean done, String desc, LocalDate dateAt) {
        super(done, desc);
        this.dateAt = dateAt;
    }

    public LocalDate getDateAt() {
        return dateAt;
    }

    @Override
    public String getType() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + OUT.format(getDateAt()) + ")";
    }
}
