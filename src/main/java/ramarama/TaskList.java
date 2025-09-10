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

    List<Task> asList() { 
        return java.util.Collections.unmodifiableList(tasks);
    }


    /*
     * TaskList from List<Task>.
     */
    TaskList(List<Task> loaded) {
        tasks.addAll(loaded);
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
        return tasks.get(i);
    }

    /*
     * Adds Task t to TaskList.
     */
    void add(Task t) {
        tasks.add(t);
    }

    /*
     * Removes Task i of TaskList.
     */
    Task remove(int i) {
        return tasks.remove(i);
    }
}
