package snackademy;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {
        setTitle("Snackademy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // fixed window size
        setLocationRelativeTo(null);

        // Show start menu initially
        showStartMenu();

        setVisible(true);
    }

    /**
     * Show the start menu screen.
     */
    public void showStartMenu() {
        StartMenuScreen startMenu = new StartMenuScreen(this);
        setContentPane(startMenu);
        revalidate();
        repaint();
    }

    /**
     * Show the settings screen.
     */
    public void showSettingsScreen() {
        SettingsScreen settings = new SettingsScreen(this);
        setContentPane(settings);
        revalidate();
        repaint(); // important to refresh the frame
    }

    /**
     * Start the main game screen.
     */
    public void startGame() {
        UILayout ui = new UILayout();
        setContentPane(ui);
        revalidate();
        repaint();

        // Ensure key bindings work
        ui.setFocusable(true);
        ui.requestFocusInWindow();

        new GameController(ui);
    }

    /**
     * Main method to launch the game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
