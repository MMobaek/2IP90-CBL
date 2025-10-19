package snackademy;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * The main entry point for the Snackademy game.
 *
 * The game takes place in the Metaforum library, where you play as a student
 * attempting to snack or whisper to friends without being caught by the
 * librarian.
 *
 * Gameplay overview:
 * When the librarian is looking away, you can move and act freely.</li>
 * When the librarian is watching, you must remain still or be caught.</li>
 * 
 *
 * 
 * Class overview:
 * {@code Main} — launches the game and shows the start menu.
 * {@code UILayout} — builds and displays the main game window, positions objects, and handles 
 * resizing.
 * {@code GameController} — connects the UI, player, and librarian; handles keyboard and button 
 * input; updates game logic.</li>
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
 * @author Magnus Mobeak
 * @author Eline Smit
 *         Eindhoven University of Technology - 2IP90 Programming
 */

public class Main {

    /**
     * Launches the Snackademy game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> { // Makes the startmenu come first, before the UI stuff.
            JFrame frame = new JFrame("Snackademy");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            StartMenuScreen startMenu = new StartMenuScreen(frame);
            frame.add(startMenu).setVisible(true);
        });

        UILayout ui = new UILayout();
        new GameController(ui);
    }
}
