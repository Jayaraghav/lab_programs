import java.util.Scanner;

public class DiffieHellman {
    static long powerhouse(long base,long exp,long mod){
        long result=1;
        while(exp>0){
            result = (result*base)%mod;
            exp--;
        }
        return result;
    }

    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        long q,alpha;
        long XA,XB;
        long YA,YB;
        long kA,kB;
        System.out.print("Enter Prime number(q): ");
        q = sc.nextLong();
        System.out.print("Enter Primitive root of p: ");
        alpha = sc.nextLong();
        System.out.print("Enter private key of A: ");
        XA=sc.nextLong();
        System.out.print("Enter private key of B: ");
        XB=sc.nextLong();
        YA = powerhouse(alpha,XA,q);
        YB = powerhouse(alpha,XB,q);
        System.out.println("Public Key of A: "+YA);
        System.out.println("Public Ket of B: "+YB);
        kA=powerhouse(YA,XB,q);
        kB=powerhouse(YB,XA,q);
        if(kA==kB){
            System.out.print("Shared key is: "+kA);
        }else {
            System.out.print("Shared key is not same!");
        }
    }

}
