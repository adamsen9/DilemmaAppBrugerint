package adamsen.dk.Dilemma40.Boundary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adamsen.dk.Dilemma40.Controller.DatalagController;
import adamsen.dk.Dilemma40.Entity.Dilemma;
import adamsen.dk.Dilemma40.R;

/**
 * Created by Frederik on 11/27/2015.
 */
public class DilemmaAdapter extends BaseExpandableListAdapter {

    private Context ctx;
    private ArrayList<Dilemma> Dilemmaer;
    ArrayList<String> options;
    RadioGroup rg;
    DatalagController DTC;
    String string;
    ArrayList<String> voted;
    FileOutputStream fos;
    String filename = "Android.txt";
    File path=Environment.getExternalStorageDirectory();
    File textfile = new File(path, filename);
    ConnectivityManager cManager;
    NetworkInfo nInfo;




    public DilemmaAdapter(Context ctx, ArrayList<Dilemma> Dilemmaer, FileOutputStream fos,DatalagController DTC, ConnectivityManager cManager, NetworkInfo nInfo) {
        this.ctx = ctx;
        this.Dilemmaer = Dilemmaer;
        this.fos = fos;
        try {
            textfile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.DTC = DTC;
        this.cManager = cManager;
        this.nInfo = nInfo;
    }

    @Override
    public int getGroupCount() {

        //Returnerer antallet af liste-elementer, dvs. antallet af dilemmaer
        return Dilemmaer.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        //Returnerer antallet af børn, dette skal altid kun være 1 (der er kun et dilemma per liste-element)
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return Dilemmaer.get(groupPosition);
    }

    @Override
    public Object getChild(int parent, int child) {
        //Denne returnerer dilemmaet tilhørende det pågældende liste-element
        //int child er altid 0 (det første element)
        //int parent er det tilsvarende dilemma
        return Dilemmaer.get(parent);
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int parent, int child) {

        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {

        String group_title = Dilemmaer.get(parent).getTitel();

        if(convertView == null) {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.parent_layout,parentView,false);
        }
        TextView parent_textview = (TextView) convertView.findViewById(R.id.parent_txt);
        parent_textview.setText(group_title);
        return convertView;
    }

    @Override
    public View getChildView(final int parent, int child, boolean isLastChild, View convertView, ViewGroup parentView) {

        //Beskrivelse hentes
        String dilemma_desc = ((Dilemma) getChild(parent, child)).getDesc();

        LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflator.inflate(R.layout.child_layout,parentView,false);

        TextView child_textview = (TextView) convertView.findViewById(R.id.dilemma_txt);
        child_textview.setText(dilemma_desc);



        //Valgmuligheder indlæses
        options = new ArrayList<String>(Arrays.asList(((Dilemma) getChild(parent, child)).getOptions()));

        rg = (RadioGroup) convertView.findViewById(R.id.dilemma_valgmuligheder);
        RadioButton button;
        for(String s : options) {

            button = new RadioButton(ctx);
            button.setText(s);
            rg.addView(button);
        }

        try {
            if(existsalready(Dilemmaer.get(parent).getId())){
                hide(parent,convertView);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button vote = (Button) convertView.findViewById(R.id.button);
        final View finalConvertView = convertView;
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    makeMessage(rg, finalConvertView, parent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //Toast
        String message = ((Dilemma) getChild(parent, child)).getTitel();


        //Return
        return convertView;
    }

    private void makeMessage(RadioGroup rg, View convertView, int parent) throws IOException {

        if(nInfo != null && nInfo.isConnected()){
        }else{
            Toast toast = Toast.makeText(ctx, "Du har ikke internetforbindelse, og folk vil ikke kunne se din stemme før denne blive oprettet igen", Toast.LENGTH_SHORT);
            toast.show();
        }

        int index = rg.indexOfChild(convertView.findViewById(rg.getCheckedRadioButtonId()));
        if(index != -1) {
            Dilemmaer.get(parent).addVotes(index);
            afgivSteme(Dilemmaer.get(parent), index);


            writetext(Dilemmaer.get(parent).getId());
            hide(parent, convertView);
        }
    }

    public void hide(int parent, View convertView){
        for (int i = 0; i < rg .getChildCount(); i++) {
            ((RadioButton) rg.getChildAt(i)).setText(Dilemmaer.get(parent).getVotes()[i] + " - " + options.get(i));
        }
        Button vote = (Button) convertView.findViewById(R.id.button);
        vote.setVisibility(View.GONE);
    }


    public List<String> readFile() throws IOException {
        //Get the text file
        File file = new File(path,filename);
        //Read text from file
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            System.out.println(e);
        }
        List<String> list = Arrays.asList(text.toString().split(","));
        return list;
    }

    public boolean existsalready(String string) throws IOException {
        List<String> list = readFile();
        System.out.println(list.size());
        for(int i = 0; i < list.size(); i++){

            if(list.get(i).equals(null)){
                System.out.println("Jeg var null");
                return true;
            }
            else if(list.get(i).equals(string)){
                System.out.println("Jeg var ikke null, men identisk med noget andetZ");
                return true;
            }
        }
        System.out.println("Jeg fandt ingenting");

        return false;
    }

    public void writetext(String string) throws IOException {
        FileWriter f;
        try {
            f = new FileWriter(path+"/"+filename, true);
            f.write(string+",");
            f.flush();
            f.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void afgivSteme(Dilemma d, int stemmeNr) {
        DTC.afgivStemme(d, stemmeNr);

    }

    public void setDTC(DatalagController DTC) {
        this.DTC = DTC;
    }
}
