module paka.tinder.tinderclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires spring.web;


    opens paka.tinder.tinderclient to javafx.fxml;
    exports paka.tinder.tinderclient;
    exports paka.tinder.tinderclient.GUIController;
    opens paka.tinder.tinderclient.GUIController to javafx.fxml;
}