package fr.wildcodeschool.wildquizz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ArrayList<ResultsModel> resultsModelArrayList = new ArrayList<>();
        resultsModelArrayList.add(new ResultsModel("aaa","bbb",R.drawable.ic_launcher_foreground));
        resultsModelArrayList.add(new ResultsModel("ccc","ddd",R.drawable.ic_launcher_foreground,"fff"));


        ResultsAdapter resultsAdapter = new ResultsAdapter(this, resultsModelArrayList);

        ListView studentsListView
                = (ListView) findViewById(R.id.list_resultats);

        studentsListView.setAdapter(resultsAdapter);

    }
}
