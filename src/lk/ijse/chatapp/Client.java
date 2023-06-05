package lk.ijse.chatapp;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
   /* private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;*/

    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;

            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());

           /* this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));*/

            this.username = username;

        } catch (Exception e) {
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }


    public void sendMessage() {
        try {
         /*   bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();*/
            dataOutputStream.writeUTF(username);
            dataOutputStream.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                /*bufferedWriter.write(username + ": " + messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();*/
                dataOutputStream.writeUTF(username + ": " + messageToSend);
                dataOutputStream.flush();
            }
        } catch (Exception e) {
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }

    public void listenToMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;
                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = dataInputStream.readUTF();
                        System.out.println(msgFromGroupChat);
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

