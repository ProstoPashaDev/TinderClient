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
import java.util.Objects;

public class RegistrationController {
    static boolean validEmail(String email) {
        // return email.matches("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}");
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }
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
        FXMLLoader fxmlLoader = new FXMLLoader(TinderClientApplication.class.getResource("authorization.fxml"));
        logInButton.getScene().setRoot(fxmlLoader.load());
    }

    @FXML
    protected void setSignUpButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TinderClientApplication.class.getResource("authorization.fxml"));
        String emailAddress = loginField.getText().trim();
        String password = passwordField.getText();
        String passwordCheck = passwordCheckField.getText();
        if (!validEmail(emailAddress)) {
            greetingText.setText("Please enter valid email address");
            return;
        }
        if (password.isEmpty()) {
            greetingText.setText("Please enter valid password");
            return;
        }
        if (!Objects.equals(passwordCheck, password)) {
            greetingText.setText("Passwords are not same");
        }
        //проверка на пароль нужной длины, наличие спец символов, цифр, крч обсудим
        User user = new User();
        user.setEmail(emailAddress);
        user.setPassword(password);
        // короче сюда впишем some shit with database

        //
        signUpButton.getScene().setRoot(fxmlLoader.load());
    }
}

