package fr.wildcodeschool.wildquizz;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static fr.wildcodeschool.wildquizz.R.layout.item_qcm;

public class CreateQcmActivity extends AppCompatActivity {
    private String quizz = null;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qcm);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Quizz");



        Intent recupQcm = getIntent();

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

                //TODO Récupérer l'identifiant du quizz
                String id = "1";
                String qcm = nameQcm.getText().toString();
                String ask = question.getText().toString();
                String ans1 = answer1.getText().toString();
                String ans2 = answer2.getText().toString();
                String ans3 = answer3.getText().toString();
                String ans4 = answer4.getText().toString();
                int correctAnswer = 1;//TODO récupérer le numéro de la réponse correcte

                QcmModel qcmModel = new QcmModel(qcm,ask,ans1,ans2,ans3,ans4,correctAnswer);
                myRef.push().setValue(qcmModel);




                addQcmToDB(nameQcm.getText().toString());
                Intent goToCreateQuizz = new Intent(CreateQcmActivity.this, CreateQuizzActivity.class);
                CreateQcmActivity.this.startActivity(goToCreateQuizz);


            }
        });

    }

    private void addQcmToDB(String name) {
        DbHelper mDbHelper = new DbHelper(CreateQcmActivity.this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues qcm = new ContentValues();

        qcm.put(DbContract.QcmEntry.COLUMN_NAME_QCM, name);
        long newPersonId = db.insert(DbContract.QcmEntry.TABLE_NAME, null, qcm);

    }









}
