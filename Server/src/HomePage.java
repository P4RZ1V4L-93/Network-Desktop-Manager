import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class HomePage {
    public static Socket socket;
    static DataOutputStream out;
    static DataInputStream in;

    public void initialize() {
        try {
            ServerSocket serverSocket = new ServerSocket(0, 1, InetAddress.getLocalHost());
            System.out.println("Server started");
            System.out.println("Server is running on " + serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort());
            System.out.println("Waiting for client...");

            while (true) {
                socket = serverSocket.accept();
                System.out.println("Client connected");
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                waitForClient(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void waitForClient(int signal) {
        try {
            if (signal == 1) {
                out.writeUTF("");
                out.flush();
            }

            System.out.print("Waiting for client to choose a window: ");
            int window = in.readInt();
            switch (window) {
                case -10:
                    System.out.println("Screen Share");
                    new ScreenShare(socket, out, in);
                    break;
                case -11:
                    System.out.println("Chat");
                    new Chat(socket, out, in);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HomePage().initialize();
    }
}
