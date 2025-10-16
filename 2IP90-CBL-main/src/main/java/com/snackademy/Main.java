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
 * {@code Main} — launches the game.</li>
 * {@code UILayout} — builds and displays the graphical user interface.</li>
 * {@code GameController} — handles keyboard input and timing logic.</li>
 * {@code Player} — manages the player’s movement and rendering.</li>
 * {@code Librarian} — controls the librarian’s attention states.</li>
 * 
 *
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
