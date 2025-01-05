package paka.tinder.tinderclient.Secure;
import java.io.*;
import java.lang.reflect.Array;
import java.security.*;
import java.util.Arrays;
import javax.crypto.*;

import jakarta.xml.bind.DatatypeConverter;

import static java.lang.Math.min;

public class BillCipher {
    private static final String RSA = "RSA";
    private static final int KEY_SIZE = 256;
    private static final int MAX_MESSAGE_SIZE = KEY_SIZE-11;
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
            KPGenerator.initialize(KEY_SIZE*8, secureRandom);
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
        try {
            byte[] objectBytes = this.ObjectToBytes(object);
            int size = (objectBytes.length+MAX_MESSAGE_SIZE-1)/MAX_MESSAGE_SIZE;
            byte[] encrypted_msg = new byte[size*KEY_SIZE];
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE,this. publicKey);

            for (int i = 0; i < size; i++) {
                cipher.doFinal(objectBytes,i*MAX_MESSAGE_SIZE,min(objectBytes.length-i*MAX_MESSAGE_SIZE,MAX_MESSAGE_SIZE),encrypted_msg,i*KEY_SIZE);
            }

            StringBuilder cursed_msg = new StringBuilder();
            for (byte b : encrypted_msg) {
                cursed_msg.append((char) (b + 256));
            }
            return cursed_msg.toString();
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IOException | ShortBufferException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e);
        }
        return null;
    }
    public <T> T Decrypt(String cursed_msg,Class<T> clas){
        try {
            byte[] uncursed_msg = new byte[cursed_msg.length()];
            for (int i=0; i<cursed_msg.length();++i) {
                uncursed_msg[i]=(byte)(cursed_msg.charAt(i)-256);
            }
            int size = (uncursed_msg.length+KEY_SIZE-1)/KEY_SIZE;
            byte[] bytes = new byte[size*MAX_MESSAGE_SIZE];
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
            byte[] temp = new byte[KEY_SIZE];

            for (int i = 0; i < size; i++) {
                cipher.doFinal(uncursed_msg,i*KEY_SIZE,KEY_SIZE,temp,0);
                System.arraycopy(temp,0,bytes,i*MAX_MESSAGE_SIZE,MAX_MESSAGE_SIZE);
            }

            T result = this.BytesToObject(bytes, clas);
            return result;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 BadPaddingException | IOException | ClassNotFoundException | ShortBufferException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e);
        }
        return null;
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
