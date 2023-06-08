package lk.ijse.chatapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializerServer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/Server.fxml"))));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setTitle("Server");

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            Platform.exit();  // Close the application
        });
    }
}
