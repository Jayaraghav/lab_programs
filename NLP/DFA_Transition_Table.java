import java.util.Scanner;

public class DFA_Transition_Table {

    enum State{
        q0,
        q1,
        q2
    }

    static State [][] transition = new State[3][2];

    static int symbol_Index(char a){
        if(a=='a') return 0;
        else if(a=='b') return 1;
        return -1;
    }

    static boolean Validate(String s){
        State current = State.q0;
        for(char c:s.toCharArray()){
            int symbol = symbol_Index(c);
            if(symbol==-1){
                current = State.q0;
                continue;
            }
            current = transition[current.ordinal()][symbol];
        }
        return current == State.q2;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("The states are:");

        for(State s:State.values()){
            System.out.print(s+"\t");
        }

        System.out.println("\nEnter the transition table values: ");
        System.out.println("Enter in this format State_on_a State_on_b");

        for(State s:State.values()){
            System.out.print(s+": ");
            String a = scan.next();
            String b = scan.next();
            transition[s.ordinal()][0] = State.valueOf(a);
            transition[s.ordinal()][1] = State.valueOf(b);
        }
        scan.nextLine();
        System.out.println("\nThe transition table:");
        System.out.println("State\ta\tb");
        for(State s: State.values()){
            if(s == State.q0){ System.out.println("->"+s+"\t"+transition[s.ordinal()][0]+"\t"+transition[s.ordinal()][1]);}
            else if(s==State.q2){ System.out.println("*"+s+"\t\t"+transition[s.ordinal()][0]+"\t"+transition[s.ordinal()][1]); }
            else{
                System.out.println(s+"\t\t"+transition[s.ordinal()][0]+"\t"+transition[s.ordinal()][1]);
            }

        }

        while (true) {
            System.out.print("\nEnter the String to Validate (Enter break to stop): ");
            String s = scan.nextLine();
            if(s.equalsIgnoreCase("break")){
                System.out.println("Thank you!");
                break;}
            if(Validate(s)){
                System.out.println(s+" String accepted");
            }else{
                System.out.println(s+" String Rejected");
            }
        }
        scan.close();
    }
}
