import java.util.ArrayList;
import java.util.Map;

//Gustaf Holmer
// gustafholmeri@gmail.com

public class FoundationPile extends PileOutline {


    int foundationStartNum = 1;
    String pileColor = "empty";

    public FoundationPile(int inputX, int inputY) {
        super(inputX, inputY);
    }

    @Override
    public boolean checkIfPileEmpty(Card cInput) {
        if (pileColor.equals("empty") && cInput.cardValueNum == 1) {
            return true;
        } else {
            for (Card c : cardPileOutLineCollection) {
                if (c.cardValueNum == cInput.cardValueNum) {
                    return false;
                } else if (pileColor.equals(cInput.cardValueColor) && foundationStartNum + 1 == cInput.cardValueNum) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public void addCardInPileList(Card cInput, Integer xInput, Integer yInput) {
        super.addCardInPileList(cInput, xInput, yInput);
        if (cInput.cardValueNum == 1) {
            pileColor = cInput.cardValueColor;
        } else {
            foundationStartNum++;
        }
    }


    @Override
    public void removeCardFromPileList(Card cInput, Integer xInput, Integer yInput) {
        super.removeCardFromPileList(cInput, xInput, yInput);
        if (cInput.cardValueNum == 1) {
            pileColor = "empty";
        } else {
            foundationStartNum--;
        }

    }










}
