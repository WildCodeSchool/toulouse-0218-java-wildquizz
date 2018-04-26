package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateQcmActivity extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    DatabaseReference mQuizzRef;
    int mPosition = 1;
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

                if (qcm.isEmpty() || ask.isEmpty() || ans1.isEmpty() || ans2.isEmpty() || ans3.isEmpty()
                         || ans4.isEmpty()){

                    Toast.makeText(CreateQcmActivity.this, "Remplissez tous les champs s'il vous plaît !", Toast.LENGTH_SHORT).show();

                }
                else {
                    qcm = nameQcm.getText().toString();
                    ask = question.getText().toString();
                    ans1 = answer1.getText().toString();
                    ans2 = answer2.getText().toString();
                    ans3 = answer3.getText().toString();
                    ans4 = answer4.getText().toString();

                    final QcmModel qcmModel = new QcmModel(qcm, ask, ans1, ans2, ans3, ans4, mPosition);

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




            }
        });

        RadioGroup radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)radioGroup.findViewById(i);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    switch (i){
                        case R.id.radiobtn_1 :
                            mPosition = 1;
                            break;
                        case R.id.radiobtn_2:
                            mPosition = 2;
                            break;
                        case R.id.radiobtn_3:
                            mPosition = 3;
                            break;
                        case R.id.radiobtn_4:
                            mPosition = 4;
                            break;
                    }
                }
            }
        });
    }
}
