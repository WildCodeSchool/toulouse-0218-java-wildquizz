package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayQuizzActivity extends AppCompatActivity {

    private TextView mTime;
    private Button mStart;
    private Button mCancel;
    private CountDownTimer mCountDownTimer;



    private View.OnClickListener btnClickOnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
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
        mStart = (Button) findViewById(R.id.btn_start);
        mStart.setOnClickListener(btnClickOnListener);
        mCancel = (Button) findViewById(R.id.btn_stop);
        mCancel.setOnClickListener(btnClickOnListener);
        mTime = (TextView) findViewById(R.id.count);
        start();
    }

    private void start () {
        mTime.setText("00:20");
        mCountDownTimer = new CountDownTimer(20 * 1000, 1000){
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

    private void cancel () {
        if (mCountDownTimer !=null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
        //TODO : si le timer = 0, passage au qcm suivant :

    }


    public void ValidateAnswer(View view) {
        //TODO : si bonne réponse 5pts sinon 0pts



        //TODO : passage au qcm suivant

        Intent goToResults = new Intent(PlayQuizzActivity.this, ResultsActivity.class);
        PlayQuizzActivity.this.startActivity(goToResults);
    }
}
