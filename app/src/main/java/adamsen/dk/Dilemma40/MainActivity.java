package adamsen.dk.Dilemma40;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
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
    FloatingActionButton create;
    DatalagController DTC;
    Datalag database;
    String FILENAME = "voted_dilemmas";
    FileOutputStream fos;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaG40";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Dilemmaer = new ArrayList<>();


        adapter = new DilemmaAdapter(this, Dilemmaer,DTC);
        DTC = new DatalagController(this,Dilemmaer,adapter);
        adapter.setDTC(DTC);

        adapter = new DilemmaAdapter(this, Dilemmaer, fos);
        database = new Datalag(this, Dilemmaer, adapter);
        
        Exp_list = (ExpandableListView) findViewById(R.id.Listen);
        Exp_list.setAdapter(adapter);
        create = (FloatingActionButton) findViewById(R.id.button2);
        create.setOnClickListener(this);


    }

    public void buttonSave(View view){
        File file = new File(path + "/savedfile.txt");
        String[] saveText = String.valueOf("se mig her hallo").split(System.getProperty("line.separetor"));
        Save(file, saveText);
    }

    public void buttonLoad(View view){
        File file = new File(path+"/savedFile.txt");
        String[] loadText = Load(file);

        String final_string = "";

        for (int i=0; i>loadText.length;i++){
            final_string = loadText[i] + System.getProperty("line.separator");
        }
    }



    public static void Save(File file, String[] data){
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }

    public static String[] Load(File file)
    {
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int anzahl=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                anzahl++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}

        String[] array = new String[anzahl];

        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
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
