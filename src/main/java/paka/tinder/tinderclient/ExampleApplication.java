package paka.tinder.tinderclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import paka.tinder.tinderclient.Secure.BillCipher;

import java.io.*;
import java.security.PublicKey;
import java.util.Objects;

/**
 * Start/main class
 */
public class ExampleApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
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

        String msg = "\uD83D\uDE3Aagagsdf\uD83D\uDC31\u200D\uD83D\uDC64§".repeat(2);
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

//        Initializing fxmlLoader
        FXMLLoader fxmlLoader = new FXMLLoader(ExampleApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello Project Developer!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //Starting JavaFX application (execute method start)
        launch();
    }
}