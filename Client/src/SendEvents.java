import javax.swing.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SendEvents implements KeyListener, MouseMotionListener, MouseListener{
    private Socket cSocket;
    JPanel cpanel;
    String width, height;

    double w, h;
    DataOutputStream out;

    SendEvents(Socket s, JPanel panel, String width, String height, DataOutputStream out) {
        cSocket = s;
        cpanel = panel;
        this.width = width;
        this.height = height;
        this.out = out;
        w = Double.valueOf(width.trim()).doubleValue();
        h = Double.valueOf(height.trim()).doubleValue();

        cpanel.addKeyListener(this);
        cpanel.addMouseListener(this);
        cpanel.addMouseMotionListener(this);
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        double xScale = (double) w / cpanel.getWidth();
        double yScale = (double) h / cpanel.getHeight();
        try {
            out.write(Commands.MOVE_MOUSE.getAbbrev());
            out.writeInt((int) (e.getX() * xScale));
            out.writeInt((int) (e.getY() * yScale));
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            out.write(Commands.PRESS_MOUSE.getAbbrev());
            int button = e.getButton();
            int xButton = 16;
            if (button == 3) {
                xButton = 4;
            }
            out.writeInt(xButton);
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            out.write(Commands.RELEASE_MOUSE.getAbbrev());
            int button = e.getButton();
            int xButton = 16;
            if (button == 3) {
                xButton = 4;
            }
            out.writeInt(xButton);
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        try {
            out.write(Commands.PRESS_KEY.getAbbrev());
            out.writeInt(e.getKeyCode());
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        try {
            out.write(Commands.RELEASE_KEY.getAbbrev());
            out.writeInt(e.getKeyCode());
            out.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
