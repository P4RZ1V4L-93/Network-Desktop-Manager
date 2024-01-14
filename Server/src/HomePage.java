import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class HomePage {
    ServerSocket serverSocket;
    DataInputStream in;
    DataOutputStream out;

    public void initialize(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port, 1, InetAddress.getLocalHost());
            System.out.println("Server started");
            System.out.println("Server is running on " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
            System.out.println("Waiting for client...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                Scanner scanner = new Scanner(socket.getInputStream());
                int window = scanner.nextInt();

                switch (window) {
                    case -10:
                        System.out.println("Screen Share");
                        new ScreenShare(socket);
                        break;
                    case -11:
                        System.out.println("Chat");
                        new Chat(socket);
                        break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String port = JOptionPane.showInputDialog("please enter port number");
        new HomePage().initialize(Integer.parseInt(port));
    }
}
