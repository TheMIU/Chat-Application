package lk.ijse.chatapp.controller;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lk.ijse.chatapp.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientController {
    public JFXTextField txtSend;
    public VBox vbox;
    public Label txtName;

    public void initialize() throws IOException {
       /* Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username : ");
        String username = scanner.nextLine();

        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, username);
        client.listenToMessage();
        client.sendMessage();*/

        // Set UserName
        txtName.setText(LoginController.userName);
        LoginController.userName = null;
    }

    public void btnSendMsgClickOnAction(ActionEvent actionEvent) {
    }

    public void btnSendImgClickOnAction(ActionEvent actionEvent) {
    }
}
