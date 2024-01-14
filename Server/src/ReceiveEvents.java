import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReceiveEvents extends Thread {
    Socket socket;
    Robot robot;
    boolean continueLoop = true;

    SendScreen sendScreen;

    DataInputStream in;

    public ReceiveEvents(Socket sc, Robot robot, DataInputStream in, SendScreen sendScreen) {
        this.socket = sc;
        this.robot = robot;
        this.in = in;
        this.sendScreen = sendScreen;
        start();
    }

    @Override
    public void run() {
        try {
            while (continueLoop) {
                int command = in.readInt();
                switch (command) {
                    case -1:
                        robot.mousePress(in.readInt());
                        break;
                    case -2:
                        robot.mouseRelease(in.readInt());
                        break;
                    case -3:
                        robot.keyPress(in.readInt());
                        break;
                    case -4:
                        robot.keyRelease(in.readInt());
                        break;
                    case -5:
                        robot.mouseMove(in.readInt(), in.readInt());
                        break;
                    case -12:
                        continueLoop = false;
                        sendScreen.continueLoop = false;
                        System.out.println("Client has exited the Screen Share");
                        HomePage.waitForClient(2);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
