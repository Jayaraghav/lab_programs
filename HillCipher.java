class Matrix{
    static final int Mod = 26;

    static int mod(int no){
        return (((no%Mod)+Mod)%Mod);
    }

    static int detinverse(int num){
        for(int i=1;i<Mod;i++){
            if((num*i)%Mod==1){
                return i;
            }
        }
        return -1;
    }

    static int determinant(int [][]m){
        return m[0][0]*(m[1][1]*m[2][2]-m[2][1]*m[1][2])
                -m[0][1]*(m[1][0]*m[2][2]-m[2][0]*m[1][2])
                +m[0][2]*(m[1][0]*m[2][1]-m[2][0]*m[1][1]);
    }

    static int[][] adjoint(int[][]m){
        int [][] adj = new int[3][3];
        adj[0][0] = m[1][1]*m[2][2]-m[2][1]*m[1][2];
        adj[0][1] = -(m[1][0]*m[2][2]-m[2][0]*m[1][2]);
        adj[0][2] = (m[1][0]*m[2][1]-m[2][0]*m[1][1]);

        adj[1][0] = -(m[0][1]*m[2][2]-m[0][2]*m[2][1]);
        adj[1][1] = (m[0][0]*m[2][2]-m[0][2]*m[2][0]);
        adj[1][2] = -(m[0][0]*m[2][1]-m[0][1]*m[2][0]);

        adj[2][0] = (m[0][1]*m[1][2]-m[0][2]*m[1][1]);
        adj[2][1] = -(m[0][0]*m[1][2]-m[1][0]*m[0][2]);
        adj[2][2] = (m[0][0]*m[1][1]-m[0][1]*m[1][0]);
        return adj;
    }


    static int[][] inverse(int [][] m){
        int det = mod(determinant(m));
        int detinv = detinverse(det);

        if(detinv==-1)return null;
        int[][] adj = adjoint(m);
        int [][] inv = new int[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                inv[i][j] = mod(adj[j][i]*detinv);
            }
        }
        return inv;
    }
    static int[][] multiply(int [][]p,int [][]key){
        int [][] result = new int[1][3];
         for(int i=0;i<3;i++){
             int sum = 0;
            for(int k=0;k<3;k++){
                sum+=p[0][k]*key[k][i];
            }
            result[0][i]=mod(sum);
        }
         return result;
    }
}


public class HillCipher {
    static String pad(String plaintext){
        while(plaintext.length()%3!=0){
            plaintext+='x';
        }
        return plaintext;
    }

    static String encrypt(String plaintext,int[][] key){
        plaintext = (plaintext.length()%3==0)?plaintext.toLowerCase().replaceAll("\\s+",""):pad(plaintext.toLowerCase().replaceAll("\\s+",""));
        StringBuilder cipher = new StringBuilder();
        for(int i=0;i<plaintext.length();i+=3){
            int[][] block = new int[1][3];
            for(int j=0;j<3;j++){
                block[0][j]=plaintext.charAt(i+j)-'a';
            }
            int[][] result = Matrix.multiply(block,key);
            for(int j=0;j<3;j++){
                cipher.append((char)(result[0][j]+'a'));
            }
        }
        return cipher.toString();
    }

    static String decrypt(String ciphertext,int[][]key){
        StringBuilder string = new StringBuilder();
        for(int i=0;i<ciphertext.length();i+=3){
            int[][] block = new int[1][3];
            for (int j=0;j<3;j++){
                block[0][j]=ciphertext.charAt(i+j)-'a';
            }
            int[][] result = Matrix.multiply(block,key);
            for (int k=0;k<3;k++){
                string.append((char)(result[0][k]+'a'));
            }
        }
        return string.toString();
    }

    public static void main(String args[]){
        int[][] key = {
                {6,24,1},
                {13,16,10},
                {20,17,15}
        };
        String text = "Jayaraghav";
        int [][] inverse = Matrix.inverse(key);
        if(inverse==null)System.out.println("Enter proper matrix");
        String ciphertext = encrypt(text,key);
        System.out.println("Ciphertext:"+ciphertext);
        System.out.println("Decrypted text: "+decrypt(ciphertext,inverse));
    }
}
