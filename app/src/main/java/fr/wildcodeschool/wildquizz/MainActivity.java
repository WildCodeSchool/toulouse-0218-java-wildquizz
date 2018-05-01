package fr.wildcodeschool.wildquizz;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    public static final int PASSWORD_HIDDEN = 1;
    public static final int PASSWORD_VISIBLE = 2;
    int mPasswordVisibility = PASSWORD_HIDDEN;
    private EditText mEmail;
    private EditText mPassword;

    private FirebaseDatabase mDatabase;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    public static boolean isNetworkOnline(Context con) {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);

                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                    status = true;
                } else {
                    status = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            }
        };
        mEmail = findViewById(R.id.edit_email);
        mPassword = findViewById(R.id.edit_password);



        ImageView ivPassword = findViewById(R.id.iv_password);
        // Cryptage mot de passe
        ivPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mPasswordVisibility == PASSWORD_HIDDEN) {
                    mPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    mPasswordVisibility = PASSWORD_VISIBLE;
                } else {
                    mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPasswordVisibility = PASSWORD_HIDDEN;
                }
            }
        });

        Button buttonConnexion = findViewById(R.id.button_connexion);
        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailText = mEmail.getText().toString();
                final String passwordText = mPassword.getText().toString();
                if (isNetworkOnline(getApplicationContext())) {
                    if (emailText.isEmpty() || passwordText.isEmpty()) {
                        Toast.makeText(MainActivity.this, R.string.enter_email_and_password, Toast.LENGTH_SHORT).show();
                    } else {
                        mAuth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, R.string.incorrect_email_and_password, Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent gotoMenu = new Intent(MainActivity.this, MenuActivity.class);
                                    MainActivity.this.startActivity(gotoMenu);
                                }
                            }
                        });
                    }

                }
                else{
                    Toast.makeText(MainActivity.this, R.string.advertissement_not_connected, Toast.LENGTH_LONG).show();
                }
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

        Button buttonForgetPass = findViewById(R.id.btn_forget_password);
        buttonForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });


    }


}
