package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

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
                EditText userNameValue = findViewById(R.id.edit_username);
                EditText password = findViewById(R.id.edit_password);
                EditText passwordConfirm = findViewById(R.id.edit_pass_confirm);

                 if (isChecked) {
                    registerValue.setEnabled(!isChecked);
                }

                registerValue.setEnabled(isChecked);
            }
        });

    }
}
