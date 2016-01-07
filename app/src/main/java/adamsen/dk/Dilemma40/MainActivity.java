package adamsen.dk.Dilemma40;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    ArrayList <Dilemma> Dilemmaer;

    ExpandableListView Exp_list;
    DilemmaAdapter adapter;
    Button create;
    Datalag database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Dilemmaer = new ArrayList<>();
        //Splash screen start


        //Spash screen slut



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Exp_list = (ExpandableListView) findViewById(R.id.Listen);



        adapter = new DilemmaAdapter(this, Dilemmaer);
        Exp_list.setAdapter(adapter);

        database = new Datalag(this, Dilemmaer, adapter);

        create = (Button) findViewById(R.id.button2);
        create.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==create){
            createClick();
        }
    }

    private void createClick() {
        //create.setText("hello");
        new Intent("adamsen.dk.Dilemma40.Create");

        startActivity(new Intent("adamsen.dk.Dilemma40.Create"));
    }

}
