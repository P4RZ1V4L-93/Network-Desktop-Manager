import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MenuPage {
    Socket socket;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    PrintWriter out;

    public MenuPage(String ip, int port) {

        SwingUtilities.invokeLater(this::drawGUI);

        try {
            this.socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connecting to the server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MenuPage(Socket socket) {
        SwingUtilities.invokeLater(this::drawGUI);
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void drawGUI() {
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);

        JButton ScreenShare = new JButton("Screen Share");
        ScreenShare.addActionListener(e -> {
            frame.dispose();
            out.println(Window.ScreenShare.getValue());
            new ScreenGUI(socket);
        });
        JButton Chat = new JButton("Chat");
        Chat.addActionListener(e -> {
            frame.dispose();
            out.println(Window.Chat.getValue());
            new ChatGUI(socket);
        });

        panel.add(ScreenShare);
        panel.add(Chat);
    }
}
