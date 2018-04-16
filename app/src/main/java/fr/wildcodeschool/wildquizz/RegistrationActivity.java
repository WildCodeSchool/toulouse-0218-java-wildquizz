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

    private static final int PICK_IMAGE_RESULTS = 1;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private ImageView mImg;

    private Button buttonCreate;
    private Bitmap bitmap;

    private Uri filePath;

    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("images");

        Intent register = getIntent();



        mImg = findViewById(R.id.image_first);
        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //choisir une image :
                afficherImage();
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(intent, 0);
            }
        });

        buttonCreate = findViewById(R.id.button_create);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = findViewById(R.id.edit_username);
                EditText passord= findViewById(R.id.edit_pass);
                EditText confirmPassword = findViewById(R.id.edit_pass_confirm);
                String child = username.getText().toString();

                databaseReference = database.getReference("Users").child(child);
                databaseReference.child("Name").setValue(username.getText().toString());
                databaseReference.child("Password").setValue(passord.getText().toString());
                databaseReference.child("ConfirmPassword").setValue(confirmPassword.getText().toString());

                //upload l'image selectionnée :
                uploadFile();

                Intent gotoMenu = new Intent(RegistrationActivity.this, MenuActivity.class);
                RegistrationActivity.this.startActivity(gotoMenu);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_RESULTS && resultCode == RESULT_OK) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mImg.setImageBitmap(bitmap);
                bitmap = (Bitmap) data.getExtras().get("data");
                Glide.with(this)
                        .load(bitmap)
                        .apply(RequestOptions.circleCropTransform())
                        .into(mImg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //choisir un fichier dans l'appareil :
    private void afficherImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an image"), PICK_IMAGE_RESULTS);
    }

    //upload l'image dans firebase :
    private void uploadFile(){
        if (filePath != null) {
            StorageReference riversRef = mStorageRef.child("image_profile.jpg").child(filePath.getLastPathSegment());
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            //TODO : voir cours
                            //databaseReference.child(name).child("image").setValue(downloadUrl.toString());
                            Toast.makeText(RegistrationActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(RegistrationActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            //affiche un toast d'erreur :

        }
    }


}
