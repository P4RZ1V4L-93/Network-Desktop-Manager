import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class SendScreen extends Thread{
    Socket socket;
    Robot robot;
    Rectangle rectangle;
    static boolean continueLoop = true;
    static OutputStream out;

    public SendScreen(Socket socket, Robot robot, Rectangle rectangle) {
        this.socket = socket;
        this.robot = robot;
        this.rectangle = rectangle;
        start();
    }

    @Override
    public void run() {
        try {
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (continueLoop) {
            BufferedImage image = robot.createScreenCapture(rectangle);
            try {
                ImageIO.write(image, "jpeg", out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeWindow() {
        continueLoop = false;
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
