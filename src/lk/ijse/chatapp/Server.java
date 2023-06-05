package lk.ijse.chatapp;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lk.ijse.chatapp.controller.ServerController;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private ServerController serverController;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }


  /*  public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();
    }*/


    public void startServer(ServerController serverController) {
        this.serverController = serverController;

        // Server Started msg
        serverController.printMsg("Server Started.",Pos.CENTER_LEFT);

        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New Client Connected!");
                //serverController.printMsg("New Client Connected!",Pos.CENTER_LEFT);
                ClientHandler clientHandler = new ClientHandler(socket,serverController);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            closeServerSocket();
            System.out.println(e);
        }
    }


    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
