import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class ReceivingScreen extends Thread {
    private JPanel panel;
    boolean continueLoop=true;
    InputStream oin;
    Image image1;

    public ReceivingScreen(InputStream oin, JPanel panel) {
        this.panel = panel;
        this.oin = oin;
        start();
    }

    @Override
    public void run() {
        try {
            while (continueLoop)
            {
                byte[] bytes = new byte[1024*1024];
                int count=0;
                do {
                    count+=oin.read(bytes, count, bytes.length-count);
                } while (!(count>4 && bytes[count-2]==(byte)-1 && bytes[count-1]==(byte)-39));
                image1 = ImageIO.read(new ByteArrayInputStream(bytes));
                image1 = image1.getScaledInstance(panel.getWidth(), panel.getHeight(), Image.SCALE_FAST);

                Graphics graphics = panel.getGraphics();
                graphics.drawImage(image1, 0, 0, panel.getWidth(), panel.getHeight(), panel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWindow() {
        continueLoop = false;
        try {
            oin.close();
            image1 = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
