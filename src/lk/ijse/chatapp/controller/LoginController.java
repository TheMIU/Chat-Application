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
        // create new scene - Client Interface
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(ClassLoader.getSystemResource("lk/ijse/chatapp/view/Client.fxml")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // close the current scene
        Stage currentStage = (Stage) txtName.getScene().getWindow();
        currentStage.close();
    }
}
