package snackademy;

import javax.swing.*;

public class GameFrame extends JFrame {

    public StartMenuScreen frameStartMenu;

    public GameFrame() {
        setTitle("Snackademy");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Create StartMenuScreen once
        frameStartMenu = new StartMenuScreen(this);
        showStartMenu();

        setVisible(true);
    }

    // Show the existing start menu (do NOT create a new one)
    public void showStartMenu() {
        setContentPane(frameStartMenu);
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

        ui.setFocusable(true);
        ui.requestFocusInWindow();

        new GameController(ui);
    }

    // Optional: show leaderboard using the existing StartMenuScreen
    public void showLeaderboard() {
        if (frameStartMenu != null) {
            frameStartMenu.showLeaderboard();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
