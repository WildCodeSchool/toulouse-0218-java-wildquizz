package fr.wildcodeschool.wildquizz;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;

    private ImageView mImg;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mEmail;

    private Button buttonCreate;

    //private StorageReference mStorageRef;
    //private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        database = FirebaseDatabase.getInstance();
        //mStorageRef = FirebaseStorage.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.edit_email2);


        Intent register = getIntent();

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

        buttonCreate = findViewById(R.id.button_create);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String child = mUsername.getText().toString();
                final String password = mPassword.getText().toString();
                final String confirmPassword = mConfirmPassword.getText().toString();
                final String email = mEmail.getText().toString();

                // Hasher un mot de passe :
                // HashCode hashCode = Hashing.sha256().hashString(password, Charset.defaultCharset());

                if (email.isEmpty() || child.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Veuillez renseigner les champs obligatoires", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                //user is successful registered and logged in
                                //start the logout activity
                                //DATABASE :
                                databaseReference = database.getReference("Users").child(child);
                                databaseReference.child("Name").setValue(mUsername.getText().toString());
                                databaseReference.child("Password").setValue(mPassword.getText().toString());
                                databaseReference.child("ConfirmPassword").setValue(mConfirmPassword.getText().toString());
                                Toast.makeText(RegistrationActivity.this, "Registered succesfully", Toast.LENGTH_SHORT).show();
                                Intent gotoMenu = new Intent(RegistrationActivity.this, MenuActivity.class);
                                RegistrationActivity.this.startActivity(gotoMenu);
                            }
                            else {
                                Toast.makeText(RegistrationActivity.this, "Could not register, please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        Glide.with(this).load(bitmap).apply(RequestOptions.circleCropTransform()).into(mImg);
    }



}
