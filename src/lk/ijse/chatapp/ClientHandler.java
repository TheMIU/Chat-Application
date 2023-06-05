package lk.ijse.chatapp;

import javafx.application.Platform;
import javafx.geometry.Pos;
import lk.ijse.chatapp.controller.ClientController;
import lk.ijse.chatapp.controller.ServerController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private String clientName;
    private ServerController serverController;

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = dataInputStream.readUTF();
                broadcastMassage(messageFromClient);
            } catch (Exception e) {
                closeEverything(socket, dataInputStream, dataOutputStream);
                break;
            }
        }
    }

    public ClientHandler(Socket socket, ServerController serverController) {
        try {
            this.serverController = serverController;
            this.socket = socket;
            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

            this.clientName = dataInputStream.readUTF();
            clientHandlers.add(this);
            broadcastMassage("SERVER: " + clientName + " has entered the chat");

        } catch (Exception e) {
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }

    private void broadcastMassage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {
            try {
                if (!clientHandler.clientName.equals(clientName)) {
                    clientHandler.dataOutputStream.writeUTF(messageToSend);
                    clientHandler.dataOutputStream.flush();

                }
            } catch (Exception e) {
                closeEverything(socket, dataInputStream, dataOutputStream);
            }
        }
    }

    private void closeEverything(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
        removeClientHandler();
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

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMassage("SERVER: " + clientName + " has left the chat");
    }
}
