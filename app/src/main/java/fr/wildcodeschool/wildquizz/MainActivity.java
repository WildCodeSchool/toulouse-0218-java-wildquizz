package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonConnexion = findViewById(R.id.button_connexion);
        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMenu = new Intent(MainActivity.this, MenuActivity.class);
                MainActivity.this.startActivity(gotoMenu);
            }
        });

        Button buttonRegister = findViewById(R.id.button_registration);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoRegistration = new Intent(MainActivity.this, RegistrationActivity.class);
                MainActivity.this.startActivity(gotoRegistration);
            }
        });

    }
}
