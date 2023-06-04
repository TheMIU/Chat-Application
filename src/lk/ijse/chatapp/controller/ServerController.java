package lk.ijse.chatapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lk.ijse.chatapp.Server;

import java.io.IOException;
import java.net.ServerSocket;


public class ServerController {
    public VBox vbox;

    public void initialize() throws IOException {
        // begin
        VBox vBox1 = new VBox();
        Label label = new Label("Server Started.");
        label.setFont(Font.font("jetbrains mono"));
        vBox1.getChildren().add(label);
        vBox1.setAlignment(Pos.CENTER_LEFT);
        vbox.getChildren().add(vBox1);

        VBox vBox2 = new VBox();
        Label label2 = new Label("right text.");
        label2.setFont(Font.font("jetbrains mono"));
        vBox2.getChildren().add(label2);
        vBox2.setAlignment(Pos.CENTER_RIGHT);
        vbox.getChildren().add(vBox2);

        new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(1234);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Server server = new Server(serverSocket);
            server.startServer();
        });
        // Server start
        /*ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();*/
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

}
