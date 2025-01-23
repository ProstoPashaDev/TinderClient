package paka.tinder.tinderclient.GUIController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import paka.tinder.tinderclient.TinderClientApplication;
import paka.tinder.tinderclient.User;

import java.io.IOException;

public class LogInController {

    @FXML
    private Label greetingText;

    @FXML
    private Button logInButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    protected void setSignUpButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TinderClientApplication.class.getResource("registration.fxml"));
        signUpButton.getScene().setRoot(fxmlLoader.load());
    }

    @FXML
    protected void setLogInButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TinderClientApplication.class.getResource("appInterface.fxml"));
        String emailAddress = loginField.getText().trim();
        String password = passwordField.getText();
        User user = new User();
        //check that user exist in dataBase
        //
        //Then it connects with database info
        //
        logInButton.getScene().setRoot(fxmlLoader.load());
    }
}