package adamsen.dk.Dilemma40.Controller;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.firebase.client.Firebase;

import java.io.Serializable;
import java.util.ArrayList;

import adamsen.dk.Dilemma40.Entity.Datalag;
import adamsen.dk.Dilemma40.Entity.Dilemma;
import adamsen.dk.Dilemma40.Boundary.DilemmaAdapter;

/**
 * Created by FrederikSwag on 14-Jan-16.
 */
public class DatalagController {
    transient ArrayList<Dilemma> dlTmp;
    transient DilemmaAdapter dilemmaAdapter;
    transient Datalag dataLag;
    transient Context ctx;
    transient Firebase myFirebaseRef;


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
        dataLag.afgivStemme(d, stemmeNr);
    }

    public String toString() {
        return "Im alive";
    }
}
