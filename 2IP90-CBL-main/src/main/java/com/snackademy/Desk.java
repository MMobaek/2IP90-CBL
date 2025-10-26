package snackademy;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents a Desk object in the Snackademy game.
 * <p>
 * The desk can be displayed via a JLabel and its image can be scaled dynamically.
 */
public class Desk {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /** Default width and height of the desk image in pixels. */
    private static final int DEFAULT_SIZE = 150;

    // -------------------------------------------------------------------------
    // Instance Variables
    // -------------------------------------------------------------------------

    /** JLabel to display the desk in the UI. */
    private final JLabel label;

    /** ImageIcon representing the desk image. */
    private ImageIcon icon;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Constructs a Desk at the specified position.
     *
     * @param x X coordinate of the desk
     * @param y Y coordinate of the desk
     */
    public Desk(int x, int y) {
        icon = loadIcon("/snackademy/resources/desk.png");
        label = new JLabel(icon);

        // Position and size the label in the UI
        label.setBounds(x, y, DEFAULT_SIZE, DEFAULT_SIZE);
    }

    // -------------------------------------------------------------------------
    // Image Loading
    // -------------------------------------------------------------------------

    /**
     * Loads an ImageIcon from a resource path.
     *
     * @param path the resource path of the image
     * @return ImageIcon loaded from the resource
     * @throws IllegalStateException if the resource cannot be found
     */
    private ImageIcon loadIcon(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            throw new IllegalStateException("Desk image not found: " + path);
        }
        return new ImageIcon(url);
    }

    // -------------------------------------------------------------------------
    // Accessor Methods
    // -------------------------------------------------------------------------

    /**
     * Returns the JLabel used to display the desk.
     *
     * @return JLabel representing the desk
     */
    public JLabel getLabel() {
        return label;
    }

    /**
     * Returns a scaled version of the desk image for dynamic resizing.
     *
     * @param width new width in pixels
     * @param height new height in pixels
     * @return scaled ImageIcon
     */
    public ImageIcon getScaledIcon(int width, int height) {
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
