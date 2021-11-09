import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Gustaf Holmer
// gustafholmeri@gmail.com

abstract public class PileOutline extends Card {

    ArrayList<Card> cardPileOutLineCollection =
            new ArrayList<>();

    ArrayList<Integer> xCoordsCollection =
            new ArrayList<>();

    ArrayList<Integer> yCoordsCollection =
            new ArrayList<>();

    boolean isWastePile = false;
    boolean isLastCard = false;

    public PileOutline(int inputX, int inputY) {
        super(inputX, inputY);
        xCoordsCollection.add(inputX);
        yCoordsCollection.add(inputY);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawRect(xCoord, yCoord, 70, 95); // x & y - 1px to fit card
    }

    @Override
    // If pressed mouse is in PileOutline, return true
    public boolean ifClickInFigure(int x1, int y1) {

        ArrayList<Boolean> boolBuffer =
                new ArrayList<>();

        for (int i = 0; i < xCoordsCollection.size(); i++) {
            if ((x1 >= xCoordsCollection.get(i)) && (y1 >= yCoordsCollection.get(i)) &&
                    (x1 < ((xCoordsCollection.get(i)) + (71))) &&
                    (y1 < ((yCoordsCollection.get(i)) + (96)))) {
                boolBuffer.add(true);
            } else {
                boolBuffer.add(false);
            }
        }
        return boolBuffer.contains(true);
    }


    abstract boolean checkIfPileEmpty(Card cInput);

    public void addCardInPileList (Card cInput, Integer xInput, Integer yInput ) {
        cardPileOutLineCollection.add(cInput);

        xCoordsCollection.add(xInput);
        yCoordsCollection.add(yInput);

    }

    public void removeCardFromPileList (Card cInput, Integer xInput, Integer yInput) {
        cardPileOutLineCollection.remove(cInput);

        xCoordsCollection.remove(xCoordsCollection.size()-1);
        yCoordsCollection.remove(yCoordsCollection.size()-1);

        if (cardPileOutLineCollection.size() != 0) {
            cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).cardLocked = false;
        }

    }

    public ArrayList<Card> getCardPileOutLineCollection() {
        return cardPileOutLineCollection;
    }


    // checks if card is released on the top card
    public boolean checkIfReleaseOnTopCard (Card cInput) {
        if (cardPileOutLineCollection.size() == 0) {
            return true;
        } else {
            cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).cardLocked = false; // lets the top card be moved around
            return cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).ifClickInFigure(cInput.xCoord + 35, cInput.yCoord + 48);
        }
    }




}
