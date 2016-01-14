package adamsen.dk.Dilemma40;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    ArrayList <Dilemma> Dilemmaer;

    ExpandableListView Exp_list;
    DilemmaAdapter adapter;
    FloatingActionButton create;
    DatalagController DTC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dilemmaer = new ArrayList<>();

        adapter = new DilemmaAdapter(this, Dilemmaer,DTC);
        DTC = new DatalagController(this,Dilemmaer,adapter);

        Exp_list = (ExpandableListView) findViewById(R.id.Listen);
        Exp_list.setAdapter(adapter);
        create = (FloatingActionButton) findViewById(R.id.button2);
        create.setOnClickListener(this);

        DatalagController DTC = new DatalagController(this,Dilemmaer,adapter);

    }

    @Override
    public void onClick(View v) {
        if(v==create){
            createClick();
        }
    }

    private void createClick() {
        //create.setText("hello");
        Intent i = new Intent("adamsen.dk.Dilemma40.Create");
        i.putExtra("DTC",DTC);

        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Snackbar.make(findViewById(android.R.id.content), "Dilemma oprettet", Snackbar.LENGTH_LONG)
                        .show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Not implemented
            }
        }
    }
}
