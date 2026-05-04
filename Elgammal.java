import java.util.Random;
import java.util.Scanner;

public class Elgammal {

    static int GCD(int a, int b){
        while(b!=0){
            int temp=b;
            b = a%b;
            a=temp;
        }
        return a;
    }

    static int modinverse(int num,int mod){
        for(int i=1;i<mod;i++){
            if((num*i)%mod==1){
                return i;
            }
        }
        return -1;
    }

    static int powerhouse(int base,int exp,int mod){
        int result = 1;
        while(exp>0){
            result = (result*base)%mod;
            exp--;
        }
        return result;
    }

    static int Hash(String message,int mod){
        int h=0;
        for(char c:message.toCharArray()){
            h=(h+c)%mod;
        }
        return h;
    }

    public static void main(String [] args){
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.print("Enter the message: ");
        String message = sc.nextLine();

        System.out.print("Enter the prime number(p): ");
        int p = sc.nextInt();

        System.out.print("Enter generator (g): ");
        int g = sc.nextInt();

        // Private key
        int XA = rand.nextInt(p-2)+1;

        // Public key
        int YA = powerhouse(g,XA,p);

        System.out.println("Public key is (p, g, YA): (" + p + ", " + g + ", " + YA + ")");
        System.out.println("Private key: " + XA);

        int hash = Hash(message,p-1);

        int k;
        do{
            k=rand.nextInt(p-2)+1;
        }while(GCD(k,p-1)!=1);

        int s1 = powerhouse(g,k,p);

        int kinverse = modinverse(k,p-1);

        int s2 = (kinverse*(hash-XA*s1))%(p-1);
        if(s2<0) s2 += (p-1);

        System.out.println("Signature (s1, s2): (" + s1 + ", " + s2 + ")");

        // Verification
        int v1 = powerhouse(g,hash,p);
        int v2 = (powerhouse(YA,s1,p)*powerhouse(s1,s2,p))%p;

        if(v1==v2){
            System.out.println("Signature verified");
        }else{
            System.out.println("Signature not verified");
        }
    }
}