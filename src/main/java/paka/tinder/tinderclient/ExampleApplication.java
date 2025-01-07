package paka.tinder.tinderclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import paka.tinder.tinderclient.GUIController.LogInController;

import java.io.IOException;
import java.util.Objects;

/**
 * Start/main class
 */
public class ExampleApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Font customFont = Font.loadFont(Objects.requireNonNull(getClass().getResource("Manrope-VariableFont_wght.ttf")).toExternalForm(), 12);
        //Initializing fxmlLoader
        FXMLLoader fxmlLoader = new FXMLLoader(ExampleApplication.class.getResource("authorization.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("InnoTinder");
        stage.getIcons().add(new Image (Objects.requireNonNull(getClass().getResourceAsStream("Logo.png"))));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        //Starting JavaFX application (execute method start)
        launch();
    }
}