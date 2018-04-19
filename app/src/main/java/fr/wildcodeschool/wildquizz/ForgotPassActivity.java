package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        Button btnReturn = findViewById(R.id.btn_back);
        Button btnChangePass = findViewById(R.id.btn_reset_password);
        final EditText emailText = findViewById(R.id.et_email);
        mAuth = FirebaseAuth.getInstance();

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent(ForgotPassActivity.this, MainActivity.class);
                startActivity(returnIntent);
                finish();
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailText.getText().toString();

                if (email.isEmpty()) {
                    Toast.makeText(ForgotPassActivity.this, R.string.enter_email_please, Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ForgotPassActivity.this, R.string.email_send, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ForgotPassActivity.this, R.string.unknow_email, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }


            }
        });





    }
}
