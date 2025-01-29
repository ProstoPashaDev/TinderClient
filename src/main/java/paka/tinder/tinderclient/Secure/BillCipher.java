package paka.tinder.tinderclient.Secure;

import java.io.*;
import java.lang.reflect.Array;
import java.security.*;
import java.util.Arrays;
import javax.crypto.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;

import jakarta.xml.bind.DatatypeConverter;

import static java.lang.Math.min;

public class BillCipher {
    private static final String RSA = "RSA";
    private static final int KEY_SIZE = 256;
    private static final int MAX_MESSAGE_SIZE = KEY_SIZE - 11;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public BillCipher() {
    }

    public BillCipher(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PublicKey generateKeys() {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator KPGenerator = null;
        PublicKey publicKey = null;
        try {
            KPGenerator = KeyPairGenerator.getInstance(RSA);
            KPGenerator.initialize(KEY_SIZE * 8, secureRandom);
            KeyPair keys = KPGenerator.generateKeyPair();
            this.privateKey = keys.getPrivate();
            publicKey = keys.getPublic();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e);
        }
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String Encrypt(Object object) {
        try {
            byte[] objectBytes = this.ObjectToBytes(object);
            int size = (objectBytes.length + MAX_MESSAGE_SIZE - 1) / MAX_MESSAGE_SIZE;
            byte[] encrypted_msg = new byte[size * KEY_SIZE];
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, this.publicKey);

            for (int i = 0; i < size; i++) {
                cipher.doFinal(objectBytes, i * MAX_MESSAGE_SIZE, min(objectBytes.length - i * MAX_MESSAGE_SIZE, MAX_MESSAGE_SIZE), encrypted_msg, i * KEY_SIZE);
            }
            String cursed_msg = curseByteArray(encrypted_msg);
            return cursed_msg;
        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | NoSuchPaddingException | InvalidKeyException |
                 BadPaddingException | IOException | ShortBufferException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e);
        }
        return null;
    }

    public <T> T Decrypt(String cursed_msg, Class<T> clas) {
        try {
            byte[] uncursed_msg = uncurseByteArray(cursed_msg);

            int size = (uncursed_msg.length + KEY_SIZE - 1) / KEY_SIZE;
            byte[] bytes = new byte[size * MAX_MESSAGE_SIZE];
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, this.privateKey);
            byte[] temp = new byte[KEY_SIZE];

            for (int i = 0; i < size; i++) {
                cipher.doFinal(uncursed_msg, i * KEY_SIZE, KEY_SIZE, temp, 0);
                System.arraycopy(temp, 0, bytes, i * MAX_MESSAGE_SIZE, MAX_MESSAGE_SIZE);
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

    private byte[] ObjectToBytes(Object object) throws IOException {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        ObjectOutputStream ois = new ObjectOutputStream(boas);
        ois.writeObject(object);
        return boas.toByteArray();
    }

    private <T> T BytesToObject(byte[] bytes, Class<T> clas) throws IOException, ClassNotFoundException, ClassCastException {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        T object = (T) in.readObject();
        in.close();
        return object;
    }

    public void printKeys(PublicKey publicKey) {
        System.out.printf("Public key is:%s\n" +
                "Private key is:%s\n", DatatypeConverter.printHexBinary(
                publicKey.getEncoded()), DatatypeConverter.printHexBinary(
                this.privateKey.getEncoded()));
    }

    public static String curseByteArray(byte[] array) {
        StringBuilder cursed_msg = new StringBuilder();
        for (byte b : array) {
            cursed_msg.append((char) (b + 256));
        }
        return cursed_msg.toString();
    }

    public static byte[] uncurseByteArray(String cursedArray) {
        byte[] uncursed_msg = new byte[cursedArray.length()];
        for (int i = 0; i < cursedArray.length(); ++i) {
            uncursed_msg[i] = (byte) (cursedArray.charAt(i) - 256);
        }
        return uncursed_msg;
    }

    // AND NOW AES

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int AES_KEY_LENGTH = 256;
    private static final int GCM_TAG_LENGTH = 128; // размер тега аутентификации в битах
    private static final int GCM_NONCE_LENGTH = 12; // размер nonce в байтах

    public static SecretKey generateKey() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(AES_KEY_LENGTH);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    private static byte[] generateNonce() {
        byte[] nonce = new byte[GCM_NONCE_LENGTH];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    // Шифрование с использованием GCM
    public static String encrypt(String data, SecretKey key) {
        try {
            byte[] nonce = generateNonce();
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);

            byte[] encryptedData = cipher.doFinal(data.getBytes());
            // Объединяем nonce и зашифрованные данные для передачи
            byte[] combined = new byte[nonce.length + encryptedData.length];
            System.arraycopy(nonce, 0, combined, 0, nonce.length);
            System.arraycopy(encryptedData, 0, combined, nonce.length, encryptedData.length);

            return curseByteArray(combined);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e);
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    // Расшифровка GCM
    public static String decrypt(String encryptedData, SecretKey key) {
        try {
            byte[] decoded = uncurseByteArray(encryptedData);

            // Извлекаем nonce из начала данных
            byte[] nonce = new byte[GCM_NONCE_LENGTH];
            byte[] ciphertext = new byte[decoded.length - GCM_NONCE_LENGTH];
            System.arraycopy(decoded, 0, nonce, 0, GCM_NONCE_LENGTH);
            System.arraycopy(decoded, GCM_NONCE_LENGTH, ciphertext, 0, ciphertext.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);

            return new String(cipher.doFinal(ciphertext));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println(e);
            System.out.println(e.getStackTrace());
        }
        return null;
    }
}
