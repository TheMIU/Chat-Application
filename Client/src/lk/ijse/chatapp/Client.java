package lk.ijse.chatapp;

import lk.ijse.chatapp.controller.ClientController;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;


import javax.imageio.ImageIO;
import java.awt.*;
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

    // send button clicked --> invoke
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
        // Resize the image
        double maxWidth = 600; // Maximum width for resizing
        double maxHeight = 400; // Maximum height for resizing
        double width = image.getWidth();
        double height = image.getHeight();

        if (width > maxWidth || height > maxHeight) {
            double scaleFactor = Math.min(maxWidth / width, maxHeight / height);
            width *= scaleFactor;
            height *= scaleFactor;
        }

        // Create a resized BufferedImage
        BufferedImage resizedImage = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(SwingFXUtils.fromFXImage(image, null), 0, 0, (int) width, (int) height, null);
        g.dispose();

        // Compress the image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", outputStream);

        // Convert the resized and compressed image to a Base64-encoded string
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public static Image convertStringToImage(String imageAsString) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(imageAsString);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        return new Image(inputStream);
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

                        if (finalMsgFromGroupChat.length() > 200) {
                           Image image = convertStringToImage(finalMsgFromGroupChat);
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

