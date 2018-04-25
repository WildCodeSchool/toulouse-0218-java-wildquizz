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
        //TODO : récupérer le qcm :
        idQuizz = getIntent().getStringExtra("idQuizz");
        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference playRef = mDatabase.getReference("Quizz").child(idQuizz).child("qcmList");
        /*for (int i = 1; i < 4; i++) {
            playRef.limitToFirst(i).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (final DataSnapshot qcmSnapshot : dataSnapshot.getChildren()) {
                        final QcmModel qcmModel = qcmSnapshot.getValue(QcmModel.class);
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

                        checkAnswer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                return;

                            }
                        });
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            */
            playRef.limitToFirst(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (final DataSnapshot qcmSnapshot : dataSnapshot.getChildren()) {
                        final QcmModel qcmModel = qcmSnapshot.getValue(QcmModel.class);
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


                        checkAnswer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO : si bonne réponse 5pts sinon 0pts
                                int goodAnswer = 5;
                                int badAnswer = 0;
                                if (mPosition == correctAnswer) {
                                    int[] array = new int[]{goodAnswer};
                                    int score = ScoreClass.foundQuizzScore(array);
                                } else {
                                    int[] array = new int[]{badAnswer};
                                    int score = ScoreClass.foundQuizzScore(array);
                                }

                                //TODO : passage au qcm suivant
                                //String idQuizz = getIntent().getStringExtra("idQuizz");
                                //mDatabase = FirebaseDatabase.getInstance();
                                //DatabaseReference playRef2 = mDatabase.getReference("Quizz").child(idQuizz).child("qcmList");
                                playRef.limitToLast(1).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot qcmSnap : dataSnapshot.getChildren()) {
                                            QcmModel qcmModel1 = qcmSnap.getValue(QcmModel.class);
                                            qcmModelList.add(qcmModel1);
                                            String question = qcmModel1.getQuestion();
                                            tvQuestion.setText(question);
                                            String answer1 = qcmModel1.getAnswer1();
                                            tvAnswer1.setText(answer1);
                                            String answer2 = qcmModel1.getAnswer2();
                                            tvAnswer2.setText(answer2);
                                            String answer3 = qcmModel1.getAnswer3();
                                            tvAnswer3.setText(answer3);
                                            String answer4 = qcmModel1.getAnswer4();
                                            tvAnswer4.setText(answer4);
                                            final int correctAnswer = qcmModel1.getCorrectAnswer();
                                            checkAnswer.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    //TODO : si bonne réponse 5pts sinon 0pts
                                                    int goodAnswer = 5;
                                                    int badAnswer = 0;
                                                    if (mPosition == correctAnswer) {
                                                        int[] array = new int[]{goodAnswer};
                                                        int score = ScoreClass.foundQuizzScore(array);
                                                    } else {
                                                        int[] array = new int[]{badAnswer};
                                                        int score = ScoreClass.foundQuizzScore(array);
                                                    }
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });



                            /*
                            //Finish quizz :
                            Intent goToResults = new Intent(PlayQuizzActivity.this, ResultsActivity.class);
                            PlayQuizzActivity.this.startActivity(goToResults);*/
                            }
                        });
                        quit();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            quit();


    }



    private void start() {
        mCountDownTimer = new CountDownTimer(60 * 5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //mTime.setText(String.valueOf(millisUntilFinished / 1000));
                mTime.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                mTime.setText("");
            }
        };
        mCountDownTimer.start();
    }

    private void cancel() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        } else {
            //TODO : si le timer = 0, passage au qcm suivant :
        }
    }

    private View.OnClickListener btnClickOnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_start:
                    start();
                    break;
                case R.id.btn_stop:
                    cancel();
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
