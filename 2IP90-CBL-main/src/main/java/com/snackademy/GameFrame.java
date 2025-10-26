package snackademy;

import javax.swing.*;

/**
 * The main JFrame for the Snackademy game.
 * <p>
 * Handles different screens (start menu, game UI, settings, leaderboard) 
 * and manages a single shared music player instance.
 */
public class GameFrame extends JFrame {

    // -------------------------------------------------------------------------
    // Instance Variables
    // -------------------------------------------------------------------------

    /** The start menu screen displayed at launch. */
    public StartMenuScreen frameStartMenu;

    /** Single shared music player for all screens. */
    private final MusicPlayer musicPlayer;

    /** Resource path for start menu background music. */
    private final String menuMusic = "src/main/java/com/snackademy/resources/background.wav";

    /** Resource path for game background music. */
    private final String gameMusic = "src/main/java/com/snackademy/resources/background2.wav";

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /** Initializes the main game window and start menu. */
    public GameFrame() {
        super("Snackademy"); // Set window title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null); // Center on screen

        // Initialize single shared music player
        musicPlayer = new MusicPlayer();

        // Stop music when window closes
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                musicPlayer.stopMusic();
            }
        });

        // Initialize and display start menu
        frameStartMenu = new StartMenuScreen(this);
        showStartMenu();

        setVisible(true);
    }

    // -------------------------------------------------------------------------
    // Music Control
    // -------------------------------------------------------------------------

    /** Returns the shared MusicPlayer instance. */
    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    /** Plays the start menu background music. */
    public void playMenuMusic() {
        musicPlayer.stopMusic();
        musicPlayer.playMusic(menuMusic, true);
    }

    /** Plays the game background music. */
    public void playGameMusic() {
        musicPlayer.stopMusic();
        musicPlayer.playMusic(gameMusic, true);
    }

    // -------------------------------------------------------------------------
    // Screen Navigation
    // -------------------------------------------------------------------------

    /** Displays the start menu screen. */
    public void showStartMenu() {
        setContentPane(frameStartMenu);
        revalidate();
        repaint();
        playMenuMusic();
    }

    /** Starts the main game. */
    public void startGame() {
        UILayout ui = new UILayout();
        setContentPane(ui);
        revalidate();
        repaint();

        // Ensure keyboard focus
        ui.setFocusable(true);
        ui.requestFocusInWindow();

        // Initialize game controller and start music
        new GameController(ui);
        playGameMusic();
    }

    /** Displays the settings screen. */
    public void showSettingsScreen() {
        SettingsScreen settings = new SettingsScreen(this);
        setContentPane(settings);
        revalidate();
        repaint();
    }

    /** Shows the leaderboard via the start menu screen. */
    public void showLeaderboard() {
        if (frameStartMenu != null) {
            frameStartMenu.showLeaderboard();
            playMenuMusic();
        }
    }

    // -------------------------------------------------------------------------
    // Main Method
    // -------------------------------------------------------------------------

    /** Launches the Snackademy game. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}
