import javax.swing.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.net.Socket;

public class SendEvents implements KeyListener, MouseMotionListener, MouseListener{
    private Socket cSocket;
    private JPanel cpanel;
    public static PrintWriter printWriter;
    String width, height;

    double w, h;

    SendEvents(Socket s, JPanel panel, String width, String height) {
        cSocket = s;
        cpanel = panel;
        this.width = width;
        this.height = height;
        w = Double.valueOf(width.trim()).doubleValue();
        h = Double.valueOf(height.trim()).doubleValue();

        cpanel.addKeyListener(this);
        cpanel.addMouseListener(this);
        cpanel.addMouseMotionListener(this);
        try {
            printWriter = new PrintWriter(cSocket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(){
        cpanel.removeKeyListener(this);
        cpanel.removeMouseListener(this);
        cpanel.removeMouseMotionListener(this);
        printWriter.close();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        double xScale = (double) w / cpanel.getWidth();
        double yScale = (double) h / cpanel.getHeight();
        printWriter.println(Commands.MOVE_MOUSE.getAbbrev());
        printWriter.println((int) (e.getX() * xScale));
        printWriter.println((int) (e.getY() * yScale));
        printWriter.flush();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        printWriter.println(Commands.PRESS_MOUSE.getAbbrev());
        int button = e.getButton();
        int xButton = 16;
        if (button==3){
            xButton = 4;
        }
        printWriter.println(xButton);
        printWriter.flush();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        printWriter.println(Commands.RELEASE_MOUSE.getAbbrev());
        int button = e.getButton();
        int xButton = 16;
        if (button==3){
            xButton = 4;
        }
        printWriter.println(xButton);
        printWriter.flush();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        printWriter.println(Commands.PRESS_KEY.getAbbrev());
        printWriter.println(e.getKeyCode());
        printWriter.flush();
    }


    @Override
    public void keyReleased(KeyEvent e) {
        printWriter.println(Commands.RELEASE_KEY.getAbbrev());
        printWriter.println(e.getKeyCode());
        printWriter.flush();
    }

}
