package snackademy;

import java.awt.*;
import javax.swing.*;

/**
 * Settings screen to adjust game options such as player speed.
 */
public class SettingsScreen extends JPanel {

    private final GameFrame frame;
    private final JSlider speedSlider;

    /**
     * Constructs the settings screen.
     *
     * @param frame the main game frame
     */
    public SettingsScreen(GameFrame frame) {
        this.frame = frame;

        setLayout(null);
        setBackground(Color.YELLOW);

        JLabel label = new JLabel("Player Speed:");
        label.setBounds(50, 50, 150, 30);
        add(label);

        speedSlider = new JSlider(1, 20, Player.getSpeed());
        speedSlider.setBounds(200, 50, 300, 50);
        add(speedSlider);

        JButton applyButton = new JButton("Apply");
        applyButton.setBounds(200, 120, 100, 30);
        add(applyButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(320, 120, 100, 30);
        add(backButton);

        applyButton.addActionListener(this::applySpeed);
        backButton.addActionListener(this::goBack);
    }

    /** Applies the speed selected in the slider. */
    private void applySpeed(java.awt.event.ActionEvent e) {
        Player.setSpeed(speedSlider.getValue());
    }

    /** Goes back to the start menu. */
    private void goBack(java.awt.event.ActionEvent e) {
        frame.showStartMenu();
    }
}
