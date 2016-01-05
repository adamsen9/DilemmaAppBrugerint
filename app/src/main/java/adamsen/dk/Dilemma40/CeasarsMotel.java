package adamsen.dk.Dilemma40;

import android.content.Context;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Frederik on 1/4/2016.
 */
public class CeasarsMotel {

    ArrayList<Dilemma> Dilemmaer;
    Firebase myFirebaseRef;

    public CeasarsMotel(Context ctx, ArrayList Dilemmaer) {
        this.Dilemmaer = Dilemmaer;

        Firebase.setAndroidContext(ctx);
        myFirebaseRef = new Firebase("https://dilemmaapp.firebaseio.com/");

    myFirebaseRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            System.out.println(dataSnapshot.getValue());
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {
            System.out.println("The read failed: " + firebaseError.getMessage());
        }
    });

    }


    //Nyt dilemma
    public void newDilemmaDatabase(Dilemma d) {
        myFirebaseRef.push().setValue(d);
    }

    //Opdatering af stemmer
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
