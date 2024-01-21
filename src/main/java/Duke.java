import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    static ArrayList<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        String greet = "____________________________________________________________\n" +
                " Hello! I'm Jamie\n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n";
        String bar = "____________________________________________________________";
        System.out.println(greet);

        Scanner in = new Scanner(System.in);
        while (true) {
            String input = in.nextLine();
            String[] parts = input.split(" ", 2);
            String command = parts[0];

            switch (command) {
                case "bye":
                    System.out.println(bar + "\nBye. Hope to see you again soon!\n" + bar);
                    return;
                case "list":
                    System.out.println(bar + "\n Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        Task task = tasks.get(i);
                        System.out.printf("%d.[%s] %s\n", i + 1, task.isMarked() ? "X" : " ", task.getDescription());
                    }
                    System.out.println(bar);
                    break;
                case "mark":
                    int markIndex = Integer.parseInt(parts[1]) - 1;
                    tasks.get(markIndex).setMarked(true);
                    System.out.println(bar + "\n Nice! I've marked this task as done:\n   [X] " + tasks.get(markIndex).getDescription() + "\n" + bar);
                    break;
                case "unmark":
                    int unmarkIndex = Integer.parseInt(parts[1]) - 1;
                    tasks.get(unmarkIndex).setMarked(false);
                    System.out.println(bar + "\n OK, I've marked this task as not done yet:\n   [ ] " + tasks.get(unmarkIndex).getDescription() + "\n" + bar);
                    break;
                default:
                    Task newTask = new Task(parts[0], false);
                    tasks.add(newTask);
                    System.out.println(bar + "\n added: " + newTask.getDescription() + "\n" + bar);
            }
        }
    }

    static class Task {
        private String description;
        private boolean isMarked;

        public Task(String description, boolean isMarked) {
            this.description = description;
            this.isMarked = isMarked;
        }

        public String getDescription() {
            return description;
        }

        public boolean isMarked() {
            return isMarked;
        }

        public void setMarked(boolean marked) {
            isMarked = marked;
        }
    }
}