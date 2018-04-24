package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateQcmActivity extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    DatabaseReference mQuizzRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qcm);
        setTitle(getString(R.string.title_create_qcm));
        mDatabase = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        final String idQuizz = intent.getStringExtra("idQuizz");

        Button validateQcm = findViewById(R.id.button_val);
        validateQcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText nameQcm = findViewById(R.id.edit_name_qcm);
                EditText question = findViewById(R.id.edit_question);
                EditText answer1 = findViewById(R.id.edit_answer_1);
                EditText answer2 = findViewById(R.id.edit_answer_2);
                EditText answer3 = findViewById(R.id.edit_answer_3);
                EditText answer4 = findViewById(R.id.edit_answer_4);

                String qcm = nameQcm.getText().toString();
                String ask = question.getText().toString();
                String ans1 = answer1.getText().toString();
                String ans2 = answer2.getText().toString();
                String ans3 = answer3.getText().toString();
                String ans4 = answer4.getText().toString();
                int correctAnswer = 1;//TODO récupérer le numéro de la réponse correcte

                final QcmModel qcmModel = new QcmModel(qcm, ask, ans1, ans2, ans3, ans4, correctAnswer);

                mQuizzRef = mDatabase.getReference("Quizz").child(idQuizz).child("qcmList");
                // Read from the database
                mQuizzRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // enregistrer le qcm dans Firebase
                        mQuizzRef.push().setValue(qcmModel);

                        Intent goToCreateQuizz = new Intent(CreateQcmActivity.this, CreateQuizzActivity.class);
                        goToCreateQuizz.putExtra("idQuizz", idQuizz);
                        CreateQcmActivity.this.startActivity(goToCreateQuizz);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
            }
        });

    }
}
