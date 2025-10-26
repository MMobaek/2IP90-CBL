package snackademy;

import javax.swing.*;
import java.awt.*;

/**
 * Custom dialog for saving progress, styled like CaughtScreen.
 * Combines confirmation and name input in one dialog with improved colors.
 */
public class SaveProgressScreen extends JDialog {

    private String playerName = null;

    private static final Color BACKGROUND_COLOR = new Color(255, 217, 102); // warm golden yellow
    private static final Color BUTTON_COLOR = new Color(211, 47, 47); // bright red
    private static final Color TEXT_COLOR = new Color(93, 31, 31); // dark brown/red for labels
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 16);

    public SaveProgressScreen(JFrame parent, int snackCounter) {
        super(parent, "Save Progress", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        setSize(450, 200);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Prompt label
        JLabel promptLabel = new JLabel("Do you want to save your progress?", SwingConstants.CENTER);
        promptLabel.setFont(TITLE_FONT);
        promptLabel.setForeground(TEXT_COLOR);
        add(promptLabel, BorderLayout.NORTH);

        // Center panel for name input, initially hidden
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(BACKGROUND_COLOR);
        inputPanel.setVisible(false);

        JLabel nameLabel = new JLabel("Enter your name:", SwingConstants.CENTER);
        nameLabel.setFont(TITLE_FONT);
        nameLabel.setForeground(TEXT_COLOR);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.BOLD, 20));
        nameField.setForeground(TEXT_COLOR);
        nameField.setHorizontalAlignment(SwingConstants.CENTER);

        inputPanel.add(nameLabel, BorderLayout.NORTH);
        inputPanel.add(nameField, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        styleButton(yesButton);
        styleButton(noButton);

        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        yesButton.addActionListener(e -> {
            promptLabel.setVisible(false);
            yesButton.setVisible(false);
            noButton.setVisible(false);
            inputPanel.setVisible(true);

            JButton saveButton = new JButton("Save");
            styleButton(saveButton);
            buttonPanel.add(saveButton);
            buttonPanel.revalidate();
            buttonPanel.repaint();

            saveButton.addActionListener(ev -> {
                String text = nameField.getText().trim();
                if (!text.isEmpty()) {
                    playerName = text;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            });
        });

        noButton.addActionListener(e -> dispose());
    }

    private void styleButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE); // white text for readability
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    /**
     * Show the dialog and return the entered name.
     * @return the player name, or null if cancelled
     */
    public String getPlayerName() {
        setVisible(true); // blocks until dialog closes
        return playerName;
    }
}
