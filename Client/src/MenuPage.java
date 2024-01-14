import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MenuPage {
    Socket socket;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();

    DataInputStream in;
    DataOutputStream out;

    public MenuPage(String ip, int port) {

        SwingUtilities.invokeLater(this::drawGUI);

        try {
            this.socket = new Socket(ip, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            System.out.println("Connected to the server");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MenuPage(Socket socket, DataInputStream in, DataOutputStream out) {
        SwingUtilities.invokeLater(this::drawGUI);
            this.socket = socket;
            this.in = in;
            this.out = out;
            System.out.println("Back to the menu");
    }

    void drawGUI() {
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);

        JButton ScreenShare = new JButton("Screen Share");
        ScreenShare.addActionListener(e -> {
            try {
                frame.dispose();
                out.writeInt(Window.ScreenShare.getValue());
                out.flush();
                new ScreenGUI(socket, in, out);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JButton Chat = new JButton("Chat");
        Chat.addActionListener(e -> {
            try {
                frame.dispose();
                out.writeInt(Window.Chat.getValue());
                out.flush();
                new ChatGUI(socket, in, out);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        panel.add(ScreenShare);
        panel.add(Chat);
    }
}
