package ramarama;

class Todo extends Task {
    Todo(boolean done, String desc) {
        super(done, desc);
    }

    @Override
    public String getType() {
        return "T";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
