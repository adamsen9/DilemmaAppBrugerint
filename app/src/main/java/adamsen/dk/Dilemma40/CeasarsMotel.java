package adamsen.dk.Dilemma40;

import android.content.Context;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import java.util.ArrayList;

/**
 * Created by Frederik on 1/4/2016.
 */
public class CeasarsMotel {

    ArrayList<Dilemma> Dilemmaer;
    Firebase myFirebaseRef;
    DilemmaAdapter adapter;

    public CeasarsMotel(Context ctx, final ArrayList Dilemmaer, final DilemmaAdapter adapter) {
        this.Dilemmaer = Dilemmaer;
        this.adapter = adapter;

        Firebase.setAndroidContext(ctx);
        myFirebaseRef = new Firebase("https://dilemmaapp.firebaseio.com/");


        //Indledende indlæsning af dilemmaer!

        //Indlæsning af nyt oprettet dilemma fra databasen
        myFirebaseRef.addChildEventListener(new ChildEventListener() {
            //Indledende indlæsning af dilemmaer og efterfølgende tilføjelser af nye
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    Dilemmaer.add(dataSnapshot.getValue(Dilemma.class));
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //Todo: implementer at et dilemma bliver fjerne, for testforsøg
                try {
                    Dilemmaer.remove(dataSnapshot.getValue(Dilemma.class));
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {

                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


    //Oprettelse af nyt dilemma
    public void newDilemmaDatabase(Dilemma d) {
        myFirebaseRef.setValue(d);
        d.setId(myFirebaseRef.getKey());
        //Push data skal bruges til at sætte ID for dilemma
    }

    // Todo: Implementer opdatering af stemmer
    public void opdaterStemmer(Dilemma d) {

        Firebase stemme = new Firebase("https://dilemmaapp.firebaseio.com/" + d.getId());

        stemme.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue() == null)  {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }

                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                System.out.println("Ding");
                //This method will be called once with the results of the transaction
            }

        });
    }
}
