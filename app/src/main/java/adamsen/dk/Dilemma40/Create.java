package adamsen.dk.Dilemma40;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public class Create extends Activity {

    EditText textIn;
    Button buttonAdd;
    LinearLayout container;
    EditText title;
    EditText info;
    Button ok;
    Dilemma dilemma;
    final List<String> dilemma_inner = new ArrayList<String>();
    static Firebase myFirebaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_layout);
        myFirebaseRef = new Firebase("https://dilemmaapp.firebaseio.com/");

        title = (EditText) findViewById(R.id.editText_titel);
        info = (EditText) findViewById(R.id.editText_beskrivelse);
        textIn = (EditText)findViewById(R.id.textin);
        buttonAdd = (Button)findViewById(R.id.add);
        container = (LinearLayout)findViewById(R.id.container);
        ok = (Button) findViewById(R.id.button_ok);

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
                Button buttonRemove = (Button) addView.findViewById(R.id.remove);
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
                    Toast toast4 = Toast.makeText(getApplicationContext(), "Dilemmaet er lavet", Toast.LENGTH_SHORT);
                    toast4.show();
                    String[] arr = dilemma_inner.toArray(new String[dilemma_inner.size()]);
                    dilemma = new Dilemma(title.getText().toString(), info.getText().toString(), arr);
                    newDilemmaDatabase(dilemma);
                    finish();

                }
            }
        });
    }

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

    public static void newDilemmaDatabase(Dilemma d) {
        myFirebaseRef.push().setValue(d);
    }
}
