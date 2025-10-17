package paka.tinder.tinderclient.GUIController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class AppInterfaceController {
    @FXML
    BorderPane mainBorderPane;

    @FXML
    BorderPane chatBorderPane;

    @FXML
    VBox friendsListContainer;

    @FXML
    VBox usersListContainer;

    public void showChat() {
        mainBorderPane.setCenter(chatBorderPane);
    }

    public void showProfile() {

    }

    public void showNews() {

    }

    public void showFriends() {
        friendsListContainer.setVisible(true);
        usersListContainer.setVisible(true);
    }
}
