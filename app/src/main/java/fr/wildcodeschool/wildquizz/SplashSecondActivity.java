package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashSecondActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5500;
    private ImageView mZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_second_activity);

        final String idQuizz = getIntent().getStringExtra("id");
        final int nbQcm = getIntent().getIntExtra("nbQcm",0);

        mZoom = (ImageView) findViewById(R.id.img_content);
        Animation zoomAnimation = AnimationUtils.loadAnimation(SplashSecondActivity.this, R.anim.zooming);
        mZoom.startAnimation(zoomAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(SplashSecondActivity.this,PlayQuizzActivity.class);
                i.putExtra("idQuizz", idQuizz);
                i.putExtra("nbQcm", nbQcm);
                i.putExtra("scores", new int[nbQcm]);
                startActivity(i);
                finish();

            }

        }, SPLASH_TIME_OUT);


    }
}
