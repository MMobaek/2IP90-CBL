package snackademy;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Snackademy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showStartMenu() {
        StartMenuScreen startMenu = new StartMenuScreen(this);
        setContentPane(startMenu);
        revalidate();
        repaint();
    }

    public void showSettingsScreen() {
        SettingsScreen settings = new SettingsScreen(this);
        setContentPane(settings);
        revalidate();
        repaint();
    }

    public void startGame() {
        UILayout ui = new UILayout();
        setContentPane(ui);
        revalidate();
        repaint();

        // Focus is crucial for key bindings
        ui.setFocusable(true);
        ui.requestFocusInWindow();

        new GameController(ui);
    }
}
