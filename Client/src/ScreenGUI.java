import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.net.Socket;
import java.io.*;

public class ScreenGUI extends Thread {
    String width, height;
    private JFrame frame = new JFrame();
    private JDesktopPane desktopPane = new JDesktopPane();
    private Socket socket;
    private JInternalFrame internalFrame = new JInternalFrame("Server Screen", true, true, true, true);
    private JPanel panel = new JPanel();

    public ScreenGUI(Socket socket) {
        this.socket = socket;
        start();
    }

    public void drawGUI() {
        frame.add(desktopPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        internalFrame.setLayout(new BorderLayout());
        internalFrame.getContentPane().add(panel, BorderLayout.CENTER);
        internalFrame.setSize(100, 100);
        desktopPane.add(internalFrame);

        try {
            internalFrame.setMaximum(true);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        panel.setFocusable(true);
        internalFrame.setVisible(true);
    }

    @Override
    public void run() {
        InputStream in = null;
        drawGUI();
        try {
            in = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(in);
            width = dataInputStream.readUTF();
            height = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new ReceivingScreen(in, panel);
        new SendEvents(socket, panel, width, height);
    }
}