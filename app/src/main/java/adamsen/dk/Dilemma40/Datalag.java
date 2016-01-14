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
public class Datalag {

    ArrayList<Dilemma> Dilemmaer;
    Firebase myFirebaseRef;
    Firebase voteRef;
    Dilemma tmp;
    DatalagController DTC;

    public Datalag(Context ctx, final ArrayList<Dilemma> Dilemmaer, final DatalagController DTC) {
        this.Dilemmaer = Dilemmaer;
        this.DTC = DTC;
        Firebase.setAndroidContext(ctx);
        myFirebaseRef = new Firebase("https://dilemmaapp.firebaseio.com/");


        //Indledende indlæsning af dilemmaer!

        //Indlæsning af nyt oprettet dilemma fra databasen
        myFirebaseRef.addChildEventListener(new ChildEventListener() {
            //Indledende indlæsning af dilemmaer og efterfølgende tilføjelser af nye
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    tmp = dataSnapshot.getValue(Dilemma.class);
                    tmp.setId(dataSnapshot.getKey());

                    Dilemmaer.add(tmp);

                    DTC.opdaterAdapter();



                } catch (Exception e) {

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //s = ID

                tmp = dataSnapshot.getValue(Dilemma.class);
                tmp.setId(dataSnapshot.getKey());


                for(Dilemma d : Dilemmaer) {
                    if(d.getId().equals(tmp.getId())) {
                        for(int i = 0; i < d.getVotes().length; i++) {
                            System.out.println(d.getVotes()[i]);
                            d.setVotes(i, tmp.getVotes()[i]);
                            System.out.println(d.getVotes()[i]);
                        }
                    }
                }


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public  void afgivStemme(Dilemma d, int stemmeNr) {
        voteRef = new Firebase("https://dilemmaapp.firebaseio.com/" + d.getId() + "/votes/" + stemmeNr);
        voteRef.runTransaction(new Transaction.Handler() {

            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                currentData.setValue((Long) currentData.getValue() + 1);
                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                //This method will be called once with the results of the transaction
            }
        });
    }
}
