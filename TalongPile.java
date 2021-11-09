import java.util.Map;

//Gustaf Holmer
// gustafholmeri@gmail.com

public class TalongPile extends PileOutline {

    public TalongPile(int inputX, int inputY) {
        super(inputX, inputY);
    }

    @Override
    public void addCardInPileList(Card cInput, Integer xInput, Integer yInput) {
        super.addCardInPileList(cInput, xInput, yInput);
    }

    @Override
    public void removeCardFromPileList(Card cInput, Integer xInput, Integer yInput) {
        super.removeCardFromPileList(cInput, xInput, yInput);

        if (cardPileOutLineCollection.size() == 0 && !isWastePile) {
            isLastCard = true;
        }

        if (cardPileOutLineCollection.size() != 0) {
            cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).cardLocked = true;
            cardPileOutLineCollection.get(cardPileOutLineCollection.size() - 1).cardFromTalong = true;
        }


    }

    @Override
    boolean checkIfPileEmpty(Card cInput) {

        if (isLastCard) {
            return false;

        } else if (cInput.cardFromTalong && !isWastePile && cardPileOutLineCollection.size() == 0) {
            isWastePile = true;
            return true;
        } else if (cInput.cardFromTalong && isWastePile) {
            return true;
        }
        return false;
    }



}
