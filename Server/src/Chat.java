import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Chat extends Thread{
    JFrame frame = new JFrame();
    JButton sendMessage;
    JTextField messageBox;
    JTextArea chatBox;

    Socket socket;
    DataOutputStream out;
    DataInputStream in;

    boolean continueloop = true;

    public Chat(Socket socket, DataOutputStream out, DataInputStream in) {
        this.socket = socket;
        this.out = out;
        this.in = in;
        start();
    }


    public void display() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        messageBox = new JTextField(30);
        messageBox.requestFocusInWindow();

        sendMessage = new JButton("Send Message");
        sendMessage.addActionListener(new sendMessageButtonListener());

        chatBox = new JTextArea();
        chatBox.setEditable(false);
        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        chatBox.setLineWrap(true);

        mainPanel.add(new JScrollPane(chatBox), BorderLayout.CENTER);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);

        mainPanel.add(BorderLayout.SOUTH, southPanel);

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(470, 300);
        frame.setVisible(true);
        frame.setName("Chat Window");
        frame.setTitle("Server Chat");
    }

    @Override
    public void run() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(this::display);
        new ReceiveMessage().start();
    }


    class sendMessageButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (messageBox.getText().isEmpty()) {
                // do nothing
            } else if (messageBox.getText().equals(".clear")) {
                chatBox.setText("Cleared all messages\n");
                messageBox.setText("");
            } else {
                try {
                    out.writeUTF("Server: " + messageBox.getText() + "\n");
                    out.flush();
                    chatBox.append("<You>:  " + messageBox.getText() + "\n");
                    messageBox.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            messageBox.requestFocusInWindow();
        }
    }

    class ReceiveMessage extends Thread{
        public void run(){
            while(continueloop){
                try {
                    String message = in.readUTF();
                    if (message.equals(".exit")) {
                        System.out.println("Client has exited the chat");
                        continueloop = false;
                        frame.dispose();
                        HomePage.waitForClient(1);
                    }
                    chatBox.append(message + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
