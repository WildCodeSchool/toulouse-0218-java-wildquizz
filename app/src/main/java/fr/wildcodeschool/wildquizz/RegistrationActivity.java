package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    private FirebaseAuth mAuth;

    private ImageView mImg;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mEmail;
    private Button mButtonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mDatabase = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();

        Intent register = getIntent();

        //CLICK SUR L'IMAGE :
        mImg = findViewById(R.id.image_first);
        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        mUsername = findViewById(R.id.edit_username);
        mPassword = findViewById(R.id.edit_pass);
        mConfirmPassword = findViewById(R.id.edit_pass_confirm);
        mEmail = findViewById(R.id.edit_email2);


        //CLICK SUR LE BOUTON CREER SON COMPTE :
        mButtonCreate = findViewById(R.id.button_create);
        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String child = mUsername.getText().toString();
                String password = mPassword.getText().toString();
                String confirmPassword = mConfirmPassword.getText().toString();
                String email = mEmail.getText().toString();


                // Hasher un mot de passe :
                // HashCode hashCode = Hashing.sha256().hashString(password, Charset.defaultCharset());

                if (email.matches("") || child.matches("") || password.matches("") || confirmPassword.matches("")) {
                    Toast.makeText(RegistrationActivity.this, "Veuillez renseigner les champs obligatoires", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!password.equals(confirmPassword)){
                        Toast.makeText(RegistrationActivity.this, "Vous n'avez pas rennseigné le même mot de passe", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    //user is successful registered and logged in

                                    //DATABASE :
                                    mDatabaseReference = mDatabase.getReference("Users").child(child);
                                    mDatabaseReference.child("Name").setValue(mUsername.getText().toString());
                                    mDatabaseReference.child("Password").setValue(mPassword.getText().toString());
                                    mDatabaseReference.child("ConfirmPassword").setValue(mConfirmPassword.getText().toString());

                                    Toast.makeText(RegistrationActivity.this, "Registered succesfully", Toast.LENGTH_SHORT).show();
                                    Intent gotoMenu = new Intent(RegistrationActivity.this, MenuActivity.class);
                                    RegistrationActivity.this.startActivity(gotoMenu);
                                } else {
                                    Toast.makeText(RegistrationActivity.this, "Could not register, please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        mImg.setImageBitmap(bitmap);
        Glide.with(this).load(bitmap).apply(RequestOptions.circleCropTransform()).into(mImg);
    }



}
