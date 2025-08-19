import java.util.Scanner;
import java.util.ArrayList;

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
                        
        ArrayList<String> al = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        System.out.println(logo);
        while(true){
            String in = s.nextLine();
            if(in.equals("bye")) {
                System.out.println(end);
                break;
            } else if(in.equals("list")) {
                System.out.print(m);
                for (int i = 0; i < al.size(); i++) {
                    System.out.printf("     %d. %s\n", i+1, al.get(i));
                }
                System.out.print(m);
            } else {
                al.add(in);
                System.out.print(m);
                System.out.println("     added: "+in);
                System.out.print(m);
            }
        }
    }
}
