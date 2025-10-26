package snackademy;

import java.awt.Image;
import java.awt.Polygon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents a bookshelf in the game.
 * <p>
 * This class handles:
 * - Loading the bookshelf image for display in the UI.
 * - Creating a polygon hitbox for collision detection.
 */
public class Bookshelf {

    // Image for UI
    private static final int DEFAULT_SIZE = 150; // Default width and height of the bookshelf image
    private ImageIcon bookshelf; // The bookshelf image
    private final JLabel label;  // JLabel to display the bookshelf in the UI

    // Hitbox coordinates
    int[] xPoints = new int[4]; // X coordinates of the polygon
    int[] yPoints = new int[4]; // Y coordinates of the polygon

    // Offsets relative to the top-left corner of the bookshelf image
    int[] xOffsets = {52, 165, 135, 17};
    int[] yOffsets = {115, 175, 190, 130};

    Polygon bookshelfBounds; // Polygon representing the hitbox

    /** Constructor to create a bookshelf at position (x, y). */
    public Bookshelf(int x, int y) {
        this.bookshelf = loadIcon("/snackademy/resources/Bookshelves.png");
        this.label = new JLabel(bookshelf);
        this.label.setBounds(x, y, DEFAULT_SIZE, DEFAULT_SIZE); // Set position and size
        this.bookshelfBounds = getPolygonBounds(x, y);          // Create hitbox polygon
    }

    // -------------------------------------------------------------------------
    // Image Loading
    // -------------------------------------------------------------------------

    /** Loads an ImageIcon from a resource path. */
    private ImageIcon loadIcon(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            throw new IllegalStateException("Desk image not found: " + path);
        }
        return new ImageIcon(url);
    }

    /** Returns the JLabel for UI display. */
    public JLabel getLabel() {
        return label;
    }

    /** Returns a scaled version of the bookshelf image. */
    public ImageIcon getScaledIcon(int width, int height) {
        Image img = bookshelf.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // -------------------------------------------------------------------------
    // Hitbox Creation
    // -------------------------------------------------------------------------

    /**
     * Creates the polygon hitbox for the bookshelf based on offsets.
     * 
     * @param imgX X position of the bookshelf
     * @param imgY Y position of the bookshelf
     * @return Polygon representing the bookshelf hitbox
     */
    public Polygon getPolygonBounds(int imgX, int imgY) {
        for (int i = 0; i < xOffsets.length; i++) {
            xPoints[i] = imgX + xOffsets[i];
            yPoints[i] = imgY + yOffsets[i];
        }
        return new Polygon(xPoints, yPoints, xOffsets.length);
    }
}
