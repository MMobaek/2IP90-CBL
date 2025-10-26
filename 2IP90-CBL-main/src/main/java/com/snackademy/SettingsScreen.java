package snackademy;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Settings screen to adjust game options such as player speed and bookshelf count.
 */
public class SettingsScreen extends JPanel {

    private final GameFrame frame;
    private final JSlider speedSlider;
    private final JSlider bookshelfSlider;

    // Persist bookshelf count
    public static int bookshelfCount = 10; // default

    public SettingsScreen(GameFrame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());
        setBackground(new Color(200, 0, 0)); // match main menu background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        JLabel titleLabel = new JLabel("Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Player Speed Label
        JLabel speedLabel = new JLabel("Player Speed:", SwingConstants.RIGHT);
        speedLabel.setFont(new Font("Arial", Font.BOLD, 24));
        speedLabel.setForeground(Color.YELLOW);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(speedLabel, gbc);

        // Player Speed Slider
        speedSlider = new JSlider(1, 20, Player.getSpeed());
        speedSlider.setMajorTickSpacing(5);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(speedSlider, gbc);

        // Bookshelf Label
        JLabel shelfLabel = new JLabel("Number of Bookshelves:", SwingConstants.RIGHT);
        shelfLabel.setFont(new Font("Arial", Font.BOLD, 24));
        shelfLabel.setForeground(Color.YELLOW);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(shelfLabel, gbc);

        // Bookshelf Slider
        bookshelfSlider = new JSlider(1, 20, bookshelfCount);
        bookshelfSlider.setMajorTickSpacing(5);
        bookshelfSlider.setPaintTicks(true);
        bookshelfSlider.setPaintLabels(true);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(bookshelfSlider, gbc);

        // Apply Button
        JButton applyButton = new JButton("Apply");
        styleButton(applyButton);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(applyButton, gbc);

        // Back Button
        JButton backButton = new JButton("Back");
        styleButton(backButton);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(backButton, gbc);

        // Button actions
        applyButton.addActionListener(this::applySettings);
        backButton.addActionListener(this::goBack);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.YELLOW);
        button.setForeground(Color.RED);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setFocusPainted(false);
    }

    private void applySettings(ActionEvent e) {
        Player.setSpeed(speedSlider.getValue());
        bookshelfCount = bookshelfSlider.getValue();

        // Styled message like CaughtScreen
        JDialog dialog = new JDialog(frame, "Settings Applied", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(frame);
        dialog.getContentPane().setBackground(new Color(255, 220, 100));

        JLabel messageLabel = new JLabel("Settings applied successfully!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        messageLabel.setForeground(Color.RED);
        dialog.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 16));
        okButton.setBackground(new Color(200, 0, 0));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.addActionListener(ev -> dialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 220, 100));
        buttonPanel.add(okButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void goBack(ActionEvent e) {
        frame.showStartMenu();
    }

    public static int getBookshelfCount() {
        return bookshelfCount;
    }
}
