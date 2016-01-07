package adamsen.dk.Dilemma40;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    ArrayList <Dilemma> Dilemmaer;

    ExpandableListView Exp_list;
    DilemmaAdapter adapter;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Splash screen start


        //Spash screen slut



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Exp_list = (ExpandableListView) findViewById(R.id.Listen);
        Dilemmaer = DataProvider.getInfo();


        ArrayList<Dilemma> dilemmaer = new ArrayList<Dilemma>();
        adapter = new DilemmaAdapter(this, Dilemmaer);
        Exp_list.setAdapter(adapter);
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
        startActivity(new Intent("adamsen.dk.Dilemma40.Create"));
    }
}
