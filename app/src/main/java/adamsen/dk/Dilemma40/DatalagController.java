package adamsen.dk.Dilemma40;

import android.content.Context;

import com.firebase.client.Firebase;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by FrederikSwag on 14-Jan-16.
 */
public class DatalagController implements Serializable {
    Dilemma dTmp;
    ArrayList<Dilemma> dlTmp;
    DilemmaAdapter dilemmaAdapter;
    Datalag dataLag;
    Context ctx;
    Firebase myFirebaseRef;


    public DatalagController(Context ctx,ArrayList<Dilemma> Dilemmaer,DilemmaAdapter dilemmaAdapter) {
        myFirebaseRef = new Firebase("https://dilemmaapp.firebaseio.com/");

        this.dilemmaAdapter = dilemmaAdapter;
        this.ctx = ctx;

        dlTmp = Dilemmaer;
        dataLag = new Datalag(ctx,Dilemmaer,this);
    }

    public void nytDilemmaIDatabase(Dilemma d) {
        dataLag.nytDilemmaIDatabase(d);
    }

    public void opdaterAdapter() {
        dilemmaAdapter.notifyDataSetChanged();
    }

    public void afgivStemme(Dilemma d, int stemmeNr) {
        dataLag.afgivStemme(d,stemmeNr);
    }

}
