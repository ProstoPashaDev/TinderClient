package paka.tinder.tinderclient.Secure;

import java.io.*;
import java.nio.ByteBuffer;
import java.security.*;
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
        try {
            SecureRandom secureRandom = new SecureRandom();
            KeyPairGenerator KPGenerator = KeyPairGenerator.getInstance(RSA);;
            KPGenerator.initialize(KEY_SIZE * 8, secureRandom);
            KeyPair keys = KPGenerator.generateKeyPair();
            this.privateKey = keys.getPrivate();
            return keys.getPublic();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e);
        }
        return null;
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
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES
    // AND NOW AES



    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int NONCE_LENGTH_BYTE = 12;

    private SecretKey key;
    private int tagLengthBit;
    private int nonceLengthByte;

    public BillCipher(SecretKey key) {
        this(key, TAG_LENGTH_BIT, NONCE_LENGTH_BYTE);
    }

    private BillCipher(SecretKey key, int tagLengthBit, int nonceLengthByte) {
        this.key = key;
        this.tagLengthBit = tagLengthBit;
        this.nonceLengthByte = nonceLengthByte;
    }

    public static SecretKey generateKeyAES(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    public String encryptAES(String plaintext) {
        return encryptAES(plaintext,null);
    }
    public String encryptAES(String plaintext, String aad){
        try {
            // Генерируем nonce
            byte[] nonce = new byte[nonceLengthByte];
            new SecureRandom().nextBytes(nonce);

            // Инициализируем шифр
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(tagLengthBit, nonce);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);

            // Добавляем AAD если есть
            byte[] aadBytes = null;
            if (aad != null) {
                aadBytes = aad.getBytes();
                cipher.updateAAD(aadBytes);
            }

            // Шифруем
            byte[] ciphertext = cipher.doFinal(plaintext.getBytes());

            // Упаковываем всё в ByteBuffer
            ByteBuffer buffer = ByteBuffer.allocate(4 + nonce.length + 4 + ciphertext.length +
                    (aadBytes != null ? 4 + aadBytes.length : 0));

            buffer.putInt(nonce.length);
            buffer.put(nonce);
            buffer.putInt(ciphertext.length);
            buffer.put(ciphertext);

            if (aadBytes != null) {
                buffer.putInt(aadBytes.length);
                buffer.put(aadBytes);
            }

            // Преобразуем в строку
            return curseByteArray(buffer.array());
        }catch (NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | InvalidAlgorithmParameterException |
                BadPaddingException | InvalidKeyException e){
            System.out.println(e);
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    public String decryptAES(String cursedEncryptedData){
        try {
            // Распаковываем данные
            byte[] data = uncurseByteArray(cursedEncryptedData);
            ByteBuffer buffer = ByteBuffer.wrap(data);

            // Читаем nonce
            int nonceLength = buffer.getInt();
            byte[] nonce = new byte[nonceLength];
            buffer.get(nonce);

            // Читаем шифротекст
            int ciphertextLength = buffer.getInt();
            byte[] ciphertext = new byte[ciphertextLength];
            buffer.get(ciphertext);

            // Читаем AAD если есть
            byte[] aad = null;
            if (buffer.hasRemaining()) {
                int aadLength = buffer.getInt();
                aad = new byte[aadLength];
                buffer.get(aad);
            }

            // Расшифровываем
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(tagLengthBit, nonce);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);

            if (aad != null) {
                cipher.updateAAD(aad);
            }

            byte[] decrypted = cipher.doFinal(ciphertext);
            return new String(decrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException | InvalidAlgorithmParameterException e) {
            System.out.println(e);
            System.out.println(e.getStackTrace());
        }
        return null;
    }
}
