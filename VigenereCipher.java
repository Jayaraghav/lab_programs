import java.util.*;

public class VigenereCipher {

    HashMap<Character, Integer> alphaToNumeric = new HashMap<>();
    HashMap<Integer, Character> numericToAlpha = new HashMap<>();

    // Assign a=0, b=1 ... z=25
    public void assignAlphabets() {
        for (int i = 0; i < 26; i++) {
            char ch = (char) ('a' + i);
            alphaToNumeric.put(ch, i);
            numericToAlpha.put(i, ch);
        }
    }

    // Encryption
    public String encrypt(String plainText, String key) {
        StringBuilder cipher = new StringBuilder();

        for (int i = 0; i < plainText.length(); i++) {

            char p = plainText.charAt(i);

            // Skip spaces
            if (!Character.isLetter(p)) {
                cipher.append(p);
                continue;
            }

            boolean isUpper = Character.isUpperCase(p);

            char pLower = Character.toLowerCase(p);
            char kLower = Character.toLowerCase(key.charAt(i % key.length()));

            int value = (alphaToNumeric.get(pLower) + alphaToNumeric.get(kLower)) % 26;

            char c = numericToAlpha.get(value);

            cipher.append(isUpper ? Character.toUpperCase(c) : c);
        }

        return cipher.toString();
    }

    // Decryption
    public String decrypt(String cipherText, String key) {
        StringBuilder plain = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i++) {

            char c = cipherText.charAt(i);

            // Skip spaces
            if (!Character.isLetter(c)) {
                plain.append(c);
                continue;
            }

            boolean isUpper = Character.isUpperCase(c);

            char cLower = Character.toLowerCase(c);
            char kLower = Character.toLowerCase(key.charAt(i % key.length()));

            int value = (alphaToNumeric.get(cLower) - alphaToNumeric.get(kLower) + 26) % 26;

            char p = numericToAlpha.get(value);

            plain.append(isUpper ? Character.toUpperCase(p) : p);
        }

        return plain.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        VigenereCipher vc = new VigenereCipher();

        vc.assignAlphabets();

        System.out.print("Enter Plain Text: ");
        String plainText = sc.nextLine();

        System.out.print("Enter Key: ");
        String key = sc.nextLine();

        String cipherText = vc.encrypt(plainText, key);

        System.out.println("\nEncrypted Text: " + cipherText);
        System.out.println("Decrypted Text: " + vc.decrypt(cipherText, key));

        sc.close();
    }
}