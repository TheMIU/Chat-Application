package lk.ijse.chatapp;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import lk.ijse.chatapp.controller.ClientController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Base64;


public class Client {
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String username;
    private ClientController clientController;

    public Client(Socket socket, String username, ClientController clientController) {
        this.clientController = clientController;
        try {
            this.socket = socket;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            this.username = username;

        } catch (Exception e) {
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }

    //1st msg "SERVER: " + clientName + " has entered the chat"
    public void chatEnterText() {
        try {
            dataOutputStream.writeUTF(username);
            dataOutputStream.flush();

        } catch (Exception e) {
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }

    // send button clicked --> invoked
    public void sendMessage() {
        try {
            // Get the text from the txtSend text field
            String messageToSend = "";

            messageToSend = clientController.txtSend.getText();

            // Send the message
            String message = username + ": " + messageToSend;
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();

        } catch (Exception e) {
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }


    public static String convertImageToString(Image image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(bufferedImage, "png", outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public void sendImage(Image image) {
        try {
            // Convert image to string (base64)
            String imageAsTextToSend = convertImageToString(image);

            // Send the message
            String message = imageAsTextToSend;
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.toString()).show();
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }


    public void listenToMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat = "";
                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = dataInputStream.readUTF();
                        String finalMsgFromGroupChat = msgFromGroupChat;
                        System.out.println(finalMsgFromGroupChat);
                        if (finalMsgFromGroupChat.length() > 200) {
                           Image image = convertStringToImage(finalMsgFromGroupChat);
                            //Image image = new Image("file:C:/Users/HP/Desktop/Screenshot 2023-06-08 094957.jpg");
                            Platform.runLater(() -> clientController.printImageMsg(image, Pos.CENTER_LEFT));

                            System.out.println(finalMsgFromGroupChat.length());
                            System.out.println(finalMsgFromGroupChat);
                        } else {
                            Platform.runLater(() -> clientController.printMsg(finalMsgFromGroupChat, Pos.CENTER_LEFT));
                            System.out.println(finalMsgFromGroupChat);
                        }
                    } catch (Exception e) {
                        closeEverything(socket, dataInputStream, dataOutputStream);
                    }
                }
            }
        }).start();
    }

    public static Image convertStringToImage(String imageAsString) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(imageAsString);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        return new Image(inputStream);
    }

   /* public void listenToImageMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imageAsString = "";
                while (socket.isConnected()) {
                    try {
                        imageAsString = dataInputStream.readUTF();
                        String finalImageAsString = imageAsString;
                        // Convert String to image
                        Image image = convertStringToImage(finalImageAsString);

                        Platform.runLater(() -> clientController.printImageMsg(image, Pos.CENTER_LEFT));

                    } catch (Exception e) {
                        closeEverything(socket, dataInputStream, dataOutputStream);
                    }
                }
            }
        }).start();
    }*/

    private void closeEverything(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        try {
            if (dataInputStream != null) {
                dataInputStream.close();
            }

            if (dataOutputStream != null) {
                dataOutputStream.close();
            }

            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

