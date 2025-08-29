package rama2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.*;

/*
 * Tests a varied routine.
 */
class StorageRoundTripTest {

    private static final Path DATA_DIR = Paths.get("data");
    private static final Path DATA_FILE = DATA_DIR.resolve("rama2.txt");
    private static final Path BACKUP_FILE = DATA_DIR.resolve("rama2.txt.bak");

    @BeforeEach
    void backupExisting() throws IOException {
        if (Files.exists(DATA_FILE)) {
            Files.createDirectories(DATA_DIR);
            Files.deleteIfExists(BACKUP_FILE);
            Files.move(DATA_FILE, BACKUP_FILE);
        }
        Files.createDirectories(DATA_DIR);
        Files.deleteIfExists(DATA_FILE);
    }

    @AfterEach
    void restoreExisting() throws IOException {
        Files.deleteIfExists(DATA_FILE);
        if (Files.exists(BACKUP_FILE)) {
            Files.move(BACKUP_FILE, DATA_FILE);
        }
    }

    @Test
    void saveThenLoad_roundTrip_preservesAllTaskFields() throws Exception {
        // Arrange: build an in-memory list with diverse tasks
        TaskList original = new TaskList();
        original.add(new Task(Task.TaskType.T, false, "read book", "", null));
        original.add(new Task(Task.TaskType.D, false, "return book", "", LocalDate.of(2019, 10, 15))); // ISO date
        original.add(new Task(Task.TaskType.D, true, "finish draft", " (by: next week)", null)); // free-text
        original.add(new Task(Task.TaskType.E, false, "project meeting", " (from: Mon 2pm to: 4pm)", null));

        Storage storage = new Storage();
        storage.save(original); // writes to data/duke.txt

        // Act: load back
        List<Task> loadedList = storage.load();
        TaskList loaded = new TaskList(loadedList);

        // Assert: same size
        assertEquals(original.size(), loaded.size(), "task count must match");

        // Assert each field carefully
        // 1) ToDo
        Task t0 = loaded.get(0);
        assertEquals(Task.TaskType.T, t0.type);
        assertFalse(t0.done);
        assertEquals("read book", t0.desc);
        assertEquals("", t0.extra);
        assertNull(t0.due);

        // 2) Deadline (ISO)
        Task t1 = loaded.get(1);
        assertEquals(Task.TaskType.D, t1.type);
        assertFalse(t1.done);
        assertEquals("return book", t1.desc);
        assertEquals("", t1.extra);
        assertEquals(LocalDate.of(2019, 10, 15), t1.due); // parsed back to LocalDate

        // 3) Deadline (free-text)
        Task t2 = loaded.get(2);
        assertEquals(Task.TaskType.D, t2.type);
        assertTrue(t2.done);
        assertEquals("finish draft", t2.desc);
        assertEquals(" (by: next week)", t2.extra);
        assertNull(t2.due);

        // 4) Event
        Task t3 = loaded.get(3);
        assertEquals(Task.TaskType.E, t3.type);
        assertFalse(t3.done);
        assertEquals("project meeting", t3.desc);
        assertEquals(" (from: Mon 2pm to: 4pm)", t3.extra);
        assertNull(t3.due);
    }
}
