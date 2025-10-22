package snackademy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JPanel;


/** Creates an overlay over where the hitboxes are supposed to be. */
public class DebugOverlayPanel extends JPanel {

    private Player player;
    private final List<Bookshelf> bookshelves;

    public DebugOverlayPanel(Player player, List<Bookshelf> bookshelves) {
        this.player = player;
        this.bookshelves = bookshelves;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.RED);
        g2d.draw(player.getRectangleBounds());

        g2d.setColor(Color.BLUE);
        for (Bookshelf shelf : bookshelves) {
            Polygon bounds = shelf.getPolygonBounds(
                shelf.getLabel().getX(), shelf.getLabel().getY());
            g2d.drawPolygon(bounds);
        }
    }
    
}