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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Frederik on 1/4/2016.
 */
public class CeasarsMotel {

    ArrayList<Dilemma> Dilemmaer;
    Firebase myFirebaseRef;
    Dilemma tempD;

    public CeasarsMotel(Context ctx, final ArrayList Dilemmaer) {
        this.Dilemmaer = Dilemmaer;

        Firebase.setAndroidContext(ctx);
        myFirebaseRef = new Firebase("https://dilemmaapp.firebaseio.com/");


        //Indledende indlæsning af dilemmaer!
        myFirebaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map<String, Dilemma> newList = (Map<String, Dilemma>) dataSnapshot.getValue();
                Iterator it = newList.entrySet().iterator();

                while(it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();

                    HashMap<String,Object> temp =  (HashMap<String,Object>) pair.getValue();

                    String titel = (String) temp.get("titel");
                    System.out.println(titel);
                    String desc = (String) temp.get("desc");
                    System.out.println(desc);

                    ArrayList<String> optionsList = (ArrayList<String>) temp.get("options");
                    String[] optionsArray = new String[optionsList.size()];
                    optionsArray = optionsList.toArray(optionsArray);



                    tempD = new Dilemma(titel,desc,optionsArray);
                    tempD.setId((String) pair.getKey());

                    Dilemmaer.add(tempD);

                }

                System.out.println(Dilemmaer.size());



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

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
