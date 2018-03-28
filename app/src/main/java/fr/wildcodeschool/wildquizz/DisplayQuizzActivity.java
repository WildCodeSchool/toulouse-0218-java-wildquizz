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
            results.add(new DisplayQuizzModel(000,100,3.8));
            results.add(new DisplayQuizzModel(001,200,5.8));

        } catch (Exception e) {

        }

    }


}
