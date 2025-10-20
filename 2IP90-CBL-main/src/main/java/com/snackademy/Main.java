package snackademy;

import javax.swing.SwingUtilities;

/**
 * The main entry point for the Snackademy game.
 *
 * The game takes place in the Metaforum library, where you play as a student
 * attempting to snack or whisper to friends without being caught by the
 * librarian.
 *
 * Gameplay overview:
 * - When the librarian is looking away, you can move and act freely.
 * - When the librarian is watching, you must remain still or be caught.
 *
 * Class overview:
 * {@code Main} — launches the game and shows the start menu.
 * {@code UILayout} — builds and displays the main game window, positions objects, and handles
 * resizing.
 * {@code GameController} — connects the UI, player, and librarian; handles keyboard and button
 * input; updates game logic.
 * {@code Player} — manages the player’s position, movement, and walking animations.
 * {@code MovingPlayer} — handles keyboard controls for the player and triggers movement callbacks.
 * {@code Librarian} — controls the librarian’s states (INATTENTIVE, TRANSITION, ATTENTIVE) and
 * updates the icon accordingly.
 * {@code Desk} — represents a static desk object with a resizable image.
 * {@code Snackstation} — represents a static snack station object with a resizable image.
 *
 * Follows the Eindhoven University of Technology 2IP90 Java Coding Standard.
 * This ensures readable, consistent, and maintainable source code.
 * 
 * Authors:
 * Magnus Mobeak
 * Eline Smit
 * Eindhoven University of Technology - 2IP90 Programming
 */
public class Main {

    /** Launches the Snackademy game by showing the start menu. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
            frame.showStartMenu();
        });
    }
}
