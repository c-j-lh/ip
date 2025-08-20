import java.util.Scanner;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        String logo =   "     Hello! I'm Rama2\n" + //
                        "     What can I do for you?\n";
                        
        String end =    "     Bye. Hope to see you again soon!\n";
        String m = "    ____________________________________________________________";
                        
        ArrayList<String> al = new ArrayList<>();
        ArrayList<Boolean> al2 = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        System.out.println(m + "\n" + logo + m);
        while(true){
            String in = s.nextLine();
            System.out.println(m);
            if(in.equals("bye")) {
                System.out.println(end);
                break;
            } else if (in.equals("list")) {
                for (int i = 0; i < al.size(); i++) {
                    System.out.printf("     %d.[%s] %s\n", i+1, 
                        al2.get(i) ? "X": " ",  al.get(i));
                }
            } else if (in.split(" ")[0].equals("mark")) {
                int n = Integer.parseInt(in.split(" ")[1]) - 1;
                al2.set(n, true);
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       [X] " + al.get(n));
                
            } else if (in.split(" ")[0].equals("unmark")) {
                int n = Integer.parseInt(in.split(" ")[1]) - 1;
                al2.set(n, false);
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       [ ] " + al.get(n));
                
            } else {
                al.add(in);
                al2.add(false);
                System.out.println("     added: " + in);
            }
            System.out.println(m);
        }
    }
}
