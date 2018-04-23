package fr.wildcodeschool.wildquizz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListQuizzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_quizz);

        ListView listView = findViewById(R.id.quizz_list);

        final ArrayList<QuizzModel> listQuizz = new ArrayList<>();


        final ListQuizzAdapter quizzAdapter = new ListQuizzAdapter(ListQuizzActivity.this, listQuizz);
        listView.setAdapter(quizzAdapter);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query myRef = database.getReference("Quizz");

        // Read from the database
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                listQuizz.clear();
                for (DataSnapshot quizzIdSnap : dataSnapshot.getChildren()) {
                    QuizzModel quizzModel = quizzIdSnap.getValue(QuizzModel.class);
                    listQuizz.add(quizzModel);
                }
                quizzAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }
}
