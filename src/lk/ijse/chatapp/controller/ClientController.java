package lk.ijse.chatapp.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lk.ijse.chatapp.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientController {
    public JFXTextField txtSend;
    public VBox vbox;
    public Label txtName;
    private Client client;

    public void initialize() {
        new Thread(() -> {
            String username = txtName.getText();

            Socket socket = null;
            try {
                socket = new Socket("localhost", 1234);
            } catch (IOException e) {
                e.printStackTrace();
            }
            client = new Client(socket, username, this);
            client.listenToMessage();
            client.chatEnterText();
        }).start();

        // Set UserName & reset to null
        txtName.setText(LoginController.userName);
        LoginController.userName = null;
    }

    public void btnSendMsgClickOnAction(ActionEvent actionEvent) {
        printMsg(txtSend.getText(), Pos.CENTER_RIGHT);
        client.sendMessage();
        txtSend.clear();
        txtSend.requestFocus();
    }

    public void btnSendImgClickOnAction(ActionEvent actionEvent) {
    }

    public void printMsg(String msg, Pos pos) {
        VBox vBox = new VBox();
        Label label = new Label(msg);
        label.setFont(Font.font("jetbrains mono"));

        label.setStyle("-fx-background-color: #bafaf7 ; -fx-label-padding: 3px ; -fx-text-fill: #312e2e;");

        vBox.getChildren().add(label);
        vBox.setAlignment(pos);
        vbox.getChildren().add(vBox);
    }
}
