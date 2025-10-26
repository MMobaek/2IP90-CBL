package snackademy;

import javax.swing.*;

public class GameFrame extends JFrame {

    public StartMenuScreen frameStartMenu;
    private final MusicPlayer musicPlayer; // Single shared instance

    private final String menuMusic = "src/main/java/com/snackademy/resources/background.wav";
    private final String gameMusic = "src/main/java/com/snackademy/resources/background2.wav";

    public GameFrame() {
        super("Snackademy"); // set title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Single shared music player for all screens
        musicPlayer = new MusicPlayer();

        // Stop music when window closes
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                musicPlayer.stopMusic();
            }
        });

        // Initialize start menu
        frameStartMenu = new StartMenuScreen(this);
        showStartMenu();

        setVisible(true);
    }

    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    public void playMenuMusic() {
        musicPlayer.stopMusic();
        musicPlayer.playMusic(menuMusic, true);
    }

    public void playGameMusic() {
        musicPlayer.stopMusic();
        musicPlayer.playMusic(gameMusic, true);
    }

    public void showStartMenu() {
        setContentPane(frameStartMenu);
        revalidate();
        repaint();
        playMenuMusic();
    }

    public void startGame() {
        UILayout ui = new UILayout();
        setContentPane(ui);
        revalidate();
        repaint();

        ui.setFocusable(true);
        ui.requestFocusInWindow();

        new GameController(ui); // only one controller instance
        playGameMusic();        // only one background track plays
    }

    public void showSettingsScreen() {
        SettingsScreen settings = new SettingsScreen(this);
        setContentPane(settings);
        revalidate();
        repaint();
    }

    public void showLeaderboard() {
        if (frameStartMenu != null) {
            frameStartMenu.showLeaderboard();
            playMenuMusic();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
