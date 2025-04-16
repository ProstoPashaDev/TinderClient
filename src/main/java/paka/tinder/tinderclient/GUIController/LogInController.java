package paka.tinder.tinderclient.GUIController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import paka.tinder.tinderclient.Service.AuthenticationService;
import paka.tinder.tinderclient.TinderClientApplication;
import paka.tinder.tinderclient.User;

import java.io.IOException;

public class LogInController {

    AuthenticationService authService = new AuthenticationService();

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
        User user = new User(emailAddress, password);
        //check that user exist in dataBase
        try {
            authService.authorizeUser(user);
        } catch (RuntimeException e) {
            greetingText.setText(e.getMessage());
            return;
        }
        //Then it connects with database info
        //
        logInButton.getScene().setRoot(fxmlLoader.load());
    }
}