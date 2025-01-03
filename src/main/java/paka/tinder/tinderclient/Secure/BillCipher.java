package paka.tinder.tinderclient.Secure;
import java.io.*;
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
    public String Encrypt(Object object){
        byte[] encrypted_msg = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE,this. publicKey);
            encrypted_msg = cipher.doFinal(this.ObjectToBytes(object));
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IOException e) {
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
    public <T> T Decrypt(String cursed_msg,Class<T> clas){
        byte[] uncursed_msg = new byte[cursed_msg.length()];
        for (int i=0; i<cursed_msg.length();++i) {
            uncursed_msg[i]=(byte)(cursed_msg.charAt(i)-256);
        }
        T result = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
            result = this.BytesToObject(cipher.doFinal(uncursed_msg),clas);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | IOException | ClassNotFoundException e) {
            System.out.println(e.getCause());
            System.out.println(e.fillInStackTrace());
            System.out.println(e);
        }
        return result;
    }
    public byte[] ObjectToBytes(Object object) throws IOException {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        ObjectOutputStream ois = new ObjectOutputStream(boas);
        ois.writeObject(object);
        return boas.toByteArray();
    }
    public <T> T BytesToObject(byte[] bytes, Class<T> clas) throws IOException, ClassNotFoundException, ClassCastException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        T object = (T) in.readObject();
        in.close();
        return object;
    }
    public void printKeys(PublicKey publicKey){
        System.out.printf("Public key is:%s\n" +
                "Private key is:%s\n", DatatypeConverter.printHexBinary(
                publicKey.getEncoded()), DatatypeConverter.printHexBinary(
                this.privateKey.getEncoded()));
    }
}
