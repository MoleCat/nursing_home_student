package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256 {
    public static String getSecurePassword(String password, String username) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //md.update(salt);

            byte[] password_bytes = password.getBytes();
            byte[] username_bytes = username.getBytes();

            byte[] total = new byte[password_bytes.length + username_bytes.length];

            for (int i = 0; i < password_bytes.length; i++) {
                total[i] = password_bytes[i];
            }

            for (int i = 0; i < username_bytes.length; i++) {
                total[password_bytes.length + i] = username_bytes[i];
            }

            byte[] bytes = md.digest(total);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}