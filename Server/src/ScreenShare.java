import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ScreenShare {
    public ScreenShare(Socket socket) {
        Robot robot;
        Rectangle rectangle;

        try {
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            String width = String.valueOf(dim.getWidth());
            String height = String.valueOf(dim.getHeight());

            rectangle = new Rectangle(dim);
            robot = new Robot(gDev);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(width);
            out.writeUTF(height);

            new SendScreen(socket, robot, rectangle);
            new ReceiveEvents(socket, robot);

        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }
}
