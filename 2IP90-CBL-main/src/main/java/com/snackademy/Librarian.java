package snackademy;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents the librarian in the Snackademy game.
 *
 * <p>The librarian alternates between three attention states:
 * INATTENTIVE → TRANSITION → ATTENTIVE → back to INATTENTIVE.
 * Each phase lasts for a random duration defined by milestone times.</p>
 */
public class Librarian {

    /** Width and height of the librarian image. */
    private static final int SIZE = 150;

    /** Alignment constant for the JLabel. */
    private static final int CENTER = JLabel.CENTER;

    /** Visual representation of the librarian. */
    private final JLabel label;

    /** Random generator for timing milestones. */
    private final Random random = new Random();

    /** The librarian’s current attention state. */
    private State currentState = State.INATTENTIVE;

    /** Icons for each attention state. */
    private ImageIcon inattentiveIcon;
    private ImageIcon transitionIcon;
    private ImageIcon attentiveIcon;

    /** Currently displayed icon. */
    private ImageIcon currentIcon;

    /** Timing values for the state transitions. */
    private long startTime = System.currentTimeMillis();
    private int firstMilestone;
    private int secondMilestone;
    private int thirdMilestone;

    /** Defines the three librarian attention states. */
    private enum State {
        INATTENTIVE,
        TRANSITION,
        ATTENTIVE
    }

    /** Constructs a librarian at position (0, 0). */
    public Librarian() {
        this(0, 0);
    }

    /**
     * Constructs a librarian at a specific (x, y) position.
     *
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Librarian(int x, int y) {
        loadIcons();
        randomizeMilestones();
        currentIcon = inattentiveIcon;
        label = new JLabel(currentIcon, CENTER);
        label.setBounds(x, y, SIZE, SIZE);
    }

    /**
     * Loads and scales all icons for the librarian’s states.
     */
    private void loadIcons() {
        inattentiveIcon = loadAndScale("/snackademy/resources/librarianInattentive.png");
        transitionIcon = loadAndScale("/snackademy/resources/librarianTransition.png");
        attentiveIcon = loadAndScale("/snackademy/resources/librarianAttentive.png");
    }

    /**
     * Loads and scales an image to the standard librarian size.
     *
     * @param path Path of the image in resources.
     * @return The scaled {@link ImageIcon}.
     */
    private ImageIcon loadAndScale(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            throw new IllegalStateException("Image not found: " + path);
        }
        try {
            Image img = javax.imageio.ImageIO.read(url);
            Image scaled = img.getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to read image: " + path, e);
        }
    }

    /**
     * Checks if the librarian is currently attentive.
     *
     * @return {@code true} if in the ATTENTIVE state.
     */
    public boolean isAttentive() {
        return currentState == State.ATTENTIVE;
    }

    /**
     * Updates the librarian’s attention state based on elapsed time.
     * Call this periodically from the game loop.
     */
    public void updateStatus() {
        long elapsed = System.currentTimeMillis() - startTime;

        // Debug print
        System.out.println("[Lib] elapsed=" + elapsed
                + " first=" + firstMilestone
                + " second=" + secondMilestone
                + " third=" + thirdMilestone
                + " state=" + currentState);

        switch (currentState) {
            case INATTENTIVE:
                handleInattentive(elapsed);
                break;

            case TRANSITION:
                handleTransition(elapsed);
                break;

            case ATTENTIVE:
                handleAttentive(elapsed);
                break;

            default:
                throw new IllegalStateException("Unknown state: " + currentState);
        }
    }

    /**
     * Handles transition from the INATTENTIVE state.
     *
     * @param elapsed Time elapsed since last state change.
     */
    private void handleInattentive(long elapsed) {
        if (elapsed >= firstMilestone) {
            transitionTo(State.TRANSITION, transitionIcon);
        }
    }

    /**
     * Handles transition from the TRANSITION state.
     *
     * @param elapsed Time elapsed since last state change.
     */
    private void handleTransition(long elapsed) {
        if (elapsed >= secondMilestone) {
            transitionTo(State.ATTENTIVE, attentiveIcon);
        }
    }

    /**
     * Handles transition from the ATTENTIVE state.
     *
     * @param elapsed Time elapsed since last state change.
     */
    private void handleAttentive(long elapsed) {
        if (elapsed >= thirdMilestone) {
            resetCycle();
        }
    }

    /**
     * Changes the librarian’s icon and updates internal state.
     *
     * @param nextState New state.
     * @param icon      Icon representing that state.
     */
    private void transitionTo(State nextState, ImageIcon icon) {
        currentState = nextState;
        currentIcon = icon;
        label.setIcon(icon);
        startTime = System.currentTimeMillis();
    }

    /**
     * Resets the librarian to the inattentive state
     * and starts a new random attention cycle.
     */
    private void resetCycle() {
        randomizeMilestones();
        transitionTo(State.INATTENTIVE, inattentiveIcon);
    }

    /**
     * Randomly assigns milestone times (ms) for a new cycle.
     */
    private void randomizeMilestones() {
        firstMilestone = random.nextInt(10000) + 6000; // 6–16 s
        secondMilestone = random.nextInt(1500) + 500;  // 0.5–2 s
        thirdMilestone = random.nextInt(4000) + 4000;  // 4–8 s
    }

    /**
     * Returns the JLabel for display on the panel.
     *
     * @return the JLabel representing the librarian.
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Returns the currently displayed icon.
     *
     * @return the current {@link ImageIcon}.
     */
    public ImageIcon getIcon() {
        return currentIcon;
    }

    /**
     * Returns the icon used when the librarian is attentive.
     *
     * @return the attentive state {@link ImageIcon}.
     */
    public ImageIcon getAttentiveIcon() {
        return attentiveIcon;
    }
}
