import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Gustaf Holmer
* gustafholmeri@gmail.com
 *
 *
 * Main class
 *
 *           OBSERVE!!!
 * Activates the playarea where the the cards will be displayed
 *
 *
 */
public class Solitaire extends JFrame {
    public Solitaire () {

        super("A game of Solitaire ");


        JPanel s = new PlayArea("Fixed");

        ButtonClickListener bcl = new ButtonClickListener(this);

        JButton button1 = new JButton("Fixed deck");
        button1.addActionListener(bcl);


        JButton button2 = new JButton("Random deck");
        button2.addActionListener(bcl);


        JButton button3 = new JButton("Flip waste pile");
        button3.addActionListener(bcl);


        JPanel north = new JPanel();
        north.add(button1);
        north.add(button2);
        north.add(button3);
        add(north, BorderLayout.NORTH);

        add(s);

        setVisible(true);
        setSize(800,700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // starts a new game when button is pressed
    public void newGame(String sInput) {
        for (Component c : this.getRootPane().getComponents()) {
            if (c.getName().equals("null.layeredPane")) {
                for (Component sc : ((JLayeredPane) c).getComponents()) {
                    for (Component sc1 : ((JPanel) sc).getComponents()) {
                        if (sc1 instanceof PlayArea) {
                            remove(sc1);
                        }
                    }
                }
            }
        }
        JPanel s = new PlayArea(sInput);
        add(s);

        SwingUtilities.updateComponentTreeUI(this);
    }

    // is called when flip button is pressed
    public void flipPileMain() {
        for (Component c : this.getRootPane().getComponents()) {
            if (c.getName().equals("null.layeredPane")) {
                for (Component sc : ((JLayeredPane) c).getComponents()) {
                    for (Component sc1 : ((JPanel) sc).getComponents()) {
                        if (sc1 instanceof PlayArea) {
                            if (((PlayArea) sc1).ifPileReadyForFlip()) {
                                ((PlayArea) sc1).flipPile();
                            }
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] arg) {
        new Solitaire();
    }
}

