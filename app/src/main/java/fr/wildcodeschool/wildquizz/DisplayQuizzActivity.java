package fr.wildcodeschool.wildquizz;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ListView;

import java.util.ArrayList;

public class DisplayQuizzActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_quizz);




        ListView lvDisplay = findViewById(R.id.list_quizz);
        ArrayList<DisplayQuizzModel> results = new ArrayList<>();
        try{
            results.add(new DisplayQuizzModel(0,100,4.0, 4));
            results.add(new DisplayQuizzModel(1,200,5.0,5));
            results.add(new DisplayQuizzModel(3,600,2.0, 2.0));
            results.add(new DisplayQuizzModel(4,600,2.0, 2.0));
            results.add(new DisplayQuizzModel(5,600,2.0, 2.0));
        } catch (Exception e) {

        }

        DisplayQuizzAdapter adapter = new DisplayQuizzAdapter(this, results);
        lvDisplay.setAdapter(adapter);


    }


}
