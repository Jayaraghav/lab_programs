class Matrix1{
    static final int Mod = 26;

    static int detinverse(int num){
        for(int i=0;i<Mod;i++){
            if((num*i)%Mod == 1){
                return i;
            }
        }
        return -1;
    }
    static int mod(int num){
        return (num%Mod+Mod)%Mod;
    }

    static int determinant(int[][]m){
        return m[0][0]*(m[1][1]*m[2][2]-m[2][1]*m[1][2])
                -m[0][1]*(m[1][0]*m[2][2]-m[2][0]*m[1][2])
                +m[0][2]*(m[1][0]*m[2][1]-m[2][0]*m[1][1]);
    }

    static int[][] adjoint(int[][]m){
        int[][]adj = new int[3][3];
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
    static int[][] inverse(int[][]m){
        int [][]result = new int[3][3];
        int det = mod(determinant(m));
        int detinv = detinverse(det);

        if(detinv==-1){
            return null;
        }
        int [][]adj = adjoint(m);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                result[i][j] = mod(adj[j][i]*detinv);
            }
        }
        return result;
    }
    static int[][] multiply(int block[][],int key[][]){
        int result[][] = new int [1][3];
        for(int i=0;i<3;i++){
            int sum = 0;
            for(int k=0;k<3;k++){
                sum+=block[0][k]*key[k][i];
            }
            result[0][i]=mod(sum);
        }
        return result;
    }
}

public class HillCipher2 {
    static String pad(String pt){
        while(pt.length()%3!=0){
            pt+='x';
        }
        return pt;
    }

    static String encrypt(String pt,int[][]key){
        pt = (pt.length()%3==0)?pt.toLowerCase().replaceAll("\\s+",""):pad(pt.toLowerCase().replaceAll("\\s+",""));
        StringBuilder cipher = new StringBuilder();
        for(int i=0;i<pt.length();i+=3){
            int block[][] = new int[1][3];
            for(int j=0;j<3;j++){
                block[0][j] = (pt.charAt(i+j)-'a');
            }
            int result[][]=Matrix1.multiply(block,key);
            for(int j=0;j<3;j++){
                cipher.append((char) (result[0][j]+'a'));
            }
        }
        return cipher.toString();
    }
    static String decrypt(String cipher,int[][]key){
        StringBuilder decrypt = new StringBuilder();
        for(int i=0;i<cipher.length();i+=3){
            int[][] block = new int[1][3];
            for(int j=0;j<3;j++){
                block[0][j]=(cipher.charAt(i+j)-'a');
            }
            int result[][]=Matrix1.multiply(block,key);
            for(int j=0;j<3;j++){
                decrypt.append((char) (result[0][j]+'a'));
            }
        }
        return decrypt.toString();
    }
    public static void main(String args[]){
        int[][] key = {
                {6,24,1},
                {13,16,10},
                {20,17,15}
        };
        String text = "Jayaraghav";
        int [][] inverse = Matrix1.inverse(key);
        if(inverse==null)System.out.println("Enter proper matrix");
        String ciphertext = encrypt(text,key);
        System.out.println("Ciphertext:"+ciphertext);
        System.out.println("Decrypted text: "+decrypt(ciphertext,inverse));
    }
}
