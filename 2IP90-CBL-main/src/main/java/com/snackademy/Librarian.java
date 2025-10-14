package snackademy;

import java.awt.Image;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents the librarian in the Snackademy game.
 * 
 * The librarian alternates between three attention states:
 * inattentive, transitioning, and attentive. These states change
 * automatically based on randomly generated time milestones.
 * 
 * Images for the different states are loaded from the resources folder.
 * The librarian cycles through states continuously.
 * 
 */
public class Librarian {

    /** The width in pixels of the librarian image. */
    private static final int WIDTH = 150;

    /** The height in pixels of the librarian image. */
    private static final int HEIGHT = 150;

    /** The alignment constant for the JLabel. */
    private static final int CENTER = JLabel.CENTER;

    /** The visual JLabel representing the librarian. */
    private JLabel label;

    /** Random number generator for attention timing milestones. */
    private final Random random = new Random();

    /** Start time in milliseconds for the current attention cycle. */
    private long startTime = System.currentTimeMillis();

    /** Elapsed time in milliseconds since the start of the cycle. */
    private long elapsed;

    /** Time (in ms) when librarian transitions to the 'transition' state. */
    private int firstMilestone;

    /** Time (in ms) when librarian transitions to the 'attentive' state. */
    private int secondMilestone;

    /** Time (in ms) when librarian resets to the 'inattentive' state. */
    private int thirdMilestone;

    /** True if the librarian has reached the first milestone. */
    private boolean firstReached;

    /** True if the librarian has reached the second milestone. */
    private boolean secondReached;

    /** Icon displayed when librarian is inattentive. */
    private ImageIcon inattentiveIcon;

    /** Icon displayed when librarian is transitioning. */
    private ImageIcon transitionIcon;

    /** Icon displayed when librarian is attentive. */
    private ImageIcon attentiveIcon;

    /** The current icon being displayed. */
    private ImageIcon currentIcon;

    /**
     * Constructs a librarian at position (0, 0).
     * Loads icons and initializes the attention cycle.
     */
    public Librarian() {
        this(0, 0);
    }

    /**
     * Constructs a librarian at a specific coordinate position.
     *
     * @param x The X coordinate on the panel.
     * @param y The Y coordinate on the panel.
     */
    public Librarian(int x, int y) {
        loadIcons();
        randomizeMilestones();
        currentIcon = inattentiveIcon;
        label = new JLabel(currentIcon, CENTER);
        label.setBounds(x, y, WIDTH, HEIGHT);
    }

    /**
     * Loads and scales the librarian’s icons for each attention state.
     */
    private void loadIcons() {
        inattentiveIcon = loadAndScale("/snackademy/resources/librarianInattentive.png");
        transitionIcon = loadAndScale("/snackademy/resources/librarianTransition.png");
        attentiveIcon = loadAndScale("/snackademy/resources/librarianAttentive.png");
    }

    /**
     * Loads an image from resources and scales it to the standard librarian size.
     *
     * @param path The path to the image file in the resources directory.
     * @return A scaled {@link ImageIcon} object.
     * @throws RuntimeException if the image cannot be found or loaded.
     */
    private ImageIcon loadAndScale(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            throw new RuntimeException("Image not found: " + path);
        }

        try {
            Image img = javax.imageio.ImageIO.read(url);
            Image scaled = img.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read image: " + path, e);
        }
    }

    /**
     * Updates the librarian’s current attention state.
     * 
     * This method should be called repeatedly in a game loop or timer
     * to allow the librarian to automatically change between inattentive,
     * transition, and attentive states.
     */
    public void updateStatus() {
        elapsed = System.currentTimeMillis() - startTime;

        if (elapsed >= firstMilestone && !firstReached) {
            transitionTo(transitionIcon);
            firstReached = true;
        } else if (elapsed >= secondMilestone && firstReached && !secondReached) {
            transitionTo(attentiveIcon);
            secondReached = true;
        } else if (elapsed >= thirdMilestone && secondReached) {
            resetCycle();
        }
    }

    /**
     * Changes the librarian’s displayed image and resets the cycle timer.
     *
     * @param icon The new icon to display.
     */
    private void transitionTo(ImageIcon icon) {
        currentIcon = icon;
        label.setIcon(icon);
        startTime = System.currentTimeMillis();
    }

    /**
     * Resets the attention cycle to the inattentive state.
     * Randomizes new timing milestones.
     */
    public void resetCycle() {
        firstReached = false;
        secondReached = false;
        randomizeMilestones();
        transitionTo(inattentiveIcon);
    }

    /**
     * Randomly generates new milestone times for the next attention cycle.
     * Each milestone duration is expressed in milliseconds.
     */
    private void randomizeMilestones() {
        firstMilestone = random.nextInt(10000) + 6000; // 6–16 seconds
        secondMilestone = random.nextInt(1500) + 500; // 0.5–2 seconds
        thirdMilestone = random.nextInt(4000) + 4000; // 4–8 seconds
    }

    /**
     * Returns the JLabel component representing the librarian.
     *
     * @return The JLabel displaying the librarian’s current state.
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Returns the currently active icon of the librarian.
     *
     * @return The current {@link ImageIcon}.
     */
    public ImageIcon getIcon() {
        return currentIcon;
    }

    /**
     * Returns the icon used when the librarian is attentive.
     *
     * @return The attentive state {@link ImageIcon}.
     */
    public ImageIcon getAttentiveIcon() {
        return attentiveIcon;
    }
}
