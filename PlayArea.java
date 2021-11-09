import javax.swing.*;
import javax.xml.transform.Source;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.*;
import java.util.*;

//Gustaf Holmer
// gustafholmeri@gmail.com


//
//  Class which "drives" the program
//
class PlayArea extends JPanel {

    /*
    *
    * Lists which is used to help keep track of the objects
    *
     */
    private ArrayList<FoundationPile> FoundationPileOutLineCollection =
            new ArrayList<>();

    private ArrayList<SourcePile> SourcePileOutLineCollection =
            new ArrayList<>();

    private ArrayList<TalongPile> TalongPileOutLineCollection =
            new ArrayList<>();

    private ArrayList<Card> cardsCollection =
            new ArrayList<>();

    // helps deal the cards
    Map<Integer, Card> cardsD = new HashMap<>();
    Map<Integer, Card> cardsC = new HashMap<>();
    Map<Integer, Card> cardsH = new HashMap<>();
    Map<Integer, Card> cardsS = new HashMap<>();

    int beginCoordsX;
    int beginCoordsY;
    PileOutline previousPile = null;

    int cardCenterX;
    int cardCenterY;

    String typeOfDeckSort;
    private static int xCoordFixCenterOfCard = 35;
    private static int yCoordFixCenterOfCard = 48;

    public PlayArea(String sInput) {
        setBackground(Color.GRAY);
        MouseListener ml = new MouseListener(this); // connects drawarea with MouseListern object
        addMouseListener(ml);
        addMouseMotionListener(ml);

        typeOfDeckSort = sInput; // input from the buttons if fixed or random deck

        addCardOutLines();
        cardDealer();
    }

    // deals the cards
    public void cardDealer() {
        /*
        *
        * Important here to check that the path is set right
        *
         */
        Path dir = Paths.get("src/resources"); // <----- path must be to resources with all the gif filed
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                if (file.getFileName().toString().equals(".DS_Store") || file.getFileName().toString().equals("b1fv.gif")) {
                    continue;
                } else {
                    int cordX = 560; // coords for where the cards which starts in Talong pile
                    int cordY = 100;

                    // creates new playcard and connects it to the file
                    Card c = new PlayCard(cordX, cordY, file.getFileName().toString());

                    if (c.getCardValueColor().equals("d")) {
                        cardsD.put(c.getCardValueNum(), c);
                    } else if (c.getCardValueColor().equals("c")) {
                        cardsC.put(c.getCardValueNum(), c);
                    } else if (c.getCardValueColor().equals("h")) {
                        cardsH.put(c.getCardValueNum(), c);
                    } else if (c.getCardValueColor().equals("s")) {
                        cardsS.put(c.getCardValueNum(), c);
                    }

                }
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }

        Set<Map.Entry<Integer, Card>> st = cardsD.entrySet();
        for (Map.Entry<Integer, Card> me : st) {
            cardsCollection.add(me.getValue());

        }
        Set<Map.Entry<Integer, Card>> st1 = cardsC.entrySet();
        for (Map.Entry<Integer, Card> me : st1) {
            cardsCollection.add(me.getValue());
        }
        Set<Map.Entry<Integer, Card>> st2 = cardsH.entrySet();
        for (Map.Entry<Integer, Card> me : st2) {
            cardsCollection.add(me.getValue());
        }
        Set<Map.Entry<Integer, Card>> st3 = cardsS.entrySet();
        for (Map.Entry<Integer, Card> me : st3) {
            cardsCollection.add(me.getValue());
        }


        PlayType p = null;

        // creates the Playtype class which have all the coords hardcoded
        if (typeOfDeckSort.equals("Fixed")) {
            p = new PlayType(cardsCollection, cardsD, cardsC, cardsH, cardsS);
        } else if (typeOfDeckSort.equals("Random")) {
            Collections.shuffle(cardsCollection);
            p = new PlayType(cardsCollection, cardsD, cardsC, cardsH, cardsS);
        }

        ArrayList<Integer> xCoArray = p.returnOrderX();
        ArrayList<Integer> yCoArray = p.returnOrderY();

        int[] turnedUpBeginCards = {0, 7, 13, 18, 22, 25, 27};

        for (int i = 0; i < 28; i++) {
            for (int el : turnedUpBeginCards) {
                if (el == i) {
                    cardsCollection.get(i).changeSide();
                    cardsCollection.get(i).setCardLocked();
                }
            }
            cardsCollection.get(i).changeBeginCoords(xCoArray.get(i), yCoArray.get(i));
        }

        int[] intArrayX = {660, 560, 460, 360, 260, 160, 60}; // the coords for the sourcepile

        // adds cards to SourcePile
        for (Card c : cardsCollection) {
            for (int i = 0; i < intArrayX.length; i++) {
                if (c.xCoord == intArrayX[i] && c.yCoord == 230) {
                    addCardToPile(checkWhichPile(c), c.xCoord, c.yCoord, c);
                    sourcePileDealer(c);
                }
            }
        }

        // adds cards to TalongPile
        for (Card c : cardsCollection) {
            if (c.xCoord == 560 && c.yCoord == 100) {
                addCardToPile(checkWhichPile(c), c.xCoord, c.yCoord, c);
                c.setCardLocked();
                c.cardFromTalong = true;
            }
        }

    }

    // helps cardDealer add cards to source pilelists
    public void sourcePileDealer (Card cInput) {
        int counter10 = 0;
        int maxSizeOfCounter = Integer.parseInt(String.valueOf(Integer.toString(cInput.xCoord).charAt(0)));

        if (cInput.xCoord != 60) {
            while (counter10 < maxSizeOfCounter) {
                for (Card c : cardsCollection) {
                    if (c.xCoord == cInput.xCoord && c.yCoord != 230) { // is the y-coord for the source piles
                        if (c.yCoord == 250 + (20 * counter10)) {
                            addCardToPile(checkWhichPile(c), c.xCoord, c.yCoord, c);
                            counter10++;
                        }
                    }
                }
            }
        }

    }

    // draws the piles outlines
    public void addCardOutLines() {

        // all magic numbers is here for laying out all the pile outlines

        for (int i = 0; i < 7; i++) {
            if (i == 4) {
                continue;
            } else if (i > 4) {
                TalongPile c = new TalongPile(60 + (i * 100), 100);
                TalongPileOutLineCollection.add(c);
            } else {
                FoundationPile c = new FoundationPile(60 + (i * 100), 100);
                FoundationPileOutLineCollection.add(c);
            }

            for (int i1 = 0; i1 < 7; i1++) {
                SourcePile c = new SourcePile(60 + (i1 * 100), 230);
                SourcePileOutLineCollection.add(c);
            }
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // paints reverse for z-index

        for (int i = FoundationPileOutLineCollection.size() - 1; i >= 0; i--) {
            FoundationPileOutLineCollection.get(i).draw(g);
        }

        for (int i = SourcePileOutLineCollection.size() - 1; i >= 0; i--) {
            SourcePileOutLineCollection.get(i).draw(g);
        }

        for (int i = TalongPileOutLineCollection.size() - 1; i >= 0; i--) {
            TalongPileOutLineCollection.get(i).draw(g);
        }

        for (int i = cardsCollection.size() - 1; i >= 0; i--) {
            cardsCollection.get(i).draw(g);
        }

    }

    // Checks if mousepress in card
    public Card ifClickInCard(int x, int y) {
        for (Card f1 : cardsCollection) {
            if (f1.ifClickInFigure(x, y)) {

                // Transforms the objects z-index
                int index = cardsCollection.indexOf(f1);
                cardsCollection.remove(index);
                cardsCollection.add(0, f1);
                return f1;
            }
        }
        return null;
    }

    // checks if mouse is in card during movement
    public void ifMousePressedDuringMovement(Card cInput, int inputX, int inputY) {
        for (Card f1 : cardsCollection) {
            if (f1 == cInput) {
                f1.changeSide();
                f1.SaveMousePressCoords(inputX, inputY);
            }
        }

        if (checkWhichPile(cInput) != null) {
            previousPile = checkWhichPile(cInput); // saves previous pile
            removeCardFromPile(checkWhichPile(cInput), inputX, inputY, cInput);
        }
    }

    // is connected to a change position method in card class. During mouse present and movement with a card this method is activated
    public void changePositon(Card sInput, int xInput, int yInput) {
        for (Card f1 : cardsCollection) {
            if (f1 == sInput) {
                f1.changePositionFigure(xInput, yInput);
                repaint();
                cardCenterX = xInput;
                cardCenterY = yInput;
            }
        }
    }

    // saved coords which the card returns to
    public void invalidMoveSaveCoords(Card cInput) {
        beginCoordsX = cInput.getLastX();
        beginCoordsY = cInput.getLastY();
    }

    // when mouse is released with card, this method removes the card and creates a new copy of it in its place
    public void releaseCardInPile(Card cInput, PileOutline pInput) {

        ArrayList<Card> toRemove = new ArrayList();
        ArrayList<Card> toAdd = new ArrayList();

        int getX;
        int getY;
        String savedVal;

        for (Card f1 : cardsCollection) {
            if (f1 == cInput) {
                Card s = null;

                toRemove.add(f1);
                savedVal = f1.getCardValueGif();
                getX = f1.getLastX();
                getY = f1.getLastY();


                s = new PlayCard(getX, getY, savedVal);
                toAdd.add(s);

                addCardToPile(pInput, getX, getY, s);

                cardOnTop(pInput, s);

                if (f1.cardFromTalong &&  previousPile.getClass().getName().equals("TalongPile")) {
                    s.cardFromTalong = true;
                } else {
                    s.cardFromTalong = false;
                }

                s.setCardLocked();
                s.changeSide();
                repaint();
            }
        }
        // buffer here to not get error when manipulating lists which loops
        cardsCollection.removeAll(toRemove);
        cardsCollection.addAll(0, toAdd);
    }

    // if release of card is forbidden, the card is returned to its original position.Which means the card is destroyed and a copy is returned to its original positon.
    public void doNotReleaseCardInPile(Card cInput) {
        ArrayList<Card> toRemove = new ArrayList();
        ArrayList<Card> toAdd = new ArrayList();

        String savedVal;
        int getX;
        int getY;

        boolean talongPileCardReturned = false;
        int savedIndexTalongPileCardReturned = 0;

        try {
            for (Card f1 : cardsCollection) {
                if (f1 == cInput) {
                    Card s = null;

                    toRemove.add(f1);
                    savedVal = f1.getCardValueGif();
                    getX = beginCoordsX;
                    getY = beginCoordsY;

                    s = new PlayCard(getX, getY, savedVal);
                    toAdd.add(s);

                    // Cards from Talong Pile needed more attributes when copied
                    if (f1.cardFromTalong && !previousPile.isWastePile && previousPile.getClass().getName().equals("TalongPile")) {
                        s.cardFromTalong = true;
                        s.turnedDown = true;
                        savedIndexTalongPileCardReturned = previousPile.getCardPileOutLineCollection().size();
                        talongPileCardReturned = true;
                    } else if (f1.cardFromTalong) {
                        s.cardFromTalong = true;
                        s.changeSide();
                    } else {
                        s.cardFromTalong = false;
                        s.changeSide();
                    }

                    addCardToPile(previousPile, getX, getY, s);
                    s.setCardLocked();
                    repaint();
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        cardsCollection.removeAll(toRemove);

        // adds card from Talong back to its original z-index place
        if (talongPileCardReturned) {
            cardsCollection.addAll(savedIndexTalongPileCardReturned, toAdd);
        } else {
            cardsCollection.addAll(0, toAdd);
        }

    }

    // checks which pile card is released in
    public PileOutline checkWhichPile(Card f1) {

        for (FoundationPile p : FoundationPileOutLineCollection) {
            if (p.ifClickInFigure(f1.xCoord + xCoordFixCenterOfCard, f1.yCoord + yCoordFixCenterOfCard)) {
                return p;
            }
        }

        for (SourcePile p : SourcePileOutLineCollection) {
            if (p.ifClickInFigure(f1.xCoord + xCoordFixCenterOfCard, f1.yCoord + yCoordFixCenterOfCard)) {
                return p;
            }
        }

        for (TalongPile p : TalongPileOutLineCollection) {
            if (p.ifClickInFigure(f1.xCoord + xCoordFixCenterOfCard, f1.yCoord + yCoordFixCenterOfCard)) {
                return p;
            }
        }

        return null;
    }

    public void addCardToPile(PileOutline pInput, int x, int y, Card cInput) {

        if (pInput.getClass().getName().equals("FoundationPile")) {
            for (FoundationPile f : FoundationPileOutLineCollection) {
                if (pInput == f) {
                    f.addCardInPileList(cInput, x, y);
                }
            }
        }

        if (pInput.getClass().getName().equals("SourcePile")) {
            for (SourcePile s : SourcePileOutLineCollection) {
                if (pInput == s) {
                    s.addCardInPileList(cInput, x, y);
                }
            }
        }

        if (pInput.getClass().getName().equals("TalongPile")) {
            for (TalongPile t : TalongPileOutLineCollection) {
                if (pInput == t) {
                    t.addCardInPileList(cInput, x, y);
                }
            }
        }

    }

    public void removeCardFromPile(PileOutline pInput, int x, int y, Card cInput) {

        if (pInput.getClass().getName().equals("FoundationPile")) {
            for (FoundationPile f : FoundationPileOutLineCollection) {
                if (pInput == f) {
                    f.removeCardFromPileList(cInput, x, y);
                }
            }
        }

        if (pInput.getClass().getName().equals("SourcePile")) {
            for (SourcePile s : SourcePileOutLineCollection) {
                if (pInput == s) {
                    s.removeCardFromPileList(cInput, x, y);
                }
            }
        }

        if (pInput.getClass().getName().equals("TalongPile")) {
            for (TalongPile t : TalongPileOutLineCollection) {
                if (pInput == t) {
                    t.removeCardFromPileList(cInput, x, y);
                }
            }
        }


    }

    // Checks if card on top is gone and the card under is flipped
    public void cardOnTop(PileOutline pInput, Card cInput) {

        for (Card c : cardsCollection) {
            for (Card c1 : pInput.getCardPileOutLineCollection()) {
                if (c.equals(c1)) {
                    if (pInput.getCardPileOutLineCollection().indexOf(c1) < pInput.getCardPileOutLineCollection().size()) {
                        c.cardLocked = true;
                    }
                }
            }
        }

        if (previousPile != null) {
            for (Card c3 : cardsCollection) {
                for (Card c4 : previousPile.getCardPileOutLineCollection()) {
                    if (c3.equals(c4)) {
                        if (previousPile.getCardPileOutLineCollection().indexOf(c4) == previousPile.getCardPileOutLineCollection().size() - 1) {
                            if (pInput.getClass().getName() != "TalongPile") { // make the card not flip the top card in the Talong pile
                                c3.turnedDown = false;
                            }
                            c3.cardLocked = false;
                        }
                    }
                }
            }
        }
    }

    // flips the talong pile
    public void flipPile () {

        ArrayList<Card> toAdd = new ArrayList();

        for (TalongPile t : TalongPileOutLineCollection) {
            if (!t.isLastCard) {
                ArrayList<Card> tempCardsCollection = t.cardPileOutLineCollection;

                Collections.reverse(tempCardsCollection);

                Collections.reverse(t.cardPileOutLineCollection);


               t.xCoordsCollection.subList(1, t.xCoordsCollection.size()).clear();
               t.yCoordsCollection.subList(1, t.yCoordsCollection.size()).clear();


                // magic numbers for the pile coords
                for (Card c : cardsCollection) {
                    for (Card c1 : t.getCardPileOutLineCollection())
                        if (c.equals(c1)) {
                            int tempVar = 560;

                            if (t.xCoord == 660) {
                                tempVar = 660;
                            }

                            c1.xCoord = tempVar;
                            c1.yCoord = 100;

                            t.xCoordsCollection.add(tempVar);
                            t.yCoordsCollection.add(100);

                            toAdd.add(c);
                        }
                }
                t.cardPileOutLineCollection.removeAll(toAdd);

                for (Card c : toAdd) {
                    releaseCardInPile(c, t);
                }

                for (Card c : cardsCollection) {
                    for (Card c1 : t.getCardPileOutLineCollection())
                        if (c.equals(c1)) {
                            c.turnedDown = true;
                            c.cardLocked = false;
                            c.cardFromTalong = true;
                        }
                }

                t.isWastePile = false;

            }
            t.isWastePile = false;
            t.isLastCard = false;
        }
        repaint();
    }

    // pile is ready to be flipped only when all cards have been taken from the Talong Pile
    public boolean ifPileReadyForFlip () {
        for (TalongPile t : TalongPileOutLineCollection) {
            if (t.isLastCard) {
                System.out.println("Pile flipped!");
                return true;
            }
        }
        System.out.println("There is still cards left in the Talong pile!");
        return false;
    }




    // Checks Z-index and hinders piles cards moved on top of other piles
    public Boolean checkPilesZindex(Card cInput, PileOutline pInput) {

        PileOutline tempVar = null;
        PileOutline tempVar2 = null;

        // checks the Foundation piles
        if (pInput.getClass().getName().equals("FoundationPile")) {
            for (FoundationPile p : FoundationPileOutLineCollection) {
                if (p.ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {
                    tempVar = p;
                }
            }

            for (int i = FoundationPileOutLineCollection.size() - 1; i >= 0; i--) {
                if (FoundationPileOutLineCollection.get(i).ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {
                    tempVar2 = FoundationPileOutLineCollection.get(i);
                }
            }

            for (SourcePile p : SourcePileOutLineCollection) {
                if (p.ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {
                    tempVar2 = p;
                }
            }

            for (TalongPile p : TalongPileOutLineCollection) {
                if (p.ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {
                    tempVar2 = p;
                }
            }
        }

        // checks Source piles
        if (pInput.getClass().getName().equals("SourcePile")) {
            if (cInput.cardValueNum == 13 || pInput.cardPileOutLineCollection.size() <= 1) { // King gives errors if this is not true & other check is for starting card
                return true;
            }

            for (SourcePile p : SourcePileOutLineCollection) {
                if (p.ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {
                    tempVar = p;
                }
            }

            for (int i = SourcePileOutLineCollection.size() - 1; i >= 0; i--) {
                if (SourcePileOutLineCollection.get(i).ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {
                    tempVar2 = SourcePileOutLineCollection.get(i);
                }
            }

            for (FoundationPile p : FoundationPileOutLineCollection) {
                if (p.ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {

                    tempVar2 = p;
                }
            }

            for (TalongPile p : TalongPileOutLineCollection) {
                if (p.ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {
                    tempVar2 = p;
                }
            }

        }


        // checks Talong piles
        if (pInput.getClass().getName().equals("TalongPile")) {
            for (TalongPile p : TalongPileOutLineCollection) {
                if (p.ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {
                    tempVar = p;
                }
            }

            for (int i = TalongPileOutLineCollection.size() - 1; i >= 0; i--) {
                if (TalongPileOutLineCollection.get(i).ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {
                    tempVar2 = TalongPileOutLineCollection.get(i);
                }
            }


            for (FoundationPile p : FoundationPileOutLineCollection) {
                if (p.ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {
                    tempVar2 = p;
                }
            }

            for (SourcePile p : SourcePileOutLineCollection) {
                if (p.ifClickInFigure(cInput.xCoord + xCoordFixCenterOfCard, cInput.yCoord + yCoordFixCenterOfCard)) {
                    tempVar2 = p;
                }

            }
        }


        assert tempVar != null;

        if (tempVar != null || tempVar2 != null) {
            return tempVar.equals(tempVar2);
        }

        return false;
    }


}










