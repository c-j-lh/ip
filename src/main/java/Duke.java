import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String logo = "    ____________________________________________________________\r\n" + //
                        "     Hello! I'm Rama2\r\n" + //
                        "     What can I do for you?\r\n" + //
                        "    ____________________________________________________________\r\n";
                        
        String end =    "    ____________________________________________________________\r\n" + //
                        "     Bye. Hope to see you again soon!\r\n" + //
                        "    ____________________________________________________________";
        String m = "    ____________________________________________________________\r\n";
                        
        Scanner s = new Scanner(System.in);
        System.out.println(logo);
        while(true){
            String in = s.nextLine();
            if(in.equals("bye")) {
                System.out.println(end);
                break;
            } else {
                System.out.print(m);
                System.out.println("     "+in);
                System.out.print(m);
            }
        }
    }
}
