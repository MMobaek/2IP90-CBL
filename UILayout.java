import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


/**
 * The looks and mechanics for snackademy.
 */
public class UILayout extends JFrame implements ActionListener {
    // Arrow Buttons
    JButton leftArrow;
    JButton rightArrow;
    // Label of the player
    private JLabel playerLabel;
    private int playerX = 250;
    private int playerY = 100;
    // Librarian icon and label, which changes depending on phase
    ImageIcon librarianIcon;
    private JLabel librarianLabel;
    // Time (for determining when the next phase is)
    long startTime = System.currentTimeMillis();
    long endTime;
    long elapsed;
    // Determining how much time has to be waited
    Random random = new Random();
    int firstTransitionMilestone = random.nextInt(6000, 16000);
    int secondTransitionMilestone = random.nextInt(500, 2000);
    int thirdTransitionMilestone = random.nextInt(4000, 8000);
    // Determining which phase it is
    boolean firstTransitionReached = false;
    boolean secondTransitionReached = false;
    boolean thirdTransitionReached = false;
    // importing the librarian icons
    ImageIcon inattentiveIconRaw = new ImageIcon("librarianInattentive.png");
    ImageIcon transitionIconRaw = new ImageIcon("librarianTransition.png");
    ImageIcon attentiveIconRaw = new ImageIcon("librarianAttentive.png");
    // scaling the librarian image icon
    int librarianX = 150;
    int librarianY = 150;
    Image inattentiveImg = inattentiveIconRaw.getImage()
        .getScaledInstance(librarianX, librarianY, Image.SCALE_SMOOTH);
    Image transitionImg = transitionIconRaw.getImage()
        .getScaledInstance(librarianX, librarianY, Image.SCALE_SMOOTH);
    Image attentiveImg = attentiveIconRaw.getImage()
        .getScaledInstance(librarianX, librarianY, Image.SCALE_SMOOTH);
    ImageIcon inattentiveIcon = new ImageIcon(inattentiveImg);
    ImageIcon transitionIcon = new ImageIcon(transitionImg);
    ImageIcon attentiveIcon = new ImageIcon(attentiveImg);

    /**
     * For the player character.
     */
    
    public void addPlayerIcon() {
        // Icon of the player
        ImageIcon playerIcon = new ImageIcon("cardboard-box-clipart-lg.png");
        playerLabel = new JLabel(playerIcon, JLabel.CENTER);
        playerLabel.setBounds(playerX, playerY, 100, 100);
        this.add(playerLabel);
    }

    /**
     * Adds the initial librarian.
     */
    public void addLibrarianIcon() {
        // Icon of the librarian
        librarianLabel = new JLabel(inattentiveIcon, JLabel.CENTER);
        librarianLabel.setBounds(0, 225, librarianX, librarianY);
        this.add(librarianLabel);
    }

    /**
     * The transition phase of the librarian.
     */
    public void transition1() {
        librarianLabel.setIcon(transitionIcon);
    }

    /**
     * The attentive phase of the librarian.
     */
    public void transition2() {
        librarianLabel.setIcon(attentiveIcon);
    }

    /**
     * The inattentive phase of the librarian.
     */
    public void transition3() {
        librarianLabel.setIcon(inattentiveIcon);
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
        elapsed = endTime - startTime;

        if (elapsed >= firstTransitionMilestone && !firstTransitionReached) {
            startTime = System.currentTimeMillis();
            firstTransitionReached = true;
            transition1();
            return;
        }

        if (elapsed >= secondTransitionMilestone 
            && firstTransitionReached && !secondTransitionReached) {
            startTime = System.currentTimeMillis();
            secondTransitionReached = true;
            transition2();
            return;
        } 

        if (elapsed >= thirdTransitionMilestone && secondTransitionReached) {
            startTime = System.currentTimeMillis();
            firstTransitionReached = false;
            secondTransitionReached = false;
            firstTransitionMilestone = random.nextInt(6000, 16000);
            secondTransitionMilestone = random.nextInt(500, 2000);
            thirdTransitionMilestone = random.nextInt(4000, 8000);
            transition3();
        }
    }



    /**
     * implements the UI.
     */
    UILayout() {
        this.setLayout(null);

        //Player Icon
        addPlayerIcon();
        addLibrarianIcon();

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
