package lk.ijse.chatapp.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerController {
    public VBox vbox;

    public void btnAddNewClickOnAction(ActionEvent actionEvent) throws IOException {
        // create new scene - ClientLogin
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(ClassLoader.getSystemResource("lk/ijse/chatapp/view/ClientLogin.fxml")));
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
    }
}
