package adamsen.dk.Dilemma40;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Frederik on 11/27/2015.
 */
public class DataProvider {

    public static ArrayList<Dilemma> getInfo() {
        ArrayList<Dilemma> Dilemmaer = new ArrayList<>();

        String[] valg1 = {"The Roon","Jack and Gill","Birdemic"};
        String[] valg2 = {"Drop hende","Slå op med hende","Ignorer hende"};
        String[] valg3 = {"Tag på arbejde","Meld dig syg","Bare lad være med at møde op","Melde mig ind i fremmedlegionen"};
        String[] valg4 = {"Kjole 1","Kjole 2"};

        Dilemmaer.add(new Dilemma("Hvilken film skal jeg se?","Jeg keder mig bravt og vil bare vildt gerne se en god film, dette er mit udvalg.",valg1));
        Dilemmaer.add(new Dilemma("Jeg hader min kæreste","Min kæreste er virkelig nederen lige nu, hvad gør jeg?",valg2));
        Dilemmaer.add(new Dilemma("Gider ikke på arbejde","Jeg skal på arbejde men gider virkelig ikke, jeg hader min chef og er ret sikker på han også hader mig, hvad gør jeg?",valg3));
        Dilemmaer.add(new Dilemma("Hvilken kjole skal jeg have på?","Jeg kan bare virkelig ikke beslutte mig for hvilken kjole jeg gerne vil have på!",valg4));

        return Dilemmaer;
    }
}
