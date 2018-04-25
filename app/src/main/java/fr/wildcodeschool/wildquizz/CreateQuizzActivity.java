package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class CreateQuizzActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    FirebaseDatabase mDatabase;
    DatabaseReference mQuizzRef;
    private TextView mTvQcmNameValue;
    private EditText mEditQcmNameValue;
    private TextView mTvQuestionValue;
    private EditText mEditQuestionValue;
    private TextView mTvAnswer1Value;
    private EditText mEditAnswer1Value;
    private TextView mTvAnswer2Value;
    private EditText mEditAnswer2Value;
    private TextView mTvAnswer3Value;
    private EditText mEditAnswer3Value;
    private TextView mTvAnswer4Value;
    private EditText mEditAnswer4Value;
    private Button mButtonUpdate;
    private Button mButtonDelete;
    private ListView mListqcmValue;
    String mIdQuizz;
    private FirebaseAuth mAuth;
    private ImageView mAvatar;
    private String mUid;
    private TextView mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quizz);

        setTitle(getString(R.string.title_create_quizz));

        mDatabase = FirebaseDatabase.getInstance();
        mQuizzRef = mDatabase.getReference("Quizz");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        mIdQuizz = intent.getStringExtra("idQuizz");

        if (mIdQuizz == null) {
            //récupérer les données du menu pour la génération de quizz
            String key1 = "123456789";
            String key2 = "abcdefghijklmnopqrstuvwxyz";
            mIdQuizz = String.format("%s%s%s", generateString(3, key1), generateString(2, key2), generateString(3, key1));

            // créer le quizz vide dans Firebase
            QuizzModel quizzModel = new QuizzModel(mIdQuizz, new Date().getTime(), new HashMap<String, QcmModel>(), false);
            database.getReference("Quizz").child(mIdQuizz).setValue(quizzModel);
        }

        TextView tvIdQuizz = findViewById(R.id.tv_id_generate);
        tvIdQuizz.setText(mIdQuizz);
        // TODO V2 : vérifier que l'id unique n'existe pas déjà, si c'est le cas, le regénérer

        FloatingActionButton addQcm = findViewById(R.id.floating_add_qcm);
        addQcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCreateQcm = new Intent(CreateQuizzActivity.this, CreateQcmActivity.class);
                goToCreateQcm.putExtra("idQuizz", mIdQuizz);
                CreateQuizzActivity.this.startActivity(goToCreateQcm);
            }
        });

        // charger la liste des QCM du Quizz à partir de Firebase
        final ArrayList<QcmModel> qcmModels = new ArrayList<>();
        final QcmAdapter adapter = new QcmAdapter(this, 0, qcmModels);
        ListView lvListRoom = findViewById(R.id.list_qcm);
        lvListRoom.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance();
        mQuizzRef = mDatabase.getReference("Quizz").child(mIdQuizz).child("qcmList");
        // Read from the database
        mQuizzRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                qcmModels.clear(); // vide la liste par précaution
                for (DataSnapshot qcmSnapshot : dataSnapshot.getChildren()) {
                    QcmModel qcmModel = qcmSnapshot.getValue(QcmModel.class);
                    qcmModels.add(qcmModel);
                }
                adapter.notifyDataSetChanged(); // met au courant l'adapter que la liste a changé
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        lvListRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                QcmModel qcmModel = qcmModels.get(i);
                showUpdateDialog(qcmModel);

            }

        });
        /*lvListRoom.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                QcmModel qcmModel = qcmModels.get(i);
                showUpdateDialog(qcmModel.getTheme(), qcmModel.getQuestion(), qcmModel.getAnswer1(), qcmModel.getAnswer2(), qcmModel.getAnswer3(), qcmModel.getAnswer4());

                return false;
            }
        });*/




        //Navigation Drawer :
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_create);
        mToggle = new ActionBarDrawerToggle(CreateQuizzActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Navigation View :
        NavigationView navigationView = findViewById(R.id.nav_view_create);
        navigationView.setNavigationItemSelectedListener(this);

        //Affichage du profil dans la nav bar :
        View headerLayout = navigationView.getHeaderView(0);
        mDatabase = FirebaseDatabase.getInstance();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAvatar = headerLayout.findViewById(R.id.image_header);
        mUsername = headerLayout.findViewById(R.id.text_username);
        //TODO : faire pareil pour le score

        DatabaseReference pathID = mDatabase.getReference("Users").child(mUid);
        pathID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("avatar").getValue() != null)){
                    String url = dataSnapshot.child("avatar").getValue(String.class);
                    Glide.with(CreateQuizzActivity.this).load(url).apply(RequestOptions.circleCropTransform()).into(mAvatar);
                }
                if ((dataSnapshot.child("Name").getValue() != null)){
                    String username = dataSnapshot.child("Name").getValue(String.class);
                    mUsername.setText(username);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



    }



    //String 1 composé de 3 chiffres
    private String generateString(int length, String key) {
        //char[] chars = "abcdefghijklmnopqrstuvwxyz123456789".toCharArray();
        char[] char1 = key.toCharArray();
        StringBuilder stringBuilder1 = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = char1[random.nextInt(char1.length)];
            stringBuilder1.append(c);
        }
        return stringBuilder1.toString();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.home) {
            Intent goToHome = new Intent(this, MenuActivity.class);
            this.startActivity(goToHome);
        } else if (id == R.id.join) {
            Intent goToJoin = new Intent(this, JoinQuizzActivity.class);
            this.startActivity(goToJoin);
        } else if (id == R.id.create) {
            Intent goToCreate = new Intent(this, CreateQuizzActivity.class);
            this.startActivity(goToCreate);
        } else if (id == R.id.profile) {
            Intent goToProfile = new Intent(this, ProfileActivity.class);
            this.startActivity(goToProfile);
        } else if (id == R.id.displayquizz) {
            Intent goToDisplayQuizz = new Intent(this, DisplayQuizzActivity.class);
            this.startActivity(goToDisplayQuizz);
        } else if (id == R.id.logout) {
            //Déconnexion
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateQcm(final String qcm, final String ask, final String ans1, final String ans2, final String ans3, final String ans4){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Quizz");

        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot quizz : dataSnapshot.getChildren()) {
                    QcmModel qcmModel = quizz.getValue(QcmModel.class);
                    qcmModel.setTheme(qcm);
                    qcmModel.setQuestion(ask);
                    qcmModel.setAnswer1(ans1);
                    qcmModel.setAnswer2(ans2);
                    qcmModel.setAnswer3(ans3);
                    qcmModel.setAnswer4(ans4);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        QcmModel qcmModel = new QcmModel();

        //databaseReference.setValue();

        Toast.makeText(this, R.string.updated_qcm, Toast.LENGTH_SHORT).show();

    }


    private void showUpdateDialog(final QcmModel qcmModel) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);


        final EditText mEditQcmNameValue = dialogView.findViewById(R.id.edit_qcm);
        mEditQcmNameValue.setText(qcmModel.getTheme());
        final EditText mEditQuestionValue = dialogView.findViewById(R.id.edit_question);
        mEditQuestionValue.setText(qcmModel.getQuestion());
        final EditText mEditAnswer1Value =  dialogView.findViewById(R.id.edit_answer1);
        mEditAnswer1Value.setText(qcmModel.getAnswer1());
        final EditText mEditAnswer2Value =  dialogView.findViewById(R.id.edit_answer_2);
        mEditAnswer2Value.setText(qcmModel.getAnswer2());
        final EditText mEditAnswer3Value = dialogView.findViewById(R.id.edit_answer_3);
        mEditAnswer3Value.setText(qcmModel.getAnswer3());
        final EditText mEditAnswer4Value = dialogView.findViewById(R.id.edit_answer4);
        mEditAnswer4Value.setText(qcmModel.getAnswer4());
        Button mButtonUpdate = dialogView.findViewById(R.id.button_update);
        Button mButtonDelete = dialogView.findViewById(R.id.button_delete);

        dialogBuilder.setTitle(R.string.edit_qcm);





        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO mettre à jour dans Firebase


                String qcm = mEditQcmNameValue.getText().toString();
                String ask = mEditQuestionValue.getText().toString();
                String ans1 = mEditAnswer1Value.getText().toString();
                String ans2 = mEditAnswer2Value.getText().toString();
                String ans3 = mEditAnswer3Value.getText().toString();
                String ans4 = mEditAnswer4Value.getText().toString();
                int correctAnswer = 1;//TODO récupérer le numéro de la réponse correcte

                updateQcm(qcm,ask,ans1,ans2,ans3,ans4);
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Supprimer dans Firebase



            }
        });



        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();





    }

    private void addQcmToDB(String s) {
    }
    private boolean updateQcm() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Quizz");

        QcmModel qcmModel = new QcmModel();

        databaseReference.setValue(qcmModel);

        Toast.makeText(this, R.string.qcm_update_success, Toast.LENGTH_LONG).show();

        return true;
    }




}

