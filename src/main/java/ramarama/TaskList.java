package ramarama;

import java.util.ArrayList;
import java.util.List;

/*
 * List of tasks.
 */
class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /*
     * Empty TaskList.
     */
    TaskList() {
    }

    /*
     * TaskList from List<Task>.
     */
    TaskList(List<Task> loaded) {
        assert loaded != null;
        tasks.addAll(loaded);
    }

    List<Task> asList() {
        return java.util.Collections.unmodifiableList(tasks);
    }

    /*
     * Returns length of TaskList.
     */
    int size() {
        return tasks.size();
    }

    /*
     * Gets Task i of TaskList.
     */
    Task get(int i) {
        Task t = tasks.get(i);
        assert t != null;
        return t;
    }

    /*
     * Adds Task t to TaskList.
     */
    void add(Task t) {
        assert t != null;
        tasks.add(t);
    }

    /*
     * Removes Task i of TaskList.
     */
    Task remove(int i) {
        return tasks.remove(i);
    }
}
