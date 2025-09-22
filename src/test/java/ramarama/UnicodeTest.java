package ramarama;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/*
 * Tests Unicode characters in Task description.
 */
class UnicodeTest {
    private static final String TEST = "Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!";

    @Test
    void saveThenLoad_unicode_preservesUnicode() throws IOException {
        List<Task> l = new ArrayList<>();
        l.add(new Task(Task.TaskType.D, false, TEST, null, null));
        TaskList tl = new TaskList(l);

        Storage s = new Storage();
        s.save(tl);
        tl = new TaskList(s.load());

        Task t = tl.get(0);
        assertEquals(t.getDesc(), TEST);
    }
}
