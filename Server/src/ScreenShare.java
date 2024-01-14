import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ScreenShare {
    SendScreen sendScreen;
    public ScreenShare(Socket socket, DataOutputStream out, DataInputStream in) {
        Robot robot;
        Rectangle rectangle;
        String width, height;

        try {
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            width = String.valueOf(dim.getWidth());
            height = String.valueOf(dim.getHeight());

            rectangle = new Rectangle(dim);
            robot = new Robot(gDev);

            out.writeUTF(width);
            out.writeUTF(height);

            sendScreen = new SendScreen(socket, robot, rectangle, out);
            new ReceiveEvents(socket, robot, in, sendScreen);

        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }
}
