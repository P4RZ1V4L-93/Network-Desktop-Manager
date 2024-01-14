import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ReceiveEvents extends Thread {
    Socket socket;
    Robot robot;
    boolean continueLoop = true;

    public ReceiveEvents(Socket sc, Robot robot) {
        this.socket = sc;
        this.robot = robot;
        start();
    }

    @Override
    public void run() {
        Scanner scanner;
        try {
            scanner = new Scanner(socket.getInputStream());

            while (continueLoop) {
                int command = scanner.nextInt();
                switch (command) {
                    case -1:
                        robot.mousePress(scanner.nextInt());
                        break;
                    case -2:
                        robot.mouseRelease(scanner.nextInt());
                        break;
                    case -3:
                        robot.keyPress(scanner.nextInt());
                        break;
                    case -4:
                        robot.keyRelease(scanner.nextInt());
                        break;
                    case -5:
                        robot.mouseMove(scanner.nextInt(), scanner.nextInt());
                        break;
                    case -12:
                        continueLoop = false;
                        SendScreen.continueLoop = false;
                        System.out.println("Client has exited the Screen Share");

                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
