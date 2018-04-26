package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayQuizzActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    int mPosition = 1;
    private TextView mTime;
    private Button mStart;
    private Button mCancel;
    private CountDownTimer mCountDownTimer;
    private FirebaseAuth mAuth;
    private String idQuizz;
    private String mUid;
    private static final String FORMAT = "%02d:%02d";

    private int mCurrentQcm = 0;

    private int scoreQcm1 = 0;
    private int scoreQcm2 = 0;
    private int scoreQcm3 = 0;
    private int[] array1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quizz);

        mStart = findViewById(R.id.btn_start);
        mStart.setOnClickListener(btnClickOnListener);
        mCancel = findViewById(R.id.btn_stop);
        mCancel.setOnClickListener(btnClickOnListener);
        mTime = findViewById(R.id.count);
        start();
        final ImageButton checkAnswer = findViewById(R.id.ib_next);


        final TextView tvQuestion = findViewById(R.id.tv_question);
        final TextView tvAnswer1 = findViewById(R.id.tv_answer1);
        final TextView tvAnswer2 = findViewById(R.id.tv_answer2);
        final TextView tvAnswer3 = findViewById(R.id.tv_answer3);
        final TextView tvAnswer4 = findViewById(R.id.tv_answer4);

        RadioGroup radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) radioGroup.findViewById(i);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    switch (i) {
                        case R.id.radio_btn_1:
                            mPosition = 1;
                            break;
                        case R.id.radio_btn_2:
                            mPosition = 2;
                            break;
                        case R.id.radio_btn_3:
                            mPosition = 3;
                            break;
                        case R.id.radio_btn_4:
                            mPosition = 4;
                            break;
                    }
                }
            }
        });

        final List<QcmModel> qcmModelList = new ArrayList();
        final List<Integer> myList = new ArrayList<Integer>();
        //TODO : récupérer le qcm :
        idQuizz = getIntent().getStringExtra("idQuizz");
        final int currentQcm = getIntent().getIntExtra("currentQcm", 0);
        final int nbQcm = getIntent().getIntExtra("nbQcm", 0);
        final int[] scores = getIntent().getIntArrayExtra("scores");

        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference playRef = mDatabase.getReference("Quizz").child(idQuizz).child("qcmList");

        //TODO : récupérer la valeur de position et l'incrémenter de 1 dans une boucle for :
        playRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                boolean hasFoundQcm = false;
                for (final DataSnapshot qcmSnapshot : dataSnapshot.getChildren()) {
                    final QcmModel qcmModel = qcmSnapshot.getValue(QcmModel.class);
                    if (i == currentQcm) {
                        hasFoundQcm = true;
                        String question = qcmModel.getQuestion();
                        tvQuestion.setText(question);
                        String answer1 = qcmModel.getAnswer1();
                        tvAnswer1.setText(answer1);
                        String answer2 = qcmModel.getAnswer2();
                        tvAnswer2.setText(answer2);
                        String answer3 = qcmModel.getAnswer3();
                        tvAnswer3.setText(answer3);
                        String answer4 = qcmModel.getAnswer4();
                        tvAnswer4.setText(answer4);
                        final int correctAnswer = qcmModel.getCorrectAnswer();

                        //Button check
                        final int j = i;
                        checkAnswer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //si bonne réponse 5pts sinon 0pts
                                if (mPosition == correctAnswer) {
                                    scores[j] = 5;
                                } else {
                                    scores[j] = 0;
                                }

                                if (j == nbQcm - 1){
                                    //TODO : afficher la page de résultats
                                    Intent goToResults = new Intent(PlayQuizzActivity.this, ResultsActivity.class);
                                    goToResults.putExtra("idQuizz", idQuizz);
                                    goToResults.putExtra("scores", scores);
                                    startActivity(goToResults);
                                }
                                else {
                                    //TODO : passer au QCM suivant :
                                    Intent intent = new Intent(PlayQuizzActivity.this, PlayQuizzActivity.class);
                                    intent.putExtra("idQuizz", idQuizz);
                                    intent.putExtra("nbQcm", nbQcm);
                                    intent.putExtra("scores", scores);
                                    intent.putExtra("currentQcm", j+1);
                                    startActivity(intent);
                                }

                            }
                        });

                        quit();
                    }
                    i++;
                }

                if (!hasFoundQcm) {
                    //TODO : afficher la page de résultats
                    Intent goToResults = new Intent(PlayQuizzActivity.this, ResultsActivity.class);
                    goToResults.putExtra("idQuizz", idQuizz);
                    goToResults.putExtra("scores", scores);
                    startActivity(goToResults);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }



    private void start() {
        mCountDownTimer = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTime.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }
            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
    }


    private View.OnClickListener btnClickOnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_start:
                    start();
                    break;
            }
        }
    };

    private void quit(){
        ImageButton leaveQuizz = findViewById(R.id.ib_leave);
        leaveQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : abandon = 0 pts
                final int scoreQuit = 0;
                Intent returnMenu = new Intent(PlayQuizzActivity.this, MenuActivity.class);
                startActivity(returnMenu);
                mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final DatabaseReference quitRef = mDatabase.getReference("Users").child(mUid);
                quitRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        quitRef.child("score").setValue(scoreQuit);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });
    }


}
