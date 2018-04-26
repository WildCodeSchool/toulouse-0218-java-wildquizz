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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayQuizzActivity extends AppCompatActivity {

    private static final String FORMAT = "%02d:%02d";
    FirebaseDatabase mDatabase;
    int mPosition = 1;
    private TextView mTime;
    private Button mStart;
    private Button mCancel;
    private CountDownTimer mCountDownTimer;
    private FirebaseAuth mAuth;
    private String mIdQuizz;
    private String mUid;
    private int mNbQcm;
    private int mCurrentQcm;
    private int[] mScores;
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


        mIdQuizz = getIntent().getStringExtra("idQuizz");
        mCurrentQcm = getIntent().getIntExtra("currentQcm", 0);
        mNbQcm = getIntent().getIntExtra("nbQcm", 0);
        mScores = getIntent().getIntArrayExtra("scores");

        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference playRef = mDatabase.getReference("Quizz").child(mIdQuizz).child("qcmList");

        playRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                boolean hasFoundQcm = false;
                for (final DataSnapshot qcmSnapshot : dataSnapshot.getChildren()) {
                    final QcmModel qcmModel = qcmSnapshot.getValue(QcmModel.class);
                    if (i == mCurrentQcm) {
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

                        checkAnswer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //si bonne réponse 5pts sinon 0pts
                                if (mPosition == correctAnswer) {
                                    mScores[mCurrentQcm] = 5;
                                } else {
                                    mScores[mCurrentQcm] = 0;
                                }

                                if (mCurrentQcm == mNbQcm - 1) {
                                    Intent goToResults = new Intent(PlayQuizzActivity.this, ResultsActivity.class);
                                    goToResults.putExtra("idQuizz", mIdQuizz);
                                    goToResults.putExtra("scores", mScores);
                                    startActivity(goToResults);
                                } else {
                                    Intent intent = new Intent(PlayQuizzActivity.this, PlayQuizzActivity.class);
                                    intent.putExtra("idQuizz", mIdQuizz);
                                    intent.putExtra("nbQcm", mNbQcm);
                                    intent.putExtra("scores", mScores);
                                    intent.putExtra("currentQcm", mCurrentQcm + 1);
                                    startActivity(intent);
                                }

                            }
                        });

                        quit();
                    }
                    i++;
                }

                if (!hasFoundQcm) {
                    Intent goToResults = new Intent(PlayQuizzActivity.this, ResultsActivity.class);
                    goToResults.putExtra("idQuizz", mIdQuizz);
                    goToResults.putExtra("scores", mScores);
                    startActivity(goToResults);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void start() {
        mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
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
                if (mCurrentQcm == mNbQcm - 1) {
                    Intent goToResults = new Intent(PlayQuizzActivity.this, ResultsActivity.class);
                    startActivity(goToResults);
                } else {
                    Intent intent = new Intent(PlayQuizzActivity.this, PlayQuizzActivity.class);
                    intent.putExtra("idQuizz", mIdQuizz);
                    intent.putExtra("nbQcm", mNbQcm);
                    intent.putExtra("scores", mScores);
                    intent.putExtra("currentQcm", mCurrentQcm + 1);
                    startActivity(intent);
                }

            }
        };
        mCountDownTimer.start();
    }



    private void quit(){
        ImageButton leaveQuizz = findViewById(R.id.ib_leave);
        leaveQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnMenu = new Intent(PlayQuizzActivity.this, MenuActivity.class);
                startActivity(returnMenu);

            }
        });
    }


}
