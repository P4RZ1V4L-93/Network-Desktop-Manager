import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class HomePage {
    public static Scanner scanner;
    public static Socket socket;

    public void initialize() {
        try {
            ServerSocket serverSocket = new ServerSocket(0, 1, InetAddress.getLocalHost());
            System.out.println("Server started");
            System.out.println("Server is running on " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
            System.out.println("Waiting for client...");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Client connected");
                scanner = new Scanner(socket.getInputStream());
                waitForClient();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void waitForClient() {
        System.out.print("Waiting for client to choose a window: ");
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

    public static void main(String[] args) {
        new HomePage().initialize();
    }
}
