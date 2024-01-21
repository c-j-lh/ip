1)

GPT4 didn't accoutn for my original code, but I suppose that's asking a bit much, I should have just given it the whole task description instead of a mix.
(see https://chat.openai.com/share/64c95585-6fb5-42c1-b2dc-f197b30389fe)

public class Deadline extends Task {
protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + (isMarked ? "[X] " : "[ ] ") + description + " (by: " + by + ")";
    }
}

when I think I feel it'd be better to use super.toString(). Hope I'm not getting rusty