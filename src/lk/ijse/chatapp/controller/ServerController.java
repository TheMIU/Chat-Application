package lk.ijse.chatapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lk.ijse.chatapp.AppInitializerServer;
import lk.ijse.chatapp.Server;

import java.io.IOException;
import java.net.ServerSocket;


public class ServerController {
    public VBox vbox;
    private Stage primaryStage;

    public void initialize() throws IOException {
        // Server start
        new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(1234);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Server server = new Server(serverSocket);
            server.startServer(this);
        }).start();
    }

    public void btnAddNewClickOnAction(ActionEvent actionEvent) throws IOException {
        // create new scene - ClientLogin
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(ClassLoader.getSystemResource("lk/ijse/chatapp/view/ClientLogin.fxml")));
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
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
