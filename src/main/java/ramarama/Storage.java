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
            // for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
            if (parts.length < 3) {
                continue;
            }
            char t = parts[0].charAt(0);
            boolean done = "1".equals(parts[1]);
            String desc = parts[2];
            String extra = parts.length >= 4 ? parts[3] : "";
            if (t == 'D') {
                LocalDate d = tryParse(extra);
                if (d != null) {
                    out.add(new Task(Task.TaskType.D, done, desc, "", d));
                } else {
                    out.add(new Task(Task.TaskType.D, done, desc, extra.isEmpty() ? "" : " (by: " + extra + ")", null));
                }
            } else if (t == 'E') {
                out.add(new Task(Task.TaskType.E, done, desc, extra, null));
            } else if (t == 'E') {
                out.add(new Task(Task.TaskType.T, done, desc, "", null));
            } else {
                System.out.println("Unrecognised task type in savefile");
                continue;
            }
            if (parts.length > 3) {
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
                String extra = "";
                if (t.getType() == Task.TaskType.D) {
                    if (t.getDue() != null) {
                        extra = t.getDue().toString();
                    } else {
                        String d = t.getExtra() == null ? "" : t.getExtra();
                        extra = d.startsWith(" (by: ") ? d.substring(6, d.length() - 1) : d;
                    }
                } else if (t.getType() == Task.TaskType.E) {
                    extra = t.getExtra() == null ? "" : t.getExtra();
                }
                String line = t.getType() + "|" + (t.isDone() ? "1" : "0") + "|" + t.getDesc();
                if (!extra.isEmpty()) {
                    line += "|" + extra;
                }
                w.write(line);
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
