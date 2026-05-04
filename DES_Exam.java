import java.util.*;

public class DES_Exam {

    // Initial Permutation
    static int[] IP = {58,50,42,34,26,18,10,2,
            60,52,44,36,28,20,12,4,
            62,54,46,38,30,22,14,6,
            64,56,48,40,32,24,16,8,
            57,49,41,33,25,17,9,1,
            59,51,43,35,27,19,11,3,
            61,53,45,37,29,21,13,5,
            63,55,47,39,31,23,15,7};

    // Final Permutation
    static int[] FP = {40,8,48,16,56,24,64,32,
            39,7,47,15,55,23,63,31,
            38,6,46,14,54,22,62,30,
            37,5,45,13,53,21,61,29,
            36,4,44,12,52,20,60,28,
            35,3,43,11,51,19,59,27,
            34,2,42,10,50,18,58,26,
            33,1,41,9,49,17,57,25};

    // Expansion table
    static int[] E = {
            32,1,2,3,4,5,4,5,6,7,8,9,
            8,9,10,11,12,13,12,13,14,15,16,17,
            16,17,18,19,20,21,20,21,22,23,24,25,
            24,25,26,27,28,29,28,29,30,31,32,1};

    // P permutation
    static int[] P = {
            16,7,20,21,29,12,28,17,
            1,15,23,26,5,18,31,10,
            2,8,24,14,32,27,3,9,
            19,13,30,6,22,11,4,25};

    // ONE SBOX (you can expand later if needed)
    static int[][][] SBOX = {
            {
                    {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
                    {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
                    {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
                    {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13}
            }
    };

    // Convert string to binary
    static String toBinary(String s) {
        String res = "";
        for(char c: s.toCharArray())
            res += String.format("%8s", Integer.toBinaryString(c)).replace(' ','0');
        return res;
    }

    // Permutation
    static String permute(String input, int[] table) {
        String res = "";
        for(int i: table)
            res += input.charAt(i-1);
        return res;
    }

    // XOR
    static String xor(String a, String b) {
        String res = "";
        for(int i=0;i<a.length();i++)
            res += (a.charAt(i)==b.charAt(i))?'0':'1';
        return res;
    }

    // Simple SBOX (using only first box for exam)
    static String sbox(String input) {
        String out = "";
        for(int i=0;i<8;i++){
            String block = input.substring(i*6,i*6+6);
            int row = Integer.parseInt(""+block.charAt(0)+block.charAt(5),2);
            int col = Integer.parseInt(block.substring(1,5),2);
            int val = SBOX[0][row][col]; // reuse same box
            out += String.format("%4s",Integer.toBinaryString(val)).replace(' ','0');
        }
        return out;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter 8-char text: ");
        String text = sc.nextLine();

        String bin = toBinary(text);

        // Initial Permutation
        bin = permute(bin, IP);

        String L = bin.substring(0,32);
        String R = bin.substring(32);

        // Dummy key (same for all rounds for simplicity)
        String key = "111100001111000011110000111100001111000011110000";

        for(int i=0;i<16;i++){
            String expanded = permute(R,E);
            String x = xor(expanded,key);
            String s = sbox(x);
            String p = permute(s,P);

            String newR = xor(L,p);
            L = R;
            R = newR;
        }

        String combined = R + L;

        String cipher = permute(combined,FP);

        System.out.println("Encrypted Binary: "+cipher);
    }
}