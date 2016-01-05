package adamsen.dk.Dilemma40;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity{
    ArrayList <Dilemma> Dilemmaer;

    ExpandableListView Exp_list;
    DilemmaAdapter adapter;
    Boolean initalload = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Dilemmaer = new ArrayList<>();


        //Splash screen start


        //Spash screen slut
        CeasarsMotel database = new CeasarsMotel(this, Dilemmaer,initalload);


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Exp_list = (ExpandableListView) findViewById(R.id.Listen);

        //Eksempel på stemme afgivet



        ArrayList<Dilemma> dilemmaer = new ArrayList<Dilemma>();
        adapter = new DilemmaAdapter(this, Dilemmaer);
        Exp_list.setAdapter(adapter);
    }

}
