package fr.wildcodeschool.wildquizz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import fr.wildcodeschool.wildquizz.firebase.FirebaseQuizzAdapter;
import fr.wildcodeschool.wildquizz.firebase.FirebaseQuizzModel;

public class ListQuizzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_quizz);

        ArrayList<FirebaseQuizzModel> quizzModels =
                new ArrayList<>();

       quizzModels.add(new FirebaseQuizzModel("001", new Date().getTime()));
       quizzModels.add(new FirebaseQuizzModel("002", new Date().getTime() ));
       quizzModels.add(new FirebaseQuizzModel("003", new Date().getTime()));
       quizzModels.add(new FirebaseQuizzModel("004", new Date().getTime()));


        FirebaseQuizzAdapter adapter = new FirebaseQuizzAdapter(this, quizzModels);
        ListView listView = findViewById(R.id.list_quizz);
        listView.setAdapter(adapter);

    }


}
