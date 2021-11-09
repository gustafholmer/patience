import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//Gustaf Holmer
// gustafholmeri@gmail.com

public class ButtonClickListener implements ActionListener {


    Solitaire solitaire;

    public ButtonClickListener(Solitaire p) {
        solitaire = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Fixed deck":
                solitaire.newGame("Fixed");
                break;
            case "Random deck":
                solitaire.newGame("Random");
                break;
            case "Flip waste pile":
                solitaire.flipPileMain();
                break;
        }

    }

}
