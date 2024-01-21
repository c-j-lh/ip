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
        String added = "____________________________________________________________\n" +
                " Got it. I've added this task:\n" +
                "  %s\n" +
                " Now you have %d tasks in the list.\n" +
                "____________________________________________________________\n";
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
                        System.out.printf(" %s\n", task.toString());
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
                case "todo":
                    Task t = new ToDo(parts[1]);
                    tasks.add(t);
                    System.out.printf(added, t, tasks.size());
                    break;
                case "deadline":
                    String[] deadlineParts = parts[1].split(" /by ", 2);
                    Task d = new Deadline(deadlineParts[0], deadlineParts[1]);
                    tasks.add(d);
                    System.out.printf(added, d, tasks.size());
                    break;
                case "event":
                    String[] eventParts = parts[1].split(" /from ", 2);
                    String[] fromTo = eventParts[1].split(" /to ", 2);
                    Event event = new Event(eventParts[0], fromTo[0], fromTo[1]);
                    tasks.add(event);
                    System.out.printf(added, event, tasks.size());
                    break;
                default:
                    // Handle unknown commands or provide help
                    System.out.println("I'm sorry, I don't know what that means :-(");
            }
        }
    }

    static class Task {
        private String description;
        private boolean isMarked;

        public Task(String description) {
            this.description = description;
            this.isMarked = false;
        }

        public String getDescription() {
            return description;
        }

        public String toString() {
            return String.format("[%s] %s", isMarked ? "X" : " ", description);
        }

        public boolean isMarked() {
            return isMarked;
        }

        public void setMarked(boolean marked) {
            isMarked = marked;
        }
    }

    static class ToDo extends Task {
        public ToDo(String description) {
            super(description);
        }

        @Override
        public String toString() {
            return "[T]" + super.toString();
        }
    }

    static class Deadline extends Task {
        protected String by;

        public Deadline(String description, String by) {
            super(description);
            this.by = by;
        }

        @Override
        public String toString() {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    static class Event extends Task {
        protected String from;
        protected String to;

        public Event(String description, String from, String to) {
            super(description);
            this.from = from;
            this.to = to;
        }

        @Override
        public String toString() {
            return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
        }
    }
}