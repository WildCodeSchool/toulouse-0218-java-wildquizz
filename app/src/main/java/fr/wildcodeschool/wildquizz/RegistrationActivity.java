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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private ImageView mImg;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmPassword;

    private Button buttonCreate;

    private StorageReference mStorageRef;
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

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
        mPassword= findViewById(R.id.edit_pass);
        mConfirmPassword = findViewById(R.id.edit_pass_confirm);

        buttonCreate = findViewById(R.id.button_create);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String child = mUsername.getText().toString();

                databaseReference = database.getReference("Users").child(child);
                databaseReference.child("Name").setValue(mUsername.getText().toString());
                databaseReference.child("Password").setValue(mPassword.getText().toString());
                databaseReference.child("ConfirmPassword").setValue(mConfirmPassword.getText().toString());

                Intent gotoMenu = new Intent(RegistrationActivity.this, MenuActivity.class);
                RegistrationActivity.this.startActivity(gotoMenu);
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
