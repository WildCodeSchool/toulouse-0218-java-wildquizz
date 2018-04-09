package fr.wildcodeschool.wildquizz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);

        ArrayList<ResultatsModel> resultatsModelArrayList = new ArrayList<>();
        resultatsModelArrayList.add(new ResultatsModel("aaa","bbb",R.drawable.ic_launcher_foreground));
        resultatsModelArrayList.add(new ResultatsModel("ccc","ddd",R.drawable.ic_launcher_foreground,"fff"));


        ResultatsAdapter resultatsAdapter = new ResultatsAdapter(this, resultatsModelArrayList);

        ListView studentsListView
                = (ListView) findViewById(R.id.list_resultats);

        studentsListView.setAdapter(resultatsAdapter);

    }
}
