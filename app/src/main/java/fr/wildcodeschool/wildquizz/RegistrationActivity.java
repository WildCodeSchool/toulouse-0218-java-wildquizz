package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Intent register = getIntent();

        final Button registerValue = findViewById(R.id.button_create);

        CheckBox checkboxCGU = findViewById(R.id.check_cgu);
        checkboxCGU.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                 if (isChecked) {
                    registerValue.setEnabled(!isChecked);
                }

                registerValue.setEnabled(isChecked);
            }
        });

        Button buttonCreate = findViewById(R.id.button_create);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMenu = new Intent(RegistrationActivity.this, MenuActivity.class);
                RegistrationActivity.this.startActivity(gotoMenu);
            }
        });

    }
}
