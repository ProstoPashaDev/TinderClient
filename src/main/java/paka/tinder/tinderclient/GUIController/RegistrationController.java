package paka.tinder.tinderclient.GUIController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import paka.tinder.tinderclient.ExampleApplication;

import java.io.IOException;

public class RegistrationController {

    @FXML
    private Label greetingText;

    @FXML
    private Button logInButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordCheckField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    protected void setLogInButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExampleApplication.class.getResource("authorization.fxml"));
        logInButton.getScene().setRoot(fxmlLoader.load());
    }

    @FXML
    protected void setSignUpButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExampleApplication.class.getResource("authorization.fxml"));
        signUpButton.getScene().setRoot(fxmlLoader.load());
    }
}

