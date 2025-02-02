package paka.tinder.tinderclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import paka.tinder.tinderclient.Service.SecureDataTransferClientService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

/**
 * Start/main class
 */
public class TinderClientApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        /*
        Example of encryption/decryption information from Aleksei Feofanov @CatRespect

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
        */

//        Initializing fxmlLoader
        Font customFont = Font.loadFont(Objects.requireNonNull(getClass().getResource("Manrope-VariableFont_wght.ttf")).toExternalForm(), 12);
        FXMLLoader fxmlLoader = new FXMLLoader(TinderClientApplication.class.getResource("authorization.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("InnoTinder");
        stage.getIcons().add(new Image (Objects.requireNonNull(getClass().getResourceAsStream("Logo.png"))));
        stage.setScene(scene);
        stage.centerOnScreen();

        //Beginning safe data exchanging preparation
        SecureDataTransferClientService sdtService = new SecureDataTransferClientService();
        sdtService.sendPublicKey();



        stage.show();
    }

    public static void main(String[] args) {
        //Starting JavaFX application (execute method start)
        launch();
    }
}