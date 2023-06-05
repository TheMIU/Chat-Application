package lk.ijse.chatapp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lk.ijse.chatapp.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientController {
    public JFXTextField txtSend;
    public VBox vbox;
    public Label txtName;
    private Client client;
    public boolean notTyped = true;

    public void initialize() {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter username : ");
            String username = scanner.nextLine();

            Socket socket = null;
            try {
                socket = new Socket("localhost", 1234);
            } catch (IOException e) {
                e.printStackTrace();
            }
            client = new Client(socket, username, this);
            client.listenToMessage();
            client.sendMessage();
        }).start();


        // Set UserName & reset to null
        txtName.setText(LoginController.userName);
        LoginController.userName = null;
    }

    public void btnSendMsgClickOnAction(ActionEvent actionEvent) {
        printMsg(txtSend.getText(), Pos.CENTER_RIGHT);
        notTyped = false;
        client.sendMessage2();
        txtSend.clear();
        txtSend.requestFocus();
    }

    public void btnSendImgClickOnAction(ActionEvent actionEvent) {
    }

    public void printMsg(String msg, Pos pos) {
        VBox vBox = new VBox();
        Label label = new Label(msg);
        label.setFont(Font.font("jetbrains mono"));
        vBox.getChildren().add(label);
        vBox.setAlignment(pos);
        vbox.getChildren().add(vBox);
    }
}
