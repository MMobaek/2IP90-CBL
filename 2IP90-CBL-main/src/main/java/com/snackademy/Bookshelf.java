package snackademy;

import java.awt.Image;
import java.awt.Polygon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Will load the necessary stuff for the bookshelf, and will create the hitbox.
 */
public class Bookshelf {

    // Image for UI
    private static final int DEFAULT_SIZE = 150;
    private ImageIcon bookshelf;
    private final JLabel label;

    // Hitboxes
    int[] xPoints = new int[4];
    int[] yPoints = new int[4];
    int[] xOffsets = {52, 165, 135, 17};
    int[] yOffsets = {115, 175, 190, 130};

    Polygon bookshelfBounds;
    
    /** Constructor. */
    public Bookshelf(int x, int y) {
        this.bookshelf = loadIcon("/snackademy/resources/Bookshelves.png");
        this.label = new JLabel(bookshelf);
        this.label.setBounds(x, y, DEFAULT_SIZE, DEFAULT_SIZE);
        this.bookshelfBounds = getPolygonBounds(x, y);
    }


    // Loading The bookshelf icon, and making it work with the UI


    /** Loads an ImageIcon from the given resource path. */
    private ImageIcon loadIcon(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            throw new IllegalStateException("Desk image not found: " + path);
        }
        return new ImageIcon(url);
    }

    /** Returns label. */
    public JLabel getLabel() {
        return label;
    }

    /** Using this thingy again. */
    public ImageIcon getScaledIcon(int width, int height) {
        Image img = bookshelf.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }


    // Creating the hitbox

    /** 
     * Creating the bounds of the thingy.
     * @param imgX the x position of the hitbox
     * @param imgY the y position of the hitbox
     * @return bounds
     */
    public Polygon getPolygonBounds(int imgX, int imgY) {
        for (int i = 0; i < xOffsets.length; i++) {
            xPoints[i] = imgX + xOffsets[i];
            yPoints[i] = imgY + yOffsets[i];
        }
        return new Polygon(xPoints, yPoints, xOffsets.length);
    }


}
