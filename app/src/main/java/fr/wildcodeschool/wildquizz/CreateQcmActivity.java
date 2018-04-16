package fr.wildcodeschool.wildquizz;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateQcmActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qcm);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

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

                addQcmToDB(nameQcm.getText().toString());
                Intent goToCreateQuizz = new Intent(CreateQcmActivity.this, CreateQuizzActivity.class);
                CreateQcmActivity.this.startActivity(goToCreateQuizz);

                QcmModel qcmModel = new QcmModel(quizzId,nameQcm,question,answer1,answer2,answer3,answer4);
                myRef = database.getReference("Quizz");
                database = FirebaseDatabase.getInstance();
                myRef.push().setValue(qcmModel);

                myRef.child("Qcm").push().setValue(nameQcm.getText().toString());
                myRef.child("Question").push().setValue(question.getText().toString());
                myRef.child("Answer1").push().setValue(answer1.getText().toString());
                myRef.child("Answer2").push().setValue(answer2.getText().toString());
                myRef.child("Answer3").push().setValue(answer3.getText().toString());
                myRef.child("Answer4").push().setValue(answer4.getText().toString());



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
