import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

//Gustaf Holmer
// gustafholmeri@gmail.com

public class SourcePile extends PileOutline {

    String currentColor;
    int currentNum;

    Card lastCard;

    public SourcePile(int inputX, int inputY) {
        super(inputX, inputY);

    }


    @Override
    public void addCardInPileList(Card cInput, Integer xInput, Integer yInput) {
        super.addCardInPileList(cInput, xInput, yInput);

        currentColor = cInput.cardValueColor;
        currentNum = cInput.cardValueNum;
    }

    @Override
    public void removeCardFromPileList(Card cInput, Integer xInput, Integer yInput) {
        super.removeCardFromPileList(cInput, xInput, yInput);

        if (cardPileOutLineCollection.size() == 0) {
            lastCard = cInput;
        }

        if (cardPileOutLineCollection.size() != 0) {
            cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).cardLocked = true;
        }

    }

    @Override
    public boolean checkIfPileEmpty(Card cInput) {
        String tempVarColor;
        int tempVarNum;

        if (cardPileOutLineCollection.size() != 0 && cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).turnedDown) {
            return true;
        }


        if (cardPileOutLineCollection.size() == 0 && cInput.getCardValueNum() == 13 || cInput == lastCard) {
            return true;
        } else if (cardPileOutLineCollection.size() == 0){
            return false;
        }

        tempVarColor = cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).cardValueColor;
        tempVarNum = cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).cardValueNum;



        final Set<String> validSuits1 = Set.of(
                "c", "s"
        );

        final Set<String> validSuits2 = Set.of(
                "d", "h"
        );

        if (validSuits1.contains(tempVarColor) && tempVarNum > cInput.cardValueNum) {
            return  !validSuits1.contains(cInput.cardValueColor);
        }

        if (validSuits2.contains(tempVarColor) && tempVarNum > cInput.cardValueNum) {
            return  !validSuits2.contains(cInput.cardValueColor);
        }

        return false;

    }

    @Override
    public boolean checkIfReleaseOnTopCard(Card cInput) {
        if (cardPileOutLineCollection.size() != 0 && cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).turnedDown) {
            cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).cardLocked = false;
            if (cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).ifClickInFigure(cInput.xCoord + 35, cInput.yCoord + 48)) {
                return true;
            }
        }



        return super.checkIfReleaseOnTopCard(cInput);
    }
}
