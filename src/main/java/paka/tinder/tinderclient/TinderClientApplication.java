package paka.tinder.tinderclient;

import javafx.application.Application;
import javafx.stage.Stage;
import paka.tinder.tinderclient.Secure.BillCipher;

import javax.crypto.SecretKey;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * Start/main class
 */
public class TinderClientApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException{
        BillCipher BillCipherClient = new BillCipher();
        PublicKey publicKeyClient = BillCipherClient.generateKeys();
        //передали публичный ключ на сервер
        BillCipher BillCipherServer = new BillCipher(publicKeyClient);
        PublicKey publicKeyServer = BillCipherServer.generateKeys();
        //передали публичный ключ на клиент
        BillCipherClient.setPublicKey(publicKeyServer);

//        BillCipherClient.printKeys(publicKeyClient);
//        BillCipherServer.printKeys(publicKeyServer);
//        System.out.println();
        /*
        String msg = "\uD83D\uDE3Aagagsdf\uD83D\uDC31\u200D\uD83D\uDC64§".repeat(200);
        System.out.println("msg = " + msg);
        String encrypted_msg = BillCipherClient.Encrypt(msg);

        if (Objects.isNull(encrypted_msg)) {
            System.out.println("Seems like encrypting error occurred, try again or write to Pasha");
        }else {
            System.out.println("encrypted_msg = {\n"+ encrypted_msg +"\n}");
//            System.out.println("encrypted_msg.length = " + encrypted_msg.length());
            String decrypted_msg = BillCipherServer.Decrypt(encrypted_msg, String.class);
            if (Objects.isNull(decrypted_msg)) {
                System.out.println("Seems like decrypting error occurred, try again or write to Pasha");
            }else {
                System.out.println("decrypted_msg = " + decrypted_msg);
                System.out.println();
            }
        }

        String answer = "Hello, client!";
        System.out.println("answer = " + answer);
        String encrypted_answer = BillCipherServer.Encrypt(answer);

        if (Objects.isNull(encrypted_answer)) {
            System.out.println("Seems like encrypting error occurred, try again or write to Pasha");
        }else {
            System.out.println("encrypted_msg = {\n"+ encrypted_answer +"\n}");
//            System.out.println("encrypted_answer.length = " + encrypted_answer.length());

            String decrypted_answer = BillCipherClient.Decrypt(encrypted_answer,String.class);
            if (Objects.isNull(decrypted_answer)) {
                System.out.println("Seems like decrypting error occurred, try again or write to Pasha");
            }else {
                System.out.println("decrypted_msg = " + decrypted_answer);
                System.out.println();
            }
        }
        */
        User user = new User();
        user.setEmail("p.khramov@innopolis.university");
        user.setPassword("asbjfajfshash*&&^^%^tuasfgbahyYGHFD&^&");
        user.setUserId(10^4);

//        BillCipherClient.Encrypt(user);
        SecretKey key = BillCipher.generateKeyAES();
        String msg = "Hello".repeat(100);
        BillCipher cipher = new BillCipher(key);

        int counter = 0;
        long start = System.currentTimeMillis();
        String ciphr = "";
        String result = "";
        while (System.currentTimeMillis() - start < 1000){
            ciphr = cipher.encryptAES(msg);
            result = cipher.decryptAES(ciphr);
            counter++;
        }
        System.out.println("msg = " + msg);
        System.out.println("key = " + key);
        System.out.println("ciphr = " + ciphr);
        System.out.println("result = " + result);

        String encrypted = cipher.encryptAES("Секретное сообщение", "Дополнительные данные");
// encrypted теперь можно хранить в БД как строку

        String decrypted = cipher.decryptAES(encrypted);
// decrypted == "Секретное сообщение"
        System.out.println("encrypted = " + encrypted);
        System.out.println("decrypted = " + decrypted);
        System.out.println("counter = " + counter);




















//        Initializing fxmlLoader
//        FXMLLoader fxmlLoader = new FXMLLoader(ExampleApplication.class.getResource("hello-view.fxml"));
//
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello Project Developer!");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        //Starting JavaFX application (execute method start)
        launch();
    }
}