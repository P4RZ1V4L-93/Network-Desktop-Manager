import javax.swing.*;

public class Login {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton submit = new JButton("Submit");

    JLabel IPLabel = new JLabel("IP Address:");
    JTextField IPField = new JTextField(15);

    JLabel portLabel = new JLabel("Port:");
    JTextField portField = new JTextField(5);

    String IP;
    int port;

    public void drawGUI(){
        frame.setVisible(true);
        frame.setSize(300, 100);
        frame.setLocation(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(panel);
        panel.add(IPLabel);
        panel.add(IPField);
        panel.add(portLabel);
        panel.add(portField);
        submit = new JButton("Submit");
        panel.add(submit);

        submit.addActionListener(e -> {
            IP = IPField.getText();
            try {
                port = Integer.parseInt(portField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid port number");
                return;
            }
            frame.dispose();
            new MenuPage(IP, port);
        });

    }

    public static void main(String[] args) {
        new Login().drawGUI();
    }
}
