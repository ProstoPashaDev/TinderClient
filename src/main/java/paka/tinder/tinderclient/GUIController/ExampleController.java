package paka.tinder.tinderclient.GUIController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import paka.tinder.tinderclient.Service.ServiceExample;

/**
 * Class for connecting GUI(FXML file) with Java code
 */
public class ExampleController {
    //Label from FXML file
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(ServiceExample.lolExample());
    }
}