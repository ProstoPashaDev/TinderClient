package paka.tinder.tinderclient;

import javafx.application.Application;
import javafx.stage.Stage;
import paka.tinder.tinderclient.Secure.BillCipher;

import java.io.*;
import java.security.PublicKey;

/**
 * Start/main class
 */
public class ExampleApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        BillCipher billCipherClient = new BillCipher();
        PublicKey publicKeyClient = billCipherClient.generateKeys();
        //передали публичный ключ на сервер
        BillCipher billCipherServer = new BillCipher(publicKeyClient);
        PublicKey publicKeyServer = billCipherServer.generateKeys();
        //передали публичный ключ на клиент
        billCipherClient.setPublicKey(publicKeyServer);

        billCipherClient.printKeys(publicKeyClient);
        billCipherServer.printKeys(publicKeyServer);
        System.out.println();

        String msg = "Hello, server!";
        System.out.println("msg = " + msg);

        String encrypted_msg = billCipherClient.Encrypt(msg);
        System.out.println("encrypted_msg = {\n"+ encrypted_msg +"\n}");
        System.out.println("encrypted_msg.length = " + encrypted_msg.length());

        String decrypted_msg = billCipherServer.Decrypt(encrypted_msg,String.class);
        System.out.println("decrypted_msg = " + decrypted_msg);
        System.out.println();

        String answer = "Hello, client!";
        System.out.println("answer = " + answer);
        String encrypted_answer = billCipherServer.Encrypt(answer);
        System.out.println("encrypted_msg = {\n"+ encrypted_answer +"\n}");
        System.out.println("encrypted_answer.length = " + encrypted_answer.length());

        String decrypted_answer = billCipherClient.Decrypt(encrypted_answer,String.class);
        System.out.println("decrypted_msg = " + decrypted_answer);
        System.out.println();

        //Initializing fxmlLoader
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