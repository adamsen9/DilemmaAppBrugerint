package adamsen.dk.Dilemma40.Boundary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Vibrator;


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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


import adamsen.dk.Dilemma40.Controller.DatalagController;
import adamsen.dk.Dilemma40.Create;
import adamsen.dk.Dilemma40.Entity.Datalag;
import adamsen.dk.Dilemma40.Entity.Dilemma;
import adamsen.dk.Dilemma40.R;

public class MainActivity extends Activity implements View.OnClickListener, SensorEventListener {
    static ArrayList <Dilemma> Dilemmaer;

    static ExpandableListView Exp_list;
    DilemmaAdapter adapter;
    FloatingActionButton create;
    DatalagController DTC;
    Datalag database;
    String FILENAME = "voted_dilemmas";
    FileOutputStream fos;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaG40";
    NetworkInfo nInfo;
    ConnectivityManager cManager;

    Vibrator v;
    static boolean shakeState = false;

    int count = 1;
    private boolean init;
    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private float x1, x2, x3;
    private static final float ERROR = (float) 7.0;


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

        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        List<Sensor> listOfSensorsOnDevice = mSensorManager.getSensorList(Sensor.TYPE_ALL);


        for (int i = 0; i < listOfSensorsOnDevice.size(); i++) {
            if (listOfSensorsOnDevice.get(i).getType() == Sensor.TYPE_ACCELEROMETER) {
                init = false;
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                break;

            } else {

            }
        }



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
            System.out.println("Dingeling");
        }else{
            System.out.println("Ding");
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

    @Override
    public void onSensorChanged(SensorEvent e) {

        //Get x,y and z values
        float x,y,z;
        x = e.values[0];
        y = e.values[1];
        z = e.values[2];


        if (!init) {
            x1 = x;
            x2 = y;
            x3 = z;
            init = true;
        } else {

            float diffX = Math.abs(x1 - x);
            float diffY = Math.abs(x2 - y);
            float diffZ = Math.abs(x3 - z);

            //Handling ACCELEROMETER Noise
            if (diffX < ERROR) {

                diffX = (float) 0.0;
            }
            if (diffY < ERROR) {
                diffY = (float) 0.0;
            }
            if (diffZ < ERROR) {

                diffZ = (float) 0.0;
            }

            x1 = x;
            x2 = y;
            x3 = z;

            //Horizontal Shake Detected!
            if (diffX*10 > diffY) {
                onShakeEvent();
                v.vibrate(400);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Intet at lave
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public static void onShakeEvent() {

        if(shakeState) {
            //Hvis der sidst blev åbnet
            //Luk alle
            shakeState = false;
            for(int i = 0; i < Dilemmaer.size(); i++) {
                Exp_list.collapseGroup(i);
            }
        } else {
            //Hvis der sidst blev lukket
            //Åben alle
            shakeState = true;
            for(int i = 0; i < Dilemmaer.size(); i++) {
                Exp_list.expandGroup(i);
            }
        }
    }
}
