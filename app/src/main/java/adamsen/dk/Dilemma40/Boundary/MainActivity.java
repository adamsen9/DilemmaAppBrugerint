package adamsen.dk.Dilemma40.Boundary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import adamsen.dk.Dilemma40.Boundary.DilemmaAdapter;
import adamsen.dk.Dilemma40.Controller.DatalagController;
import adamsen.dk.Dilemma40.Create;
import adamsen.dk.Dilemma40.Entity.Datalag;
import adamsen.dk.Dilemma40.Entity.Dilemma;
import adamsen.dk.Dilemma40.R;

public class MainActivity extends Activity implements View.OnClickListener, Serializable {
    ArrayList <Dilemma> Dilemmaer;

    ExpandableListView Exp_list;
    DilemmaAdapter adapter;
    FloatingActionButton create;
    DatalagController DTC;
    Datalag database;
    String FILENAME = "voted_dilemmas";
    FileOutputStream fos;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaG40";
    NetworkInfo nInfo;
    ConnectivityManager cManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        nInfo = cManager.getActiveNetworkInfo();

        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Dilemmaer = new ArrayList<>();


        adapter = new DilemmaAdapter(this, Dilemmaer,fos,DTC, cManager, nInfo);
        DTC = new DatalagController(this,Dilemmaer,adapter);
        adapter.setDTC(DTC);
        
        Exp_list = (ExpandableListView) findViewById(R.id.Listen);
        Exp_list.setAdapter(adapter);
        create = (FloatingActionButton) findViewById(R.id.button2);
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
        if(nInfo != null && nInfo.isConnected()){
        }else{
            Toast toast = Toast.makeText(this, "Du har ikke internetforbindelse, og folk vil ikke kunne se din stemme før denne blive oprettet igen", Toast.LENGTH_SHORT);
            toast.show();
        }
        Intent i = new Intent(MainActivity.this,Create.class);
        startActivityForResult(i,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                DTC.nytDilemmaIDatabase((Dilemma) data.getSerializableExtra("oprettetDilemma"));

                Snackbar.make(findViewById(android.R.id.content), "Dilemma oprettet", Snackbar.LENGTH_LONG)
                        .show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Not implemented
            }
        }
    }
}
