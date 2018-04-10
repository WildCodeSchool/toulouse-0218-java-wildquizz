package fr.wildcodeschool.wildquizz;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReultatsActivity extends AppCompatActivity {

    private TextView time;
    private Button start;
    private Button cancel;
    private CountDownTimer countDownTimer;


    private View.OnClickListener btnclickonlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn:
                    start();
                    break;
                case R.id.btnstop:
                    cancel();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reultats);
        start = (Button) findViewById(R.id.btn);
        start.setOnClickListener(btnclickonlistener);
        cancel = (Button) findViewById(R.id.btnstop);
        cancel.setOnClickListener(btnclickonlistener);
        time = (TextView) findViewById(R.id.count);

    }
    private void start () {
        time.setText("00:20");

        countDownTimer = new CountDownTimer(20 * 1000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText("" + millisUntilFinished / 1000);


            }

            @Override
            public void onFinish() {
                time.setText("");
            }


        };

        countDownTimer.start();
    }
    private void cancel () {
        if (countDownTimer !=null){
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }


}
