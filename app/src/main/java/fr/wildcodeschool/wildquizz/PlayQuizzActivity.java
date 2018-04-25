package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlayQuizzActivity extends AppCompatActivity {

    FirebaseDatabase mDatabase;
    private TextView mTime;
    private Button mStart;
    private Button mCancel;
    private CountDownTimer mCountDownTimer;
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


        final TextView tvQuestion = findViewById(R.id.tv_question);
        final TextView tvAnswer1 = findViewById(R.id.tv_answer1);
        final TextView tvAnswer2 = findViewById(R.id.tv_answer2);
        final TextView tvAnswer3 = findViewById(R.id.tv_answer3);
        final TextView tvAnswer4 = findViewById(R.id.tv_answer4);

        //TODO : récupérer le qcm :
        String idQuizz = getIntent().getStringExtra("idQuizz");
        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference playRef = mDatabase.getReference("Quizz").child(idQuizz).child("qcmList");
        playRef.limitToFirst(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot qcmSnapshot : dataSnapshot.getChildren()) {
                    QcmModel qcmModel = qcmSnapshot.getValue(QcmModel.class);
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
                    int correctAnswer = qcmModel.getCorrectAnswer();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ImageButton checkAnswer = findViewById(R.id.ib_next);
        checkAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : si bonne réponse 5pts sinon 0pts

                //TODO : passage au qcm suivant


                Intent goToResults = new Intent(PlayQuizzActivity.this, ResultsActivity.class);
                PlayQuizzActivity.this.startActivity(goToResults);

            }
        });
        ImageButton leaveQuizz = findViewById(R.id.ib_leave);
        leaveQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : abandon = 0 pts


            }
        });


    }

    private void start() {
        mTime.setText("00:20");
        mCountDownTimer = new CountDownTimer(20 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTime.setText(String.valueOf(millisUntilFinished / 1000));
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
        }
        else {
            //TODO : si le timer = 0, passage au qcm suivant :

        }




    }


}
