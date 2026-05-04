import java.util.*;

public class PlayfairCipher {

    private char[][] matrix = new char[5][5];
    private HashMap<Character, int[]> indexMap = new HashMap<>();

    // Generate Key Matrix
    public void generateKeyTable(String key) {
        key = key.toLowerCase().replace(" ", "");
        LinkedHashSet<Character> set = new LinkedHashSet<>();

        for (char c : key.toCharArray()) {
            if (c == 'j') c = 'i';
            set.add(c);
        }

        for (char c = 'a'; c <= 'z'; c++) {
            if (c == 'j') continue;
            set.add(c);
        }

        Iterator<Character> it = set.iterator();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = it.next();
                indexMap.put(matrix[i][j], new int[]{i, j});
            }
        }
    }

    // Encryption
    public String encrypt(String text) {
        text = text.toLowerCase().replace(" ", "").replace("j", "i");
        StringBuilder plain = new StringBuilder(text);

        // Insert 'x' between repeated letters
        for (int i = 0; i < plain.length() - 1; i += 2) {
            if (plain.charAt(i) == plain.charAt(i + 1)) {
                plain.insert(i + 1, 'x');
            }
        }

        // Make even length
        if (plain.length() % 2 != 0) {
            plain.append('z');
        }

        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < plain.length(); i += 2) {
            char a = plain.charAt(i);
            char b = plain.charAt(i + 1);

            int[] posA = indexMap.get(a);
            int[] posB = indexMap.get(b);

            // Same row
            if (posA[0] == posB[0]) {
                cipher.append(matrix[posA[0]][(posA[1] + 1) % 5]);
                cipher.append(matrix[posB[0]][(posB[1] + 1) % 5]);
            }
            // Same column
            else if (posA[1] == posB[1]) {
                cipher.append(matrix[(posA[0] + 1) % 5][posA[1]]);
                cipher.append(matrix[(posB[0] + 1) % 5][posB[1]]);
            }
            // Rectangle rule
            else {
                cipher.append(matrix[posA[0]][posB[1]]);
                cipher.append(matrix[posB[0]][posA[1]]);
            }
        }

        return cipher.toString();
    }

    // Decryption
    public String decrypt(String cipherText) {
        cipherText = cipherText.toLowerCase().replace(" ", "");
        StringBuilder plain = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i += 2) {
            char a = cipherText.charAt(i);
            char b = cipherText.charAt(i + 1);

            int[] posA = indexMap.get(a);
            int[] posB = indexMap.get(b);

            // Same row
            if (posA[0] == posB[0]) {
                plain.append(matrix[posA[0]][(posA[1] + 4) % 5]);
                plain.append(matrix[posB[0]][(posB[1] + 4) % 5]);
            }
            // Same column
            else if (posA[1] == posB[1]) {
                plain.append(matrix[(posA[0] + 4) % 5][posA[1]]);
                plain.append(matrix[(posB[0] + 4) % 5][posB[1]]);
            }
            // Rectangle rule
            else {
                plain.append(matrix[posA[0]][posB[1]]);
                plain.append(matrix[posB[0]][posA[1]]);
            }
        }

        return plain.toString();
    }

    // Display Matrix
    public void printMatrix() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Main Method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PlayfairCipher pf = new PlayfairCipher();

        System.out.print("Enter Key: ");
        String key = sc.nextLine();

        pf.generateKeyTable(key);
        System.out.println("\nKey Matrix:");
        pf.printMatrix();

        System.out.print("\nEnter Plain Text: ");
        String plain = sc.nextLine();

        String encrypted = pf.encrypt(plain);
        System.out.println("Encrypted: " + encrypted);

        String decrypted = pf.decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}