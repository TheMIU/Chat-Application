package lk.ijse.chatapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lk.ijse.chatapp.Server;
import java.io.IOException;
import java.net.ServerSocket;


public class ServerController {
    public VBox vbox;
    private Stage primaryStage;
    @FXML
    private ScrollPane scrollPane;


    public void initialize() throws IOException {
        // Scroll to the bottom whenever the height of the VBox changes
        vbox.heightProperty().addListener((observable, oldValue, newValue) -> {
            scrollPane.setVvalue(1.0);
        });

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
        label.setFont(Font.font("jetbrains mono0"));

        label.setStyle("-fx-background-color: #bafaf7 ; -fx-end-margin: 3px ; -fx-text-fill: #312e2e;");

        vBox.getChildren().add(label);
        vBox.setAlignment(pos);
        vbox.getChildren().add(vBox);
    }

}
