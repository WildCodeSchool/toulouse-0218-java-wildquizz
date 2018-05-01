package fr.wildcodeschool.wildquizz;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static fr.wildcodeschool.wildquizz.RegistrationActivity.checkAndRequestPermissions;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabInfosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabInfosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabInfosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;

    private OnFragmentInteractionListener mListener;

    private TextView mScoreValueProfile;
    private String mUid;
    private ImageView mImageProfile;


    public TabInfosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabInfosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabInfosFragment newInstance(String param1, String param2) {
        TabInfosFragment fragment = new TabInfosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static boolean checkAndRequestPermissions(final Activity context) {
        int extstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (extstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    private String mGetImageUrl = "";
    private String mCurrentPhotoPath;
    private Uri mFileUri = null;
    public final static int GALLERY = 123;
    public final static int APP_PHOTO = 456;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    private TextView mTextUsername;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mImageProfile = getView().findViewById(R.id.iv_profile);
        mScoreValueProfile = getView().findViewById(R.id.tv_score_profile);
        mTextUsername = getView().findViewById(R.id.tv_username_fragment);


        //Modification de la photo de l'utilisateur :
        mImageProfile = getView().findViewById(R.id.iv_profile);
        ImageView changeAvatar = getView().findViewById(R.id.iv_change_avatar);
        changeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Changer mon avatar").setMessage("Pour changer votre avatar, il vous faut choisir une autre photo depuis votre appareil :")
                        .setPositiveButton("Prendre une photo depuis l'appareil", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (checkAndRequestPermissions(getActivity())) {
                                    dispatchTakePictureIntent();
                                }
                            }
                        })
                        .setNegativeButton("Choisir une image depuis la gallerie d'images", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GALLERY);
                            }
                        })
                        .show();
            }
        });

        //Affichage de l'avatar et du nom de l'user :
        mDatabase = FirebaseDatabase.getInstance();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference pathID = mDatabase.getReference("Users").child(mUid);
        pathID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("avatar").getValue() != null)) {
                    String url = dataSnapshot.child("avatar").getValue(String.class);
                    Glide.with(getActivity()).load(url).apply(RequestOptions.circleCropTransform()).into(mImageProfile);
                }
                if ((dataSnapshot.child("username").getValue() != null)) {
                    String nameUser = dataSnapshot.child("username").getValue(String.class);
                    mTextUsername.setText(nameUser);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //Modification de l'username :
        Button btnUpdateUsername = getView().findViewById(R.id.button_test);
        btnUpdateUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.update_dialog_username, null);
                builder.setView(dialogView);
                builder.setTitle("Editer le nom d'utilisateur");
                final AlertDialog alertDialog = builder.create();
                final EditText newUsername = (EditText) dialogView.findViewById(R.id.et_user_update_dialog);
                DatabaseReference nameRef = mDatabase.getReference("Users").child(mUid);
                nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if ((dataSnapshot.child("username").getValue() != null)) {
                            String nameUser = dataSnapshot.child("username").getValue(String.class);
                            newUsername.setHint(nameUser);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



                Button btnUpdateName = (Button) dialogView.findViewById(R.id.button_update);
                btnUpdateName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String user = (String) newUsername.getText().toString();
                        final DatabaseReference usernameUpdateRef = mDatabase.getReference("Users").child(mUid);
                        usernameUpdateRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                UserModel userModel1 = dataSnapshot.getValue(UserModel.class);
                                usernameUpdateRef.child("username").setValue(user);
                                alertDialog.cancel();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                });
                alertDialog.show();
            }
        });

        final ImageView mMedalBronze = getView().findViewById(R.id.iv_medal2);
        final ImageView mMedalRed = getView().findViewById(R.id.iv_medal1);
        final ImageView mMedalSilver = getView().findViewById(R.id.iv_medal3);
        final ImageView mMedalGold = getView().findViewById(R.id.iv_medal4);
        final TextView rang1 = getView().findViewById(R.id.tv_pts1);
        final TextView rang2 = getView().findViewById(R.id.tv_pts2);
        final TextView rang3 = getView().findViewById(R.id.tv_pts3);
        final TextView rang4 = getView().findViewById(R.id.tv_pts4);

        //Affichage du score dans la ratingBar et médailles :
        DatabaseReference scoreId = mDatabase.getReference("Users").child(mUid);
        scoreId.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);

                int scoreUser = userModel.getScore();
                mScoreValueProfile.setText(String.format(getString(R.string.scoretext), String.valueOf(scoreUser)));
                int nbQcm = userModel.getNbQcm();
                if (nbQcm > 0) {
                    float scoreUserFloat = scoreUser / nbQcm;
                    RatingBar ratingBar = getView().findViewById(R.id.rating_bar);
                    ratingBar.setFocusable(false);
                    ratingBar.setRating(scoreUserFloat);
                }

                switch (ScoreClass.getMedal(scoreUser)) {
                    case 1:
                        mMedalRed.setAlpha(1.0f);
                        rang1.setTextColor(Color.parseColor("#ff4081"));
                        rang1.setAlpha(1.0f);
                        break;

                    case 2:
                        mMedalRed.setAlpha(1.0f);
                        mMedalBronze.setAlpha(1.0f);
                        rang1.setTextColor(Color.parseColor("#ff4081"));
                        rang1.setAlpha(1.0f);
                        rang2.setTextColor(Color.parseColor("#ff4081"));
                        rang2.setAlpha(1.0f);
                        break;

                    case 3:
                        mMedalRed.setAlpha(1.0f);
                        mMedalBronze.setAlpha(1.0f);
                        mMedalSilver.setAlpha(1.0f);
                        rang1.setTextColor(Color.parseColor("#ff4081"));
                        rang1.setAlpha(1.0f);
                        rang2.setTextColor(Color.parseColor("#ff4081"));
                        rang2.setAlpha(1.0f);
                        rang3.setTextColor(Color.parseColor("#ff4081"));
                        rang3.setAlpha(1.0f);
                        break;

                    case 4:
                        mMedalRed.setAlpha(1.0f);
                        mMedalBronze.setAlpha(1.0f);
                        mMedalSilver.setAlpha(1.0f);
                        mMedalGold.setAlpha(1.0f);
                        rang1.setTextColor(Color.parseColor("#ff4081"));
                        rang1.setAlpha(1.0f);
                        rang2.setTextColor(Color.parseColor("#ff4081"));
                        rang2.setAlpha(1.0f);
                        rang3.setTextColor(Color.parseColor("#ff4081"));
                        rang3.setAlpha(1.0f);
                        rang4.setTextColor(Color.parseColor("#ff4081"));
                        rang4.setAlpha(1.0f);
                        break;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePicture = new  Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePicture.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                mFileUri = FileProvider.getUriForFile(getContext(),
                        "fr.wildcodeschool.wildquizz.fileprovider",
                        photoFile);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);
                startActivityForResult(takePicture, APP_PHOTO);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(),
                            R.string.need_camera, Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(),
                            R.string.need_storage,
                            Toast.LENGTH_SHORT).show();
                } else {
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case APP_PHOTO:
                try {
                    if (resultCode == RESULT_OK) {
                        mGetImageUrl = mFileUri.getPath();
                        saveCaptureImage();
                        Glide.with(getContext()).load(mFileUri).apply(RequestOptions.circleCropTransform()).into(mImageProfile);
                    } else {
                        //nothing
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case GALLERY:
                try {
                    if (resultCode == RESULT_OK) {
                        mFileUri = data.getData();
                        mGetImageUrl = mFileUri.getPath();
                    }
                    saveCaptureImage();
                    Glide.with(getContext()).load(mFileUri).apply(RequestOptions.circleCropTransform()).into(mImageProfile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void saveCaptureImage() {
        if (!mGetImageUrl.equals("") && mGetImageUrl != null) {
            StorageReference avatarRef = FirebaseStorage.getInstance().getReference("Users").child(mUid).child("avatar.jpg");
            avatarRef.putFile(mFileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    String avatarUrl = downloadUri.toString();
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(mUid).child("avatar").setValue(avatarUrl);
                }
            });
        }
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
