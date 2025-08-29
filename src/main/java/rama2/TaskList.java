package rama2;

import java.util.ArrayList;
import java.util.List;

class TaskList {
    final ArrayList<Task> tasks = new ArrayList<>();

    TaskList() {
    }

    TaskList(List<Task> loaded) {
        tasks.addAll(loaded);
    }

    int size() {
        return tasks.size();
    }

    Task get(int i) {
        return tasks.get(i);
    }

    void add(Task t) {
        tasks.add(t);
    }

    Task remove(int i) {
        return tasks.remove(i);
    }
}
