package snackademy;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

/**
 * Screen to display the leaderboard, showing the number of snacks delivered by players.
 */
public class LeaderboardScreen extends JPanel {

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Constructs a LeaderboardScreen.
     *
     * @param frame the parent GameFrame used to return to the start menu
     * @param leaderboard the list of scores to display (snacks delivered)
     */
    public LeaderboardScreen(GameFrame frame, List<Integer> leaderboard) {
        setLayout(new BorderLayout());
        setBackground(new Color(200, 0, 0)); // red background

        // Title label
        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.YELLOW);
        add(title, BorderLayout.NORTH);

        // Scores panel
        JPanel scoresPanel = new JPanel();
        scoresPanel.setBackground(new Color(200, 0, 0));
        scoresPanel.setLayout(new GridLayout(
            Math.max(leaderboard.size(), 1), 1, 10, 10
        ));

        // Display message if leaderboard is empty
        if (leaderboard.isEmpty()) {
            JLabel emptyLabel = new JLabel("No saved progress yet!", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 24));
            emptyLabel.setForeground(Color.YELLOW);
            scoresPanel.add(emptyLabel);
        } else {
            // Sort scores in descending order
            Collections.sort(leaderboard, Collections.reverseOrder());
            int rank = 1;
            for (int score : leaderboard) {
                JLabel scoreLabel = new JLabel(
                    rank + ". Snacks delivered: " + score, SwingConstants.CENTER
                );
                scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
                scoreLabel.setForeground(Color.YELLOW);
                scoresPanel.add(scoreLabel);
                rank++;
            }
        }

        add(scoresPanel, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back to Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBackground(Color.YELLOW);
        backButton.setForeground(Color.RED);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> frame.showStartMenu());
        add(backButton, BorderLayout.SOUTH);
    }
}
