package snackademy;

import javax.swing.SwingUtilities;

/**
 * The main entry point for the Snackademy game.
 *
 * Snackademy is set in the Metaforum library, where the player (a student)
 * must deliver snacks or whisper to friends without being caught by the librarian.
 *
 * Gameplay overview:
 * - The librarian has three states: INATTENTIVE, TRANSITION, and ATTENTIVE.
 * - When the librarian is looking away, the player can move and act freely.
 * - When the librarian is watching, the player must remain still or risk being caught.
 *
 * Class overview:
 * - {@code Main} — launches the game and shows the start menu.
 * - {@code UILayout} — builds and displays the main game window, positions all objects,
 *   handles resizing, movable text, snack counter, and back button.
 * - {@code GameController} — manages game logic, keyboard input, and connects
 *   the UI to the player and librarian.
 * - {@code Player} — represents the player character, handles position, movement,
 *   and walking animations.
 * - {@code MovingPlayer} — listens for keyboard input and triggers player movement callbacks.
 * - {@code Librarian} — manages the librarian’s state machine, updates icons and behavior.
 * - {@code Desk} — represents a static desk object with a resizable image.
 * - {@code Snackstation} — represents a static snack station object with a resizable image.
 * - {@code Bookshelf} — represents static bookshelf objects randomly positioned in the game.
 * - {@code DebugOverlayPanel} — optional overlay to display debug information about objects.
 * - {@code CaughtScreen} — displayed when the player is caught by the librarian.
 * - {@code StartMenuScreen} — the main start menu UI with buttons to launch the game,
 *   settings, help, and leaderboard.
 * - {@code SettingsScreen} — allows the player to adjust game settings such as
 *   number of bookshelves or difficulty.
 * - {@code LeaderboardScreen} — displays the leaderboard of players and their high scores.
 * - {@code SaveProgressScreen} — dialog for saving the player’s snack count and name.
 * - {@code ScoreEntry} — a data class storing the player name and score for the leaderboard.
 * - {@code MusicPlayer} — manages background music playback during the game.
 *
 * Authors:
 * - Magnus Mobeak
 * - Eline Smit
 *
 * Eindhoven University of Technology - 2IP90 Programming
 */
public class Main {

    /**
     * Launches the Snackademy game by showing the start menu.
     * Uses SwingUtilities.invokeLater to ensure GUI creation is on the Event Dispatch Thread.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.showStartMenu();
        });
    }
}
