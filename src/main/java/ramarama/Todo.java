package ramarama;

/**
 * Todo class, with almost the same functionality as Task.
 */
class Todo extends Task {
    Todo(boolean done, String desc) {
        super(done, desc);
    }

    /**
     * One character symbol for this task type.
     */
    @Override
    public String getType() {
        return "T";
    }

    /**
     * Returns a well-formatted String for rendering in UIs.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
