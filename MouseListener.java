import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

//Gustaf Holmer
// gustafholmeri@gmail.com

public class MouseListener extends MouseAdapter
        implements MouseMotionListener {

    PlayArea playArea;

    Card choosen;

    public MouseListener(PlayArea a) {
        playArea = a;
    }

     // mousePressed important for deciding if click is in shape. Starts the chain which moves all figures
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        choosen = playArea.ifClickInCard(x, y);
        if (choosen != null) {
            playArea.ifMousePressedDuringMovement(choosen, x, y);
            playArea.invalidMoveSaveCoords(choosen);

        }
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (choosen != null) {
            playArea.changePositon(choosen, x, y);
        }
    }

    public void mouseReleased(MouseEvent e) {
        // if release of card is okey
        if (choosen != null && playArea.checkWhichPile(choosen) != null && playArea.checkWhichPile(choosen).checkIfPileEmpty(choosen) &&
                playArea.checkWhichPile(choosen).checkIfReleaseOnTopCard(choosen) && playArea.checkPilesZindex(choosen, playArea.checkWhichPile(choosen)) ) {

            playArea.releaseCardInPile(choosen, playArea.checkWhichPile(choosen));

        } else {
            playArea.doNotReleaseCardInPile(choosen);

        }

    }




}