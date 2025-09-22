package ramarama;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals(Task.TaskType.T, t0.getType());
        assertFalse(t0.isDone());
        assertEquals("read book", t0.getDesc());
        assertEquals("", t0.getExtra());
        assertNull(t0.getDue());

        // 2) Deadline (ISO)
        Task t1 = loaded.get(1);
        assertEquals(Task.TaskType.D, t1.getType());
        assertFalse(t1.isDone());
        assertEquals("return book", t1.getDesc());
        assertEquals("", t1.getExtra());
        assertEquals(LocalDate.of(2019, 10, 15), t1.getDue()); // parsed back to LocalDate

        // 3) Deadline (free-text)
        Task t2 = loaded.get(2);
        assertEquals(Task.TaskType.D, t2.getType());
        assertTrue(t2.isDone());
        assertEquals("finish draft", t2.getDesc());
        assertEquals(" (by: next week)", t2.getExtra());
        assertNull(t2.getDue());

        // 4) Event
        Task t3 = loaded.get(3);
        assertEquals(Task.TaskType.E, t3.getType());
        assertFalse(t3.isDone());
        assertEquals("project meeting", t3.getDesc());
        assertEquals(" (from: Mon 2pm to: 4pm)", t3.getExtra());
        assertNull(t3.getDue());
    }
}
