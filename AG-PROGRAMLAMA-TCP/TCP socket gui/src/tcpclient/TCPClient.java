package tcpclient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient extends JFrame {
    private Socket socket;
    private PrintWriter out;
    private Scanner input;
    private JTextField messageField;
    private JLabel responseLabel;

    public TCPClient() {
        super("TCP Client");

        // Bağlantıyı oluştur
        if (!connectToServer()) {
            JOptionPane.showMessageDialog(null, "Sunucuya bağlanılamadı.");
            System.exit(1);
        }

        // GUI arayüzünü oluştur
        JPanel panel = new JPanel();

        messageField = new JTextField();
        messageField.setPreferredSize(new Dimension(300, 30)); // Alan boyutunu ayarla
        panel.setLayout(new GridLayout(3, 1));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); //
        responseLabel = new JLabel("");

        JButton sendButton = new JButton("Gönder");
        sendButton.setPreferredSize(new Dimension(100, 30)); // Düğme boyutunu ayarla
        sendButton.setBackground(new Color(51, 51, 51)); // Koyu gri arka plan
        sendButton.setForeground(Color.WHITE); // Beyaz metin rengi
        sendButton.setFocusPainted(false); // Odak

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                sendMessage();
            }
        });
        sendButton.setBackground(new Color(51, 51, 51)); // Koyu gri arka plan

        panel.add(messageField);
        panel.add(sendButton);
        panel.add(responseLabel);
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200); // Pencere boyutunu ayarla
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir
        setVisible(true);
    }

    private boolean connectToServer() {
        try {
            socket = new Socket("localhost", 1234);
            out = new PrintWriter(socket.getOutputStream(), true);
            input = new Scanner(socket.getInputStream());
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (out != null) {
            out.println(message);
            if (input.hasNextLine()) {
                String response = input.nextLine();
                responseLabel.setText("Sunucu: " + response);
            }
        } else {
            responseLabel.setText("Bağlantı yok.");
        }
    }

    public static void main(String[] args) {
        new TCPClient();
    }
}
