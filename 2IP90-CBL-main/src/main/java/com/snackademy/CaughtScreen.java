package snackademy;

import javax.swing.*;
import java.awt.*;

public class CaughtScreen extends JDialog {

    public CaughtScreen(JFrame parent, Runnable onReset) {
        super(parent, "Caught!", true); // modal dialog
        setLayout(new BorderLayout());
        setSize(400, 200);
        setLocationRelativeTo(parent);

        JLabel message = new JLabel("YOU ARE CAUGHT!", SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD, 32));
        message.setForeground(Color.YELLOW);
        message.setOpaque(true);
        message.setBackground(new Color(139, 0, 0));
        message.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 0), 3));

        JButton resetButton = new JButton("Reset Game");
        resetButton.setFont(new Font("Arial", Font.BOLD, 20));
        resetButton.addActionListener(e -> {
            onReset.run(); // call the reset logic
            dispose();      // close the dialog
        });

        add(message, BorderLayout.CENTER);
        add(resetButton, BorderLayout.SOUTH);
    }
}
