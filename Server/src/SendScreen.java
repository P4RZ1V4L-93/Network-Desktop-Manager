import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class SendScreen extends Thread{
    Socket socket;
    Robot robot;
    Rectangle rectangle;
    boolean continueLoop = true;
    DataOutputStream out;

    public SendScreen(Socket socket, Robot robot, Rectangle rectangle, OutputStream out) {
        this.socket = socket;
        this.robot = robot;
        this.rectangle = rectangle;
        this.out = new DataOutputStream(out);
        start();
    }

    @Override
    public void run() {
        try {
            while (continueLoop) {
                BufferedImage image = robot.createScreenCapture(rectangle);
                ImageIO.write(image, "jpeg", out);
                Thread.sleep(10);
            }
            System.out.println("Client has exited the Screen Share");
            out.flush();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
