package lk.ijse.chatapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.chatapp.controller.ClientController;

import java.io.IOException;

public class AppInitializerClient extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/ClientLogin.fxml"))));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login");
    }
}
