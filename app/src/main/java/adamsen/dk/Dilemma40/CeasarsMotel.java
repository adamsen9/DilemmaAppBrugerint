package adamsen.dk.Dilemma40;

import android.content.Context;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Frederik on 1/4/2016.
 */
public class CeasarsMotel {

    ArrayList<Dilemma> Dilemmaer;

    public CeasarsMotel(Context ctx, ArrayList Dilemmaer) {
        this.Dilemmaer = Dilemmaer;

        Firebase.setAndroidContext(ctx);
        Firebase myFirebaseRef = new Firebase("https://dilemmaapp.firebaseio.com");

        //Tilføjelse af nye dilemmaer!
        myFirebaseRef.child("Dilemmaer").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.print("Ding");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
