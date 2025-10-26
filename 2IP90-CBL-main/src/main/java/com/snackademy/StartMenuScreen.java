package snackademy;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Start menu screen for Snackademy.
 * Provides buttons to start the game, view help, adjust settings, and view the leaderboard.
 */
public class StartMenuScreen extends JPanel {

    public final GameFrame frame;
    public final List<ScoreEntry> leaderboard = new ArrayList<>();

    /**
     * Create a start menu screen.
     * @param frame the parent GameFrame
     */
    public StartMenuScreen(GameFrame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());
        setBackground(new Color(200, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

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

        JButton leaderboardButton = new JButton("Leaderboard");
        styleButton(leaderboardButton);
        gbc.gridy = 4;
        add(leaderboardButton, gbc);

        startButton.addActionListener(e -> frame.startGame());
        helpButton.addActionListener(e -> showHelp());
        settingsButton.addActionListener(e -> frame.showSettingsScreen());
        leaderboardButton.addActionListener(e -> showLeaderboard());
    }

    /**
     * Style a JButton consistently.
     * @param button the button to style
     */
    private void styleButton(JButton button) {
        button.setBackground(Color.YELLOW);
        button.setForeground(Color.RED);
        button.setFont(new Font("Arial", Font.BOLD, 24));
    }

    /**
     * Show the help screen with instructions.
     */
    private void showHelp() {
        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(new GridBagLayout());
        helpPanel.setBackground(new Color(200, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel helpText = new JLabel(
            "<html><center>Welcome to Snackademy!<br>" 
            + "Your mission: sneak snacks into the library of Metaforum at TU/e.<br>" 
            + "- Move using WASD or arrow keys.<br>" 
            + "- Collect snacks at the Snack Station and deliver them to the Desk.<br>" 
            + "- Beware! You cannot move when the librarian is watching<br>" 
            + "  if you are carrying snacks.<br>" 
            + "- If you have no snacks, you can move freely even while<br>" 
            + "  the librarian is watching.<br>" 
            + "- Do not walk into bookshelves; the librarian will notice.<br>" 
            + "- Adjust your speed and the number of bookshelves in Settings.<br>" 
            + "Deliver as many snacks as possible without getting caught!" 
            + "</center></html>",
            SwingConstants.CENTER
        );
        helpText.setForeground(Color.YELLOW);
        helpText.setFont(new Font("Arial", Font.BOLD, 24));
        helpPanel.add(helpText, gbc);

        gbc.gridy = 1;
        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(Color.YELLOW);
        backButton.setForeground(Color.RED);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> frame.showStartMenu());
        helpPanel.add(backButton, gbc);

        frame.setContentPane(helpPanel);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Show the leaderboard with names and scores.
     */
    public void showLeaderboard() {
        JPanel lbPanel = new JPanel();
        lbPanel.setLayout(new GridBagLayout());
        lbPanel.setBackground(new Color(200, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Leaderboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        lbPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        JPanel scoresPanel = new JPanel();
        scoresPanel.setBackground(new Color(200, 0, 0));
        scoresPanel.setLayout(new GridBagLayout());

        if (leaderboard.isEmpty()) {
            JLabel emptyLabel = new JLabel("No saved progress yet!", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 24));
            emptyLabel.setForeground(Color.YELLOW);
            scoresPanel.add(emptyLabel);
        } else {
            leaderboard.sort(null); // Sort descending by score
            int rank = 1;
            for (ScoreEntry entry : leaderboard) {
                JLabel scoreLabel = new JLabel(
                    rank + ". " + entry.getName() + ": "
                    + entry.getScore() + " snacks delivered",
                    SwingConstants.CENTER
                );
                scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
                scoreLabel.setForeground(Color.YELLOW);
                GridBagConstraints gbcScore = new GridBagConstraints();
                gbcScore.gridy = rank - 1;
                gbcScore.gridx = 0;
                gbcScore.insets = new Insets(5, 5, 5, 5);
                scoresPanel.add(scoreLabel, gbcScore);
                rank++;
            }
        }

        lbPanel.add(scoresPanel, gbc);

        gbc.gridy = 2;
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBackground(Color.YELLOW);
        backButton.setForeground(Color.RED);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> frame.showStartMenu());
        lbPanel.add(backButton, gbc);

        frame.setContentPane(lbPanel);
        frame.revalidate();
        frame.repaint();
    }
}
