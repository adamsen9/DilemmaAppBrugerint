package adamsen.dk.Dilemma40;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.logging.Handler;

public class MainActivity extends Activity implements View.OnClickListener {
    ArrayList <Dilemma> Dilemmaer;

    ExpandableListView Exp_list;
    DilemmaAdapter adapter;
    Button create;
    Datalag database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dilemmaer = new ArrayList<>();
        adapter = new DilemmaAdapter(this, Dilemmaer);
        database = new Datalag(this, Dilemmaer, adapter);
        Exp_list = (ExpandableListView) findViewById(R.id.Listen);
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
