package lk.ijse.chatapp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public JFXTextField txtName;

    public void btnLoginClickOnAction(ActionEvent actionEvent) throws IOException {
        // create new scene - chat app
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(ClassLoader.getSystemResource("lk/ijse/chatapp/view/Client.fxml")));
        stage.setScene(scene);
        stage.show();

        // close the current scene
        Stage currentStage = (Stage) txtName.getScene().getWindow();
        currentStage.close();
    }
}
