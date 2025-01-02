package paka.tinder.tinderclient.Secure;
import java.security.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import jakarta.xml.bind.DatatypeConverter;

public class Bill_Cipher {
    private static final String RSA = "RSA";
    private KeyPair keys;

    public Bill_Cipher(){
        try {
            this.generateKeys();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void generateKeys(){
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator KPGenerator = null;
        try {
            KPGenerator = KeyPairGenerator.getInstance(RSA);
            KPGenerator.initialize(2048, secureRandom);
            this.keys = KPGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getCause());
            System.out.println(e.fillInStackTrace());
            System.out.println(e);
        }
    }
    public byte[] Encrypt(String raw_text){
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, keys.getPrivate());
            result = cipher.doFinal(raw_text.getBytes());
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException e) {
            System.out.println(e.getCause());
            System.out.println(e.fillInStackTrace());
            System.out.println(e);
        }
        return result;
    }
    public String Decrypt(byte[] message){
        String result = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, keys.getPublic());
            result = new String(cipher.doFinal(message));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            System.out.println(e.getCause());
            System.out.println(e.fillInStackTrace());
            System.out.println(e);
        }
        return result;
    }
    public void printKeys(){
        System.out.printf("Public key is:%s\n" +
                "Private key is:%s\n", DatatypeConverter.printHexBinary(
                keys.getPublic().getEncoded()), DatatypeConverter.printHexBinary(
                keys.getPrivate().getEncoded()));
    }
}
