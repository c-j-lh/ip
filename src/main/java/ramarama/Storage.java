package ramarama;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * Saves and loads files.
 */
class Storage {
    static final Path SAVE_PATH = Paths.get("data", "rama.txt");

    /*
     * Loads file and returns List<Task>.
     */
    List<Task> load() throws IOException {
        if (!Files.exists(SAVE_PATH)) {
            return new ArrayList<>();
        }
        List<String> lines = Files.readAllLines(SAVE_PATH);
        ArrayList<Task> out = new ArrayList<>();
        for (String ln : lines) {
            String[] parts = ln.split("\\|", -1);
            if (parts.length < 3) {
                continue;
            }
            char t = parts[0].charAt(0);
            boolean done = "1".equals(parts[1]);
            String desc = parts[2];

            if (t == 'T') {
                out.add(new Task(Task.TaskType.T, done, desc, null, null));
            } else if (t == 'D') {
                if (parts.length < 4) {
                    continue;
                }
                LocalDate by = tryParse(parts[3]);
                if (by == null) {
                    continue;
                }
                out.add(new Task(Task.TaskType.D, done, desc, by, null));
            } else if (t == 'E') {
                if (parts.length < 5) {
                    continue;
                }
                LocalDate from = tryParse(parts[3]);
                LocalDate to = tryParse(parts[4]);
                if (from == null || to == null) {
                    continue;
                }
                out.add(new Task(Task.TaskType.E, done, desc, from, to));
            } else {
                continue;
            }

            if (parts.length > 5) {
                System.out.println("Misformatted task line in savefile");
            }
        }
        return out;
    }

    /*
     * Saves file from TaskList.
     */
    void save(TaskList tl) throws IOException {
        if (SAVE_PATH.getParent() != null) {
            Files.createDirectories(SAVE_PATH.getParent());
        }
        try (BufferedWriter w = Files.newBufferedWriter(SAVE_PATH)) {
            for (Task t : tl.asList()) {
                StringBuilder line = new StringBuilder();
                line.append(t.getType())
                        .append("|")
                        .append(t.isDone() ? "1" : "0")
                        .append("|")
                        .append(t.getDesc());

                if (t.getType() == Task.TaskType.D) {
                    if (t.getDue() == null) {
                        // strictly dates only; skip writing invalid record
                        continue;
                    }
                    line.append("|").append(t.getDue().toString());
                } else if (t.getType() == Task.TaskType.E) {
                    if (t.getDue() == null || t.getEnd() == null) {
                        // strictly dates only; skip writing invalid record
                        continue;
                    }
                    line.append("|").append(t.getDue().toString())
                            .append("|").append(t.getEnd().toString());
                }

                w.write(line.toString());
                w.newLine();
            }
        }
    }

    private static LocalDate tryParse(String s) {
        try {
            return LocalDate.parse(s);
        } catch (Exception e) {
            return null;
        }
    }
}
