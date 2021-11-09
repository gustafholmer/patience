import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//Gustaf Holmer
// gustafholmeri@gmail.com

// This class have all the coords hard coded for the alyout of the cards

public class PlayType {

    ArrayList<Card> cardsCollection;

    Map<Integer, Card> cardsD;
    Map<Integer, Card> cardsC;
    Map<Integer, Card> cardsH;
    Map<Integer, Card> cardsS;

    ArrayList<Integer> finalXcoords = new ArrayList<>();
    ArrayList<Integer> finalYcoords = new ArrayList<>();

    public PlayType (ArrayList<Card> cardsCollectionInput, Map<Integer, Card> cardsDinput,
                     Map<Integer, Card> cardsCinput, Map<Integer, Card> cardsHinput,
                     Map<Integer, Card> cardsSinput ) {

        cardsCollection = cardsCollectionInput;
        cardsD = cardsDinput;
        cardsC = cardsCinput;
        cardsH = cardsHinput;
        cardsS = cardsSinput;
    }

    // column 1
    int[] intArrayX = {60};
    int[] intArrayY = {230};

    // column 2
    int[] intArrayX2 = {160, 160};
    int[] intArrayY2 = {230,250,};

    // column 3
    int[] intArrayX3 = {260, 260, 260};
    int[] intArrayY3 = {230,250,270};

    // column 4
    int[] intArrayX4 = {360, 360, 360, 360};
    int[] intArrayY4 = {230,250,270, 290};

    // column 5
    int[] intArrayX5 = {460,460,460,460,460};
    int[] intArrayY5 = {230,250,270, 290, 310};

    // column 6
    int[] intArrayX6 = {560, 560,560,560,560,560};
    int[] intArrayY6 = {230,250,270, 290, 310, 330};

    // column 7
    int[] intArrayX7 = {660, 660,660,660,660,660,660,};
    int[] intArrayY7 = {230,250,270, 290, 310, 330, 350};





    public ArrayList<Integer> returnOrderX () {

        // column 1
        for (int i = 0; i < intArrayX.length; i++) {
            finalXcoords.add(intArrayX[i]);
            finalYcoords.add(intArrayY[i]);
        }

        // column 2
        for (int i = 0; i < intArrayX2.length; i++) {
            finalXcoords.add(intArrayX2[i]);
            finalYcoords.add(intArrayY2[i]);
        }

        // column 3
        for (int i = 0; i < intArrayX3.length; i++) {
            finalXcoords.add(intArrayX3[i]);
            finalYcoords.add(intArrayY3[i]);
        }

        // column 4
        for (int i = 0; i < intArrayX4.length; i++) {
            finalXcoords.add(intArrayX4[i]);
            finalYcoords.add(intArrayY4[i]);
        }

        // column 5
        for (int i = 0; i < intArrayX5.length; i++) {
            finalXcoords.add(intArrayX5[i]);
            finalYcoords.add(intArrayY5[i]);
        }

        // column 6
        for (int i = 0; i < intArrayX6.length; i++) {
            finalXcoords.add(intArrayX6[i]);
            finalYcoords.add(intArrayY6[i]);
        }

        // column 7
        for (int i = 0; i < intArrayX7.length; i++) {
            finalXcoords.add(intArrayX7[i]);
            finalYcoords.add(intArrayY7[i]);
        }

        Collections.reverse(finalXcoords);

        return finalXcoords;
    }

    public ArrayList<Integer> returnOrderY () {

        Collections.reverse(finalYcoords);

        return finalYcoords;

    }



}
