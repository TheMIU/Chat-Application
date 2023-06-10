package lk.ijse.chatapp.controller;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.chatapp.Client;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ClientController {
    public JFXTextField txtSend;
    public VBox vbox;
    public Label txtName;
    private Client client;
    @FXML
    private ScrollPane scrollPane;

    public void initialize() {
        // Scroll to the bottom whenever the height of the VBox changes
        vbox.heightProperty().addListener((observable, oldValue, newValue) -> {
            scrollPane.setVvalue(1.0);
        });


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
            //client.listenToImageMessage();
            client.chatEnterText();
        }).start();

        // Set UserName & reset to null
        txtName.setText(LoginController.userName);
        LoginController.userName = null;
    }

    public void btnSendMsgClickOnAction(ActionEvent actionEvent) {
       if(txtSend.getText() != null && !txtSend.getText().equals("") ){
           printMsg(txtSend.getText(), Pos.CENTER_RIGHT);
           client.sendMessage();
       }
        txtSend.clear();
        txtSend.requestFocus();
    }

    public void btnSendImgClickOnAction(ActionEvent actionEvent) {
        // Get the image from the user
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image to send");
        File selectedFile = fileChooser.showOpenDialog(null);

        // Check if the user has selected an image
        if (selectedFile != null) {

            // Create a new Image object from the file
            Image image = new Image(selectedFile.toURI().toString());

            // Send the image to the server
            client.sendImage(image);
            printImageMsg(image, Pos.CENTER_RIGHT);
        }
    }

    public void printMsg(String msg, Pos pos) {
        VBox vBox = new VBox();
        Label label = new Label(msg);
        label.setFont(Font.font("jetbrains mono"));

        label.setStyle("-fx-background-color: #bafaf7 ; -fx-end-margin: 3px ; -fx-text-fill: #312e2e;");

        vBox.getChildren().add(label);
        vBox.setAlignment(pos);
        vbox.getChildren().add(vBox);
    }

    public void printImageMsg(Image image, Pos position) {
        VBox vBox = new VBox();

        // Calculate the desired width and height based on the maximum allowed size
        double maxWidth = 200; // Maximum width for the image
        double maxHeight = 200; // Maximum height for the image

        double scaleFactor = Math.min(maxWidth / image.getWidth(), maxHeight / image.getHeight());
        double desiredWidth = image.getWidth() * scaleFactor;
        double desiredHeight = image.getHeight() * scaleFactor;

        // Create a resized ImageView
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(desiredWidth);
        imageView.setFitHeight(desiredHeight);
        imageView.setPreserveRatio(true);

        vBox.getChildren().add(imageView);

        vBox.setAlignment(position);
        vbox.getChildren().add(vBox);
    }

    // send msg on enter key pressed
    public void OnEnterKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            btnSendMsgClickOnAction(new ActionEvent());
        }
    }


}
