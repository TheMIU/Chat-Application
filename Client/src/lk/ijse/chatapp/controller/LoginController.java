package lk.ijse.chatapp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public JFXTextField txtName;
    public static String userName;

    public void btnLoginClickOnAction(ActionEvent actionEvent) throws IOException {
        // get username
        userName = txtName.getText();

        if (userName != null && !userName.equals("")) {
            // create new scene - Client Interface
            Stage stage = new Stage();
            Scene scene = new Scene(FXMLLoader.load(ClassLoader.getSystemResource("lk/ijse/chatapp/view/Client.fxml")));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Client");
            stage.show();

            // close the current scene
            Stage currentStage = (Stage) txtName.getScene().getWindow();
            currentStage.close();

        } else {
            new Alert(Alert.AlertType.ERROR, "User Name Required!").show();
        }

    }

    public void OnEnterKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            btnLoginClickOnAction(new ActionEvent());
        }
    }
}
