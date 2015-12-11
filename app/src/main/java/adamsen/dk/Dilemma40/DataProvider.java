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

        String[] valg1 = {"1","2","3"};
        String[] valg2 = {"Drop hende","Slå op med hende","Ignorer hende"};
        String[] valg3 = {"Hej","Med","Dig","so"};
        String[] valg4 = {"Hej","Med"};

        Dilemmaer.add(new Dilemma("Hvilken film skal jeg se?","0",valg1));
        Dilemmaer.add(new Dilemma("Jeg hader min kæreste","Min kæreste er virkelig nederen lige nu, hvad gør jeg?",valg2));
        Dilemmaer.add(new Dilemma("Gider ikke på arbejde","Robert",valg3));
        Dilemmaer.add(new Dilemma("Min mor er en so","Swag",valg4));

        return Dilemmaer;
    }
}
