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
        ArrayList<Boolean> al2 = new ArrayList<>();
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
                    System.out.printf("     %d.[%s] %s\n", i+1, 
                        al2.get(i) ? "X": " ",  al.get(i));
                }
                System.out.print(m);
            } else if(in.split(" ")[0].equals("mark")) {
                int n = Integer.parseInt(in.split(" ")[1]) - 1;
                al2.set(n, true);
                System.out.print(m);
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       [X] " + al.get(n));
                System.out.print(m);
                
            } else if(in.split(" ")[0].equals("unmark")) {
                int n = Integer.parseInt(in.split(" ")[1]) - 1;
                al2.set(n, false);
                System.out.print(m);
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       [ ] " + al.get(n));
                System.out.print(m);
                
            } else {
                al.add(in);
                al2.add(false);
                System.out.print(m);
                System.out.println("     added: "+in);
                System.out.print(m);
            }
        }
    }
}
