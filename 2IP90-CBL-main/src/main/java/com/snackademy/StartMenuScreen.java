package snackademy;

import java.awt.*;
import javax.swing.*;

/**
 * Start menu screen with Start Game, Help, and Settings buttons.
 * Allows navigation to the game, help panel, or settings panel.
 */
public class StartMenuScreen extends JPanel {

    private final JFrame frame;

    /**
     * Constructs the start menu screen.
     *
     * @param frame the main application frame
     */
    public StartMenuScreen(JFrame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());
        setBackground(new Color(200, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel title = new JLabel("Snackademy");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.YELLOW);
        gbc.gridy = 0;
        add(title, gbc);

        JButton startButton = new JButton("Start Game");
        styleButton(startButton);
        gbc.gridy = 1;
        add(startButton, gbc);

        JButton helpButton = new JButton("Help");
        styleButton(helpButton);
        gbc.gridy = 2;
        add(helpButton, gbc);

        JButton settingsButton = new JButton("Settings");
        styleButton(settingsButton);
        gbc.gridy = 3;
        add(settingsButton, gbc);

        // Action listeners
        startButton.addActionListener(this::startGame);
        helpButton.addActionListener(this::showHelp);
        settingsButton.addActionListener(this::showSettings);
    }

    /**
     * Styles a JButton consistently for the start menu.
     *
     * @param button the button to style
     */
    private void styleButton(JButton button) {
        button.setBackground(Color.YELLOW);
        button.setForeground(Color.RED);
        button.setFont(new Font("Arial", Font.BOLD, 24));
    }

    /**
     * Starts the game and switches to the game UI.
     *
     * @param e action event from the button
     */
    private void startGame(java.awt.event.ActionEvent e) {
        UILayout ui = new UILayout();
        frame.setContentPane(ui);
        frame.revalidate();
        frame.repaint();

        ui.getGamePanel().requestFocusInWindow();
        new GameController(ui);
    }

    /**
     * Shows the help panel with instructions.
     *
     * @param e action event from the button
     */
    private void showHelp(java.awt.event.ActionEvent e) {
        JPanel helpPanel = new JPanel(new BorderLayout());
        helpPanel.setBackground(new Color(200, 0, 0));

        JLabel helpText = new JLabel(
            "<html><center>Use WASD or arrow keys to move.<br>" 
            + "Deliver snacks to the desk.<br>" 
            + "Don't get caught by the librarian!</center></html>"
        );
        helpText.setForeground(Color.YELLOW);
        helpText.setFont(new Font("Arial", Font.BOLD, 24));
        helpText.setHorizontalAlignment(SwingConstants.CENTER);
        helpPanel.add(helpText, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        styleButtonSmall(backButton);
        backButton.addActionListener(this::backToMenu);

        helpPanel.add(backButton, BorderLayout.SOUTH);

        frame.setContentPane(helpPanel);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Shows the settings panel with player speed adjustment.
     *
     * @param e action event from the button
     */
    private void showSettings(java.awt.event.ActionEvent e) {
        JPanel settingsPanel = new JPanel(new GridBagLayout());
        settingsPanel.setBackground(new Color(200, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel settingsTitle = new JLabel("Settings");
        settingsTitle.setFont(new Font("Arial", Font.BOLD, 36));
        settingsTitle.setForeground(Color.YELLOW);
        gbc.gridy = 0;
        settingsPanel.add(settingsTitle, gbc);

        JLabel speedLabel = new JLabel("Player speed:");
        speedLabel.setFont(new Font("Arial", Font.BOLD, 24));
        speedLabel.setForeground(Color.YELLOW);
        gbc.gridy = 1;
        settingsPanel.add(speedLabel, gbc);

        JSlider speedSlider = new JSlider(1, 20, Player.getSpeed());
        speedSlider.setMajorTickSpacing(5);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        gbc.gridy = 2;
        settingsPanel.add(speedSlider, gbc);

        JButton applyButton = new JButton("Apply");
        styleButtonSmall(applyButton);
        applyButton.addActionListener(ev -> applySpeed(speedSlider));
        gbc.gridy = 3;
        settingsPanel.add(applyButton, gbc);

        JButton backButton = new JButton("Back to Menu");
        styleButtonSmall(backButton);
        backButton.addActionListener(this::backToMenu);
        gbc.gridy = 4;
        settingsPanel.add(backButton, gbc);

        frame.setContentPane(settingsPanel);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Applies the speed selected in the slider.
     *
     * @param slider the JSlider containing the new speed value
     */
    private void applySpeed(JSlider slider) {
        Player.setSpeed(slider.getValue());
    }

    /**
     * Returns to the main menu.
     *
     * @param e action event from the button
     */
    private void backToMenu(java.awt.event.ActionEvent e) {
        frame.setContentPane(this);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Styles a smaller JButton for back/apply buttons.
     *
     * @param button the button to style
     */
    private void styleButtonSmall(JButton button) {
        button.setBackground(Color.YELLOW);
        button.setForeground(Color.RED);
        button.setFont(new Font("Arial", Font.BOLD, 20));
    }
}
