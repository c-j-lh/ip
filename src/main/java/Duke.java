import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    static ArrayList<String> list = new ArrayList<>();
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String greet = "____________________________________________________________\n" +
                " Hello! I'm Jamie\n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n";
        String bar="____________________________________________________________";
        System.out.println(greet);

        Scanner in = new Scanner(System.in);;
        while (true) {
            String inp=in.next();
            if (inp.equals("bye")) {
                System.out.println("____________________________________________________________\n" +
                        "Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________");
                break;
            }
            if (inp.equals("list")) {
                System.out.println(bar);
                for (int i = 0; i < list.size(); i++) {
                    System.out.printf("%d. %s\n", i+1, list.get(i));
                }
                System.out.println(bar);
            } else {
                list.add(inp);
                System.out.println(bar);
                System.out.printf("added: %s\n", inp);
                System.out.println(bar);
            }
            //inp = in.next();
        }
    }
}
