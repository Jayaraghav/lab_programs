import java.util.*;

enum State{
    q0,
    q1,
    q2
}

public class DFA {

    static void print_Transition_Table(){
        System.out.println("\nTransition Table for the language that doesn't allow two consecutive 1s over  binary symbols");
        System.out.println("_".repeat(21));
        System.out.println("|\t\t"+"0"+"\t\t"+"1\t"+"|");
        System.out.println("|->*q0\t"+State.q0+"\t\t"+State.q1+"\t|");
        System.out.println("|*q1\t"+State.q0+"\t\t"+State.q2+"\t|");
        System.out.println("|q2\t\t"+State.q2+"\t\t"+State.q2+"\t|");
        System.out.println("_".repeat(21));
    }

    static State transition(State current, char input){
        switch (current){
            case q0 -> {
                if(input == '0') return State.q0;
                if(input == '1') return State.q1;
            }
            case q1 ->{
                if (input == '0') return State.q0;
                if (input == '1') return State.q2;
            }
            case q2 ->{
                return State.q2;
            }
        }
        return State.q2;
    }

    static boolean isAccepted(String s){
        State state = State.q0;
        for(char c:s.toCharArray()){
            state = transition(state,c);
        }
        return state == State.q0 || state == State.q1;
    }

    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        print_Transition_Table();
        while(true){
            System.out.print("\nEnter the String to Validate (Enter break to stop): ");
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("break")){
                System.out.println("Thank you!");
                break;
            }
            System.out.println("The entered String "+s+" is "+(isAccepted(s)?"accepted":"rejected because it contains consecutive ones"));
        }
    }

}
