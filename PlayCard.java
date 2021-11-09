import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//Gustaf Holmer
// gustafholmeri@gmail.com

public class PlayCard extends Card {


    ImageIcon anImage;

    public PlayCard(int inputX, int inputY, String s) {
        super(inputX, inputY);

        gifName = s;
        String tempVar = s.substring(0, s.length() - 4);

        cardValueColor = tempVar.substring(0, 1);
        cardValueNum = Integer.parseInt(tempVar.substring(1));

    }


    @Override
    public void draw(Graphics g) {

        if (turnedDown) {
            anImage = new ImageIcon(PlayCard.class.getResource("resources/" + "b1fv.gif"));
        } else {
            anImage = new ImageIcon(PlayCard.class.getResource("resources/" + gifName));
        }

        g.drawImage(anImage.getImage(), xCoord, yCoord, null);

    }

    @Override
    // If pressed mouse is in PileOutline, return true
    public boolean ifClickInFigure(int x1, int y1) { // 71 and 98 is the size of the card
        return ((x1 >= xCoord) && (y1 >= yCoord) && (x1 < xCoord + 71) && (y1 < yCoord + 96) && !cardLocked); // important here is cardLocked.
    }


}
