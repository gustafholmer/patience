import javax.swing.*;
import java.awt.*;

//Gustaf Holmer
// gustafholmeri@gmail.com

abstract public class Card {
    int xCoord;
    int yCoord;
    int latestRelX = 0;
    int latestRelY = 0;
    int mouseBeganMomentX = 0;
    int mouseBeganMomentY = 0;

    String gifName;
    int cardValueNum;
    String cardValueColor;
    boolean turnedDown = true;
    boolean cardLocked = true;
    boolean cardFromTalong = false;


    // this constructor exists in all subclass of Card
    public Card(int inputX, int inputY) {
        xCoord = inputX;
        yCoord = inputY;

        if (latestRelX == 0) {
            latestRelX = inputX;
            latestRelY = inputY;
        }

    }

    public void changeBeginCoords (int x, int y) {
        xCoord = x;
        yCoord = y;
        latestRelX = x;
        latestRelY = y;
    }


    public void changeSide () {
        turnedDown = false;
    }

    public void setCardLocked () {
        cardLocked = false;
    }


    // is used to return last location of object to be used in the copying phase in DrawArea
    public int getLastX() {
        return xCoord;
    }
    public int getLastY() {
        return yCoord;
    }
    public String getCardValueGif() {
        return gifName;
    }
    public String getCardValueColor() {
        return cardValueColor;
    }
    public int getCardValueNum() {
        return cardValueNum;
    }


    // fills the shape. Is different in all subclassed figures
    abstract public void draw(Graphics g);

    // saves the mouse press for calculating the change of objects position
    public void SaveMousePressCoords (int inputX, int inputY) {
        mouseBeganMomentX = inputX;
        mouseBeganMomentY = inputY;
    }

    // is different in all the subclassed figures as the shape area is different
    abstract public boolean ifClickInFigure(int x1, int y);

    // Changes the position of figure. Calculates the relative coordinates
    public void changePositionFigure ( int intChangeX, int intChangeY) {
        xCoord = intChangeX - (mouseBeganMomentX - latestRelX);
        yCoord = intChangeY - (mouseBeganMomentY - latestRelY);
    }



}
