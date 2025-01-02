package paka.tinder.tinderclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import paka.tinder.tinderclient.Secure.Bill_Cipher;

import java.io.IOException;

/**
 * Start/main class
 */
public class ExampleApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Convert byte[] to String
//        byte[] byteArray = {72, 101, 108, 108, 111}; // "Hello" in ASCII
//        String str = new String(byteArray);
//        System.out.println(str); // Output: Hello
//// Convert String back to byte[]
//        byte[] byteArrayBack = str.getBytes();
//        System.out.println(Arrays.toString(byteArrayBack)); // Output: [72, 101, 108, 108, 111]
//        System.out.println(Arrays.equals(byteArray, byteArrayBack)); // Output: true

        Bill_Cipher billCipher = new Bill_Cipher();
        billCipher.printKeys();
        String msg = "Hello";
        System.out.println(msg);

        byte[] encrypted_msg = billCipher.Encrypt(msg);
        System.out.println("encrypted_msg = {\n"+ new String(encrypted_msg)+"\n}");

        String decrypted_msg = billCipher.Decrypt(encrypted_msg);
        System.out.println("decrypted_msg = " + decrypted_msg);

//        String crypted_msg = new String(encrypted_msg);
//        byte[] bb = crypted_msg.getBytes();
//        System.out.println(java.util.Arrays.equals(encrypted_msg, bb));//Експерименты с преобразованием байтов в строку
//        for (int i = 0; i < 10; i++) {
//            System.out.println(encrypted_msg[i]+" "+bb[i]);
//        }


        //Initializing fxmlLoader
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