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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Test");
        //Splash screen start


        //Spash screen slut
        CeasarsMotel database = new CeasarsMotel(this, Dilemmaer);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Exp_list = (ExpandableListView) findViewById(R.id.Listen);
        Dilemmaer = DataProvider.getInfo();


        //Eksempel på stemme afgivet



        ArrayList<Dilemma> dilemmaer = new ArrayList<Dilemma>();
        adapter = new DilemmaAdapter(this, Dilemmaer);
        Exp_list.setAdapter(adapter);
    }

}
