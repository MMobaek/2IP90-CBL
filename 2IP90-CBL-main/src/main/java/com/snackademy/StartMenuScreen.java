package snackademy;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class StartMenuScreen extends JPanel {

    public final GameFrame frame;
    public final List<Integer> leaderboard = new ArrayList<>();

    public StartMenuScreen(GameFrame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());
        setBackground(new Color(200, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;

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

    private void styleButton(JButton button) {
        button.setBackground(Color.YELLOW);
        button.setForeground(Color.RED);
        button.setFont(new Font("Arial", Font.BOLD, 24));
    }

    private void showHelp() {
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
        backButton.addActionListener(e -> frame.showStartMenu());
        helpPanel.add(backButton, BorderLayout.SOUTH);

        frame.setContentPane(helpPanel);
        frame.revalidate();
        frame.repaint();
    }

    public void showLeaderboard() {
        StringBuilder sb = new StringBuilder("<html><center>Leaderboard:<br>");
        leaderboard.stream().sorted(Collections.reverseOrder()).forEach(score -> sb.append(score).append("<br>"));
        sb.append("</center></html>");

        JPanel lbPanel = new JPanel(new BorderLayout());
        lbPanel.setBackground(new Color(200, 0, 0));
        JLabel lbLabel = new JLabel(sb.toString(), SwingConstants.CENTER);
        lbLabel.setForeground(Color.YELLOW);
        lbLabel.setFont(new Font("Arial", Font.BOLD, 24));
        lbPanel.add(lbLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Menu");
        backButton.setBackground(Color.YELLOW);
        backButton.setForeground(Color.RED);
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.addActionListener(e -> frame.showStartMenu());
        lbPanel.add(backButton, BorderLayout.SOUTH);

        frame.setContentPane(lbPanel);
        frame.revalidate();
        frame.repaint();
    }
}
