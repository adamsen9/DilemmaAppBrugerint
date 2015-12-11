package adamsen.dk.Dilemma40;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity{
    ArrayList <Dilemma> Dilemmaer;

    ExpandableListView Exp_list;
    DilemmaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ArrayList<Dilemma> dilemmaer = new ArrayList<Dilemma>();

        setContentView(R.layout.activity_main);
        Exp_list = (ExpandableListView) findViewById(R.id.Listen);
        Dilemmaer = DataProvider.getInfo();

        adapter = new DilemmaAdapter(this, Dilemmaer);
        Exp_list.setAdapter(adapter);
    }

}
