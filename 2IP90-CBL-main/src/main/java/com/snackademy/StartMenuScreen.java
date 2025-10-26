package snackademy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StartMenuScreen extends JPanel {

    private final GameFrame frame; // keep as GameFrame

    public StartMenuScreen(GameFrame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());
        setBackground(new Color(200, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

        // Title
        JLabel title = new JLabel("Snackademy");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.YELLOW);
        gbc.gridy = 0;
        add(title, gbc);

        // Start Game Button
        JButton startButton = new JButton("Start Game");
        styleButton(startButton);
        gbc.gridy = 1;
        add(startButton, gbc);

        // Help Button
        JButton helpButton = new JButton("Help");
        styleButton(helpButton);
        gbc.gridy = 2;
        add(helpButton, gbc);

        // Settings Button
        JButton settingsButton = new JButton("Settings");
        styleButton(settingsButton);
        gbc.gridy = 3;
        add(settingsButton, gbc);

        // Actions
        startButton.addActionListener(this::startGame);
        helpButton.addActionListener(this::showHelp);
        settingsButton.addActionListener(this::showSettings);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.YELLOW);
        button.setForeground(Color.RED);
        button.setFont(new Font("Arial", Font.BOLD, 24));
    }

    private void startGame(ActionEvent e) {
        UILayout ui = new UILayout();
        frame.setContentPane(ui);
        frame.revalidate();
        frame.repaint();

        ui.setFocusable(true);
        ui.requestFocusInWindow();
        new GameController(ui);
    }

    private void showHelp(ActionEvent e) {
        JPanel helpPanel = new JPanel(new BorderLayout());
        helpPanel.setBackground(new Color(200, 0, 0));

        JLabel helpText = new JLabel(
            "<html><center>Use WASD or arrow keys to move.<br>" +
            "Deliver snacks to the desk.<br>" +
            "Don't get caught by the librarian!</center></html>",
            SwingConstants.CENTER
        );
        helpText.setForeground(Color.YELLOW);
        helpText.setFont(new Font("Arial", Font.BOLD, 24));
        helpPanel.add(helpText, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(Color.YELLOW);
        backButton.setForeground(Color.RED);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(ev -> frame.showStartMenu()); // use GameFrame method
        helpPanel.add(backButton, BorderLayout.SOUTH);

        frame.setContentPane(helpPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void showSettings(ActionEvent e) {
        frame.showSettingsScreen(); // use GameFrame method
    }
}
