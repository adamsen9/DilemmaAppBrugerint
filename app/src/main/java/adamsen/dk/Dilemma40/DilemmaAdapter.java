package adamsen.dk.Dilemma40;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Frederik on 11/27/2015.
 */
public class DilemmaAdapter extends BaseExpandableListAdapter {

    private Context ctx;
    private ArrayList<Dilemma> Dilemmaer;
    ArrayList<String> options;
    RadioGroup rg;
    DatalagController DTC;


    public DilemmaAdapter(Context ctx, ArrayList<Dilemma> Dilemmaer, DatalagController DTC) {
        this.ctx = ctx;
        this.Dilemmaer = Dilemmaer;
        this.DTC = DTC;
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

        Button vote = (Button) convertView.findViewById(R.id.button);
        final View finalConvertView = convertView;
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeMessage(rg, finalConvertView, parent);
            }
        });


        //Toast
        String message = ((Dilemma) getChild(parent, child)).getTitel();


        //Return
        return convertView;
    }

    private void makeMessage(RadioGroup rg, View convertView, int parent) {
        int index = rg.indexOfChild(convertView.findViewById(rg.getCheckedRadioButtonId()));


        //Dilemmaer.get(parent).addVotes(index);

        for (int i = 0; i < rg .getChildCount(); i++) {
            ((RadioButton) rg.getChildAt(i)).setText(Dilemmaer.get(parent).getVotes()[i] + " - " + options.get(i));
        }
        afgivSteme(Dilemmaer.get(parent), index);
        Button vote = (Button) convertView.findViewById(R.id.button);
        vote.setVisibility(View.GONE);
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
