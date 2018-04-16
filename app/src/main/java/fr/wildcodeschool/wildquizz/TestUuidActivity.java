package fr.wildcodeschool.wildquizz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;



//ACTIVITE QUI SERT A MONTRER COMMENT ON GENERE UN CODE UNIQUE AVEC 3 CHIFFRE, 2 LETTRES ET 3 CHIFFRES


public class TestUuidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_uuid);

        final TextView textGenerate = findViewById(R.id.textView);
        Button btnGenerate = findViewById(R.id.button);
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Genère des strings avec 8 symbols
                textGenerate.setText(generateString1(3)+generateString2(2)+generateString3(3));
            }
        });
    }

    private String generateString1(int length) {
        //char[] chars = "abcdefghijklmnopqrstuvwxyz123456789".toCharArray();
        char[] char1 = "123456789".toCharArray();
        StringBuilder stringBuilder1 = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            char c = char1[random.nextInt(char1.length)];
            stringBuilder1.append(c);
        }
        return stringBuilder1.toString();
    }

    private String generateString2(int length) {
        char[] char2 = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder stringBuilder2 = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            char c = char2[random.nextInt(char2.length)];
            stringBuilder2.append(c);
        }
        return stringBuilder2.toString();
    }

    private String generateString3(int length) {
        char[] char3 = "123456789".toCharArray();
        StringBuilder stringBuilder3 = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            char c = char3[random.nextInt(char3.length)];
            stringBuilder3.append(c);
        }
        return stringBuilder3.toString();
    }


}
