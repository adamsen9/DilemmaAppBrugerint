package adamsen.dk.Dilemma40;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import adamsen.dk.Dilemma40.Controller.DatalagController;
import adamsen.dk.Dilemma40.Entity.Dilemma;

public class Create extends Activity {

    EditText textIn;
    FloatingActionButton buttonAdd;
    LinearLayout container;
    EditText title;
    EditText info;
    FloatingActionButton ok;
    Dilemma dilemma;
    final List<String> dilemma_inner = new ArrayList<String>();
    static Firebase myFirebaseRef;
    static DatalagController DTC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_layout);
        myFirebaseRef = new Firebase("https://dilemmaapp.firebaseio.com/");

        //Todo indlæsning af DTC er ikke korrekt
        Intent i = getIntent();
        DTC = (DatalagController) i.getParcelableExtra("DatalagController");
        System.out.println(DTC + " 42");



        title = (EditText) findViewById(R.id.editText_titel);
        info = (EditText) findViewById(R.id.editText_beskrivelse);
        textIn = (EditText)findViewById(R.id.textin);
        buttonAdd = (FloatingActionButton)findViewById(R.id.add);

        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(backHandler);

        container = (LinearLayout)findViewById(R.id.container);
        ok = (FloatingActionButton) findViewById(R.id.button_ok);
        buttonAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(textIn.getText().toString().isEmpty()){
                    return;
                }

                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row, null);
                final TextView textOut = (TextView) addView.findViewById(R.id.textout);
                textOut.setText(textIn.getText().toString());

                dilemma_inner.add(textOut.getText().toString());
                textIn.setText("");
                ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.remove);
                buttonRemove.setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        remove(dilemma_inner, textOut.getText().toString());
                        ((LinearLayout)addView.getParent()).removeView(addView);
                    }});
                container.addView(addView);
            }});
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Du mangler titel eller beskrivelse", Toast.LENGTH_SHORT);
                    toast1.show();


                } else if (dilemma_inner.size() < 2) {
                    Toast toast3 = Toast.makeText(getApplicationContext(), "Du har ikke skrevet min 2 valgmuligheder", Toast.LENGTH_SHORT);
                    toast3.show();
                }


                else{
                    //Toast toast4 = Toast.makeText(getApplicationContext(), "Dilemmaet er lavet", Toast.LENGTH_SHORT);
                    //toast4.show();
                    String[] arr = dilemma_inner.toArray(new String[dilemma_inner.size()]);

                    dilemma = new Dilemma(title.getText().toString(), info.getText().toString(), arr);

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("oprettetDilemma",dilemma);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();

                }
            }
        });
    }

    View.OnClickListener backHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    public boolean isEmpty(){
        if(title.getText().toString().isEmpty()) return false;
        if(info.getText().toString().isEmpty()) return false;
        return true;
    }

    public void remove(List<String> list, String string){
        for (int i = 0; i < list.size() ; i++){
            if(list.get(i).toString().equals(string)){
                list.remove(i);
            }
        }
    }

    public void okClick(){

            if (!isEmpty()) {
                Toast toast1 = Toast.makeText(getApplicationContext(), "Du mangler titel eller beskrivelse", Toast.LENGTH_SHORT);
                toast1.show();


            } else if ((dilemma_inner.size()>9)) {
                Toast toast3 = Toast.makeText(getApplicationContext(), "Du har ikke skrevet min 2 valgmuligheder", Toast.LENGTH_SHORT);
                toast3.show();
            }
                else{
                Toast toast4 = Toast.makeText(getApplicationContext(), "Gratz, hvad nu", Toast.LENGTH_SHORT);
                toast4.show();
                String[] arr = dilemma_inner.toArray(new String[dilemma_inner.size()]);
                dilemma = new Dilemma(title.getText().toString(), info.getText().toString(), arr);
        }
        Toast toast5 = Toast.makeText(getApplicationContext(), "kinda fail", Toast.LENGTH_SHORT);
        toast5.show();
    }
}
