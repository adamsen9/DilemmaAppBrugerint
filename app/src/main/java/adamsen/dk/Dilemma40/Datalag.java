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
    DilemmaAdapter adapter;
    Dilemma tmp;

    public Datalag(Context ctx, final ArrayList<Dilemma> Dilemmaer, final DilemmaAdapter adapter) {
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
                    tmp = dataSnapshot.getValue(Dilemma.class);
                    tmp.setId(dataSnapshot.getKey());

                    Dilemmaer.add(tmp);

                    adapter.notifyDataSetChanged();



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
}
