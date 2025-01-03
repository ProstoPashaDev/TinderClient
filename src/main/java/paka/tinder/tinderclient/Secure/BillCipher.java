package paka.tinder.tinderclient.Secure;
import java.security.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import jakarta.xml.bind.DatatypeConverter;

public class BillCipher {
    private static final String RSA = "RSA";
    private static final int KEY_SIZE = 8*256;
    private static final int MAX_MESSAGE_SIZE = KEY_SIZE-12*8;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public BillCipher(){
    }
    public BillCipher(PublicKey publicKey){
        this.publicKey = publicKey;
    }
    public PublicKey generateKeys(){
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator KPGenerator = null;
        PublicKey publicKey = null;
        try {
            KPGenerator = KeyPairGenerator.getInstance(RSA);
            KPGenerator.initialize(KEY_SIZE, secureRandom);
            KeyPair keys = KPGenerator.generateKeyPair();
            this.privateKey = keys.getPrivate();
            publicKey = keys.getPublic();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getCause());
            System.out.println(e.fillInStackTrace());
            System.out.println(e);
        }
        return publicKey;
    }
    public void setPublicKey(PublicKey publicKey){
        this.publicKey = publicKey;
    }
    public String Encrypt(String raw_text){
        byte[] encrypted_msg = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE,this. publicKey);
            encrypted_msg = cipher.doFinal(raw_text.getBytes());
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException e) {
            System.out.println(e.getCause());
            System.out.println(e.fillInStackTrace());
            System.out.println(e);
        }
        StringBuilder cursed_msg = new StringBuilder();
        for (byte b : encrypted_msg) {
            cursed_msg.append((char) (b + 256));
        }
        return cursed_msg.toString();
    }
    public String Decrypt(String cursed_msg){
        byte[] uncursed_msg = new byte[cursed_msg.length()];
        for (int i=0; i<cursed_msg.length();++i) {
            uncursed_msg[i]=(byte)(cursed_msg.charAt(i)-256);
        }
        String result = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
            result = new String(cipher.doFinal(uncursed_msg));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException e) {
            System.out.println(e.getCause());
            System.out.println(e.fillInStackTrace());
            System.out.println(e);
        }
        return result;
    }
    public void printKeys(PublicKey publicKey){
        System.out.printf("Public key is:%s\n" +
                "Private key is:%s\n", DatatypeConverter.printHexBinary(
                publicKey.getEncoded()), DatatypeConverter.printHexBinary(
                this.privateKey.getEncoded()));
    }
}
