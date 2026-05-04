import java.util.*;

public class RowColumnCipher {

    // Get order of columns based on key
    static int[] getOrder(String key) {
        int n = key.length();
        int[] order = new int[n];

        char[] keyArr = key.toCharArray();
        char[] sorted = key.toCharArray();
        Arrays.sort(sorted);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (keyArr[i] == sorted[j]) {
                    order[i] = j;
                    sorted[j] = '*'; // mark used
                    break;
                }
            }
        }
        return order;
    }

    // Encryption
    static String encrypt(String text, String key) {

        text = text.replaceAll("\\s+", "").toUpperCase();
        key = key.toUpperCase();

        int cols = key.length();
        int rows = (int) Math.ceil((double) text.length() / cols);

        char[][] matrix = new char[rows][cols];

        int k = 0;

        // Fill row-wise
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (k < text.length())
                    matrix[i][j] = text.charAt(k++);
                else
                    matrix[i][j] = 'X'; // padding
            }
        }

        int[] order = getOrder(key);

        StringBuilder cipher = new StringBuilder();

        // Read column-wise based on order
        for (int num = 0; num < cols; num++) {
            for (int j = 0; j < cols; j++) {
                if (order[j] == num) {
                    for (int i = 0; i < rows; i++) {
                        cipher.append(matrix[i][j]);
                    }
                }
            }
        }

        return cipher.toString();
    }

    // Decryption
    static String decrypt(String cipher, String key) {

        cipher = cipher.toUpperCase();
        key = key.toUpperCase();

        int cols = key.length();
        int rows = cipher.length() / cols;

        char[][] matrix = new char[rows][cols];

        int[] order = getOrder(key);

        int k = 0;

        // Fill column-wise based on order
        for (int num = 0; num < cols; num++) {
            for (int j = 0; j < cols; j++) {
                if (order[j] == num) {
                    for (int i = 0; i < rows; i++) {
                        matrix[i][j] = cipher.charAt(k++);
                    }
                }
            }
        }

        StringBuilder plain = new StringBuilder();

        // Read row-wise
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                plain.append(matrix[i][j]);
            }
        }

        return plain.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Plain Text: ");
        String text = sc.nextLine();

        System.out.print("Enter Key: ");
        String key = sc.nextLine();

        String cipher = encrypt(text, key);
        System.out.println("Encrypted Text: " + cipher);

        String plain = decrypt(cipher, key);
        System.out.println("Decrypted Text: " + plain);

        sc.close();
    }
}