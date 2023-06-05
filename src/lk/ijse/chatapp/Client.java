package lk.ijse.chatapp;

import javafx.application.Platform;
import javafx.geometry.Pos;
import lk.ijse.chatapp.controller.ClientController;

import java.io.*;
import java.net.Socket;

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


    public void listenToMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat = "";
                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = dataInputStream.readUTF();
                        String finalMsgFromGroupChat = msgFromGroupChat;
                        Platform.runLater(() -> clientController.printMsg(finalMsgFromGroupChat, Pos.CENTER_LEFT));
                        System.out.println(finalMsgFromGroupChat);
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

