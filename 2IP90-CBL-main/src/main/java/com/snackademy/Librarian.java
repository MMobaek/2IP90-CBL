package snackademy;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents the librarian in the Snackademy game.
 * The librarian alternates between INATTENTIVE → TRANSITION → ATTENTIVE → INATTENTIVE.
 */
public class Librarian {

    private static final int SIZE = 150;
    private static final int CENTER = JLabel.CENTER;

    private final JLabel label;
    private final Random random = new Random();
    private State currentState = State.INATTENTIVE;

    private ImageIcon inattentiveIcon;
    private ImageIcon transitionIcon;
    private ImageIcon attentiveIcon;
    private ImageIcon currentIcon;

    private long startTime = System.currentTimeMillis();
    private int firstMilestone;
    private int secondMilestone;
    private int thirdMilestone;

    /** The possible attention states of the librarian. */
    private enum State {
        INATTENTIVE,
        TRANSITION,
        ATTENTIVE
    }

    /** Default constructor placing librarian at (0,0). */
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

    /** Loads icons for all librarian states. */
    private void loadIcons() {
        inattentiveIcon = loadAndScale("/snackademy/resources/librarianInattentive.png");
        transitionIcon = loadAndScale("/snackademy/resources/librarianTransition.png");
        attentiveIcon = loadAndScale("/snackademy/resources/librarianAttentive.png");
    }

    /**
     * Loads and scales an image to the standard librarian size.
     *
     * @param path path of the image in resources
     * @return scaled ImageIcon
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

    /** Returns true if the librarian is currently attentive. */
    public boolean isAttentive() {
        return currentState == State.ATTENTIVE;
    }

    /** Updates the librarian state based on elapsed time. Call this periodically. */
    public void updateStatus() {
        long elapsed = System.currentTimeMillis() - startTime;

        if (currentState == State.INATTENTIVE) {
            checkInattentive(elapsed);
            return;
        }

        if (currentState == State.TRANSITION) {
            checkTransition(elapsed);
            return;
        }

        if (currentState == State.ATTENTIVE) {
            checkAttentive(elapsed);
        }
    }

    /** Handles timing logic when librarian is INATTENTIVE. */
    private void checkInattentive(long elapsed) {
        if (elapsed >= firstMilestone) {
            transitionTo(State.TRANSITION, transitionIcon);
        }
    }

    /** Handles timing logic when librarian is TRANSITION. */
    private void checkTransition(long elapsed) {
        if (elapsed >= secondMilestone) {
            transitionTo(State.ATTENTIVE, attentiveIcon);
        }
    }

    /** Handles timing logic when librarian is ATTENTIVE. */
    private void checkAttentive(long elapsed) {
        if (elapsed >= thirdMilestone) {
            resetCycle();
        }
    }

    /**
     * Changes the librarian state and updates the icon.
     *
     * @param nextState new state
     * @param icon icon for the new state
     */
    private void transitionTo(State nextState, ImageIcon icon) {
        currentState = nextState;
        currentIcon = icon;
        label.setIcon(icon);
        startTime = System.currentTimeMillis();
    }

    /** Resets the librarian to INATTENTIVE state and randomizes milestones. */
    private void resetCycle() {
        randomizeMilestones();
        transitionTo(State.INATTENTIVE, inattentiveIcon);
    }

    /** Randomizes milestone durations for the next cycle. */
    private void randomizeMilestones() {
        firstMilestone = random.nextInt(10000) + 6000;
        secondMilestone = random.nextInt(1500) + 500;
        thirdMilestone = random.nextInt(4000) + 4000;
    }

    /** Returns the JLabel representing the librarian. */
    public JLabel getLabel() {
        return label;
    }

    /** Returns the current icon of the librarian. */
    public ImageIcon getIcon() {
        return currentIcon;
    }

    /** Returns the attentive state icon. */
    public ImageIcon getAttentiveIcon() {
        return attentiveIcon;
    }

    /**
     * Returns a scaled icon for a given state.
     *
     * @param state "INATTENTIVE", "TRANSITION", "ATTENTIVE"
     * @param width desired width
     * @param height desired height
     * @return scaled ImageIcon
     */
    public ImageIcon getScaledIcon(String state, int width, int height) {
        ImageIcon baseIcon;
        switch (state) {
            case "INATTENTIVE":
                baseIcon = inattentiveIcon;
                break;
            case "TRANSITION":
                baseIcon = transitionIcon;
                break;
            case "ATTENTIVE":
                baseIcon = attentiveIcon;
                break;
            default:
                baseIcon = currentIcon;
                break;
        }

        Image img = baseIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    /** Returns the current state name as a string. */
    public String getCurrentStateName() {
        return currentState.name();
    }
}
