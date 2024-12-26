package paka.tinder.tinderclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Start/main class
 */
public class ExampleApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
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