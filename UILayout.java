import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * The looks of snackademy.
 */



public class UILayout extends JFrame implements ActionListener {

    JButton leftArrow;
    JButton rightArrow;

    /**
     * implements the UI.
     */
    UILayout() {

        this.setLayout(null);


        // Icon of the player
        ImageIcon playerIcon = new ImageIcon("cardboard-box-clipart-lg.png");
        JLabel playerLabel = new JLabel(playerIcon, JLabel.CENTER);
        playerLabel.setBounds(250, 100, 100, 100); // Adjust x, y, width, height as needed
        this.add(playerLabel);
        
        // Buttons. Can potentially also run the ActionListener when doing keyboard input aswell
        leftArrow = new JButton();
        leftArrow.setBounds(430, 300, 50, 35);
        leftArrow.addActionListener(this);
        leftArrow.setText("<-");
        leftArrow.setFocusable(false);

        rightArrow = new JButton();
        rightArrow.setBounds(500, 300, 50, 35);
        rightArrow.addActionListener(this);
        rightArrow.setText("->");
        rightArrow.setFocusable(false);

        this.setTitle("Snackademy");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.add(leftArrow);
        this.add(rightArrow);
    }

    @Override //Idk what override really does, but it does something
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == leftArrow) {
            System.out.println("left");
        } 
        if (e.getSource() == rightArrow) {
            System.out.println("right");
        }
    }
}
