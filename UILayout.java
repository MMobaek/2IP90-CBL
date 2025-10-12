import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
// import java.util.concurrent.TimeUnit;
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
    private JLabel playerLabel;
    private int playerX = 250;
    private int playerY = 100;
    long startTime = System.currentTimeMillis();
    long endTime;
    Random random = new Random();
    int firstTransitionMilestone = random.nextInt(6000, 16000);
    int secondTransitionMilestone = random.nextInt(1000, 3000);
    int thirdTransitionMilestone = random.nextInt(4000, 8000);
    boolean firstTransitionReached = false;
    boolean secondTransitionReached = false;
    boolean thirdTransitionReached = false;

    /**
     * For the player character.
     */
    
    public void addPlayerIcon() {
        // Icon of the player
        ImageIcon playerIcon = new ImageIcon("cardboard-box-clipart-lg.png");
        playerLabel = new JLabel(playerIcon, JLabel.CENTER);
        playerLabel.setBounds(playerX, playerY, 100, 100); // Adjust x, y, width, height as needed
        this.add(playerLabel);
    }


    /**
     * Does the librarian logic to see when it shall transition.
     * 
     * 1. Unattentive to Transition
     * 2. Transition to Attentive
     * 3. Attentive to Unattentive
     */
    public void librarianStatus() {
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        if (endTime - startTime >= firstTransitionMilestone && !firstTransitionReached) {
            startTime = System.currentTimeMillis();
            firstTransitionReached = true;
            transition1();
        } else if (endTime - startTime >= secondTransitionMilestone 
            && firstTransitionReached && !secondTransitionReached) {
            startTime = System.currentTimeMillis();
            secondTransitionReached = true;
            transition2();
        } else if (endTime - startTime >= thirdTransitionMilestone && secondTransitionReached) {
            startTime = System.currentTimeMillis();
            firstTransitionReached = false;
            secondTransitionReached = false;
            transition3();
        }
    }

    /**
     * The transition phase of the librarian.
     */
    public void transition1() {
        for (int i = 0; i < 20; i++) {
            System.out.println("changing states: to Transition phase");
        }
    }

    /**
     * The attentive phase of the librarian.
     */
    public void transition2() {
        for (int i = 0; i < 20; i++) {
            System.out.println("changing states: to Attentive phase");
        }
    }

    /**
     * The inattentive phase of the librarian.
     */
    public void transition3() {
        for (int i = 0; i < 20; i++) {
            System.out.println("changing states: to Inattentive phase");
        }
    }

    /**
     * implements the UI.
     */
    UILayout() {
        this.setLayout(null);

        //Player Icon
        addPlayerIcon();

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

        while (true) { // Main gameloop
            librarianStatus();
        }
    }

    @Override //Idk what override really does, but it does something
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == leftArrow) {
            System.out.println("left");
            if (playerX > 0) {
                playerX -= 10;
            }
        } 
        if (e.getSource() == rightArrow) {
            System.out.println("right");
            if (playerX < 500) {
                playerX += 10;
                // Only want to check when moving snacks back
            }
        }
        playerLabel.setBounds(playerX, playerY, 100, 100); // Updates the position of the icon
    }
}
