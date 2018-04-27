package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class CreateQuizzActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase mDatabase;
    DatabaseReference mQuizzRef;
    String mIdQuizz;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
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
    private FirebaseAuth mAuth;
    private ImageView mAvatar;
    private String mUid;
    private TextView mUsername;
    private TextView mScoreValue;
    private QcmAdapter mQcmAdapter;
    private int mCount = 0;

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
            String key2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
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
                // TODO : vérifier le nombre d'item dans l'adapter
                if (mQcmAdapter.getCount() >= 5) {
                    Toast.makeText(CreateQuizzActivity.this, R.string.limit_create_quizz, Toast.LENGTH_SHORT).show();
                } else {
                    Intent goToCreateQcm = new Intent(CreateQuizzActivity.this, CreateQcmActivity.class);
                    goToCreateQcm.putExtra("idQuizz", mIdQuizz);
                    CreateQuizzActivity.this.startActivity(goToCreateQcm);
                }

            }
        });
        // charger la liste des QCM du Quizz à partir de Firebase
        final ArrayList<QcmModel> qcmModels = new ArrayList<>();
        mQcmAdapter = new QcmAdapter(this, 0, qcmModels);
        ListView lvListRoom = findViewById(R.id.list_qcm);
        lvListRoom.setAdapter(mQcmAdapter);

        mDatabase = FirebaseDatabase.getInstance();
        mQuizzRef = mDatabase.getReference("Quizz").child(mIdQuizz).child("qcmList");
        // Read from the database
        mQuizzRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                qcmModels.clear(); // vide la liste par précaution
                for (DataSnapshot qcmSnapshot : dataSnapshot.getChildren()) {
                    String qcmId = qcmSnapshot.getKey();
                    QcmModel qcmModel = qcmSnapshot.getValue(QcmModel.class);
                    qcmModel.setQcmId(qcmId);
                    qcmModels.add(qcmModel);

                }
                mQcmAdapter.notifyDataSetChanged(); // met au courant l'adapter que la liste a changé
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
        mScoreValue = headerLayout.findViewById(R.id.text_score_value);
        //TODO : faire pareil pour le score

        DatabaseReference pathID = mDatabase.getReference("Users").child(mUid);
        pathID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("avatar").getValue() != null)) {
                    String url = dataSnapshot.child("avatar").getValue(String.class);
                    Glide.with(CreateQuizzActivity.this).load(url).apply(RequestOptions.circleCropTransform()).into(mAvatar);
                }
                if ((dataSnapshot.child("Name").getValue() != null)) {
                    String username = dataSnapshot.child("Name").getValue(String.class);
                    mUsername.setText(username);
                }
                //For Score
                if ((dataSnapshot.child("score").getValue() != null)) {
                    String score = String.valueOf(dataSnapshot.child("score").getValue(int.class));
                    mScoreValue.setText(score);
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


    private void showUpdateDialog(final QcmModel qcmModel) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);


        final EditText mEditQcmNameValue = dialogView.findViewById(R.id.edit_qcm);
        mEditQcmNameValue.setText(qcmModel.getTheme());
        final EditText mEditQuestionValue = dialogView.findViewById(R.id.edit_question);
        mEditQuestionValue.setText(qcmModel.getQuestion());
        final EditText mEditAnswer1Value = dialogView.findViewById(R.id.edit_answer1);
        mEditAnswer1Value.setText(qcmModel.getAnswer1());
        final EditText mEditAnswer2Value = dialogView.findViewById(R.id.edit_answer_2);
        mEditAnswer2Value.setText(qcmModel.getAnswer2());
        final EditText mEditAnswer3Value = dialogView.findViewById(R.id.edit_answer_3);
        mEditAnswer3Value.setText(qcmModel.getAnswer3());
        final EditText mEditAnswer4Value = dialogView.findViewById(R.id.edit_answer4);
        mEditAnswer4Value.setText(qcmModel.getAnswer4());
        Button mButtonUpdate = dialogView.findViewById(R.id.button_update);
        final Button mButtonDelete = dialogView.findViewById(R.id.button_delete);
        final RadioGroup radioGroup = dialogView.findViewById(R.id.radiogroup);


        switch (qcmModel.getCorrectAnswer()) {

            case 1:
                RadioButton button1 = dialogView.findViewById(R.id.radiobtn_1);
                button1.setChecked(true);
                break;

            case 2:
                RadioButton button2 = dialogView.findViewById(R.id.radiobtn_2);
                button2.setChecked(true);
                break;

            case 3:
                RadioButton button3 = dialogView.findViewById(R.id.radiobtn_3);
                button3.setChecked(true);
                break;
            case 4:
                RadioButton button4 = dialogView.findViewById(R.id.radiobtn_4);
                button4.setChecked(true);
                break;

        }

        dialogBuilder.setTitle(R.string.edit_qcm);
        final AlertDialog alertDialog = dialogBuilder.create();


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Quizz").child(mIdQuizz).child("qcmList").child(qcmModel.getQcmId());

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO mettre à jour dans Firebase


                String theme = mEditQcmNameValue.getText().toString();
                String question = mEditQuestionValue.getText().toString();
                String ans1 = mEditAnswer1Value.getText().toString();
                String ans2 = mEditAnswer2Value.getText().toString();
                String ans3 = mEditAnswer3Value.getText().toString();
                String ans4 = mEditAnswer4Value.getText().toString();
                int correctAnswer = 1;//TODO récupérer le numéro de la réponse correcte

                int i = radioGroup.getCheckedRadioButtonId();
                switch (i) {
                    case R.id.radiobtn_1:
                        correctAnswer = 1;
                        break;
                    case R.id.radiobtn_2:
                        correctAnswer = 2;
                        break;
                    case R.id.radiobtn_3:
                        correctAnswer = 3;
                        break;
                    case R.id.radiobtn_4:
                        correctAnswer = 4;
                        break;
                }


                if (theme.isEmpty() || question.isEmpty() || ans1.isEmpty() ||
                        ans2.isEmpty() || ans3.isEmpty() ||
                        ans4.isEmpty()) {

                    Toast.makeText(CreateQuizzActivity.this, R.string.fill_all, Toast.LENGTH_SHORT).show();
                } else {
                    qcmModel.setTheme(theme);
                    qcmModel.setQuestion(question);
                    qcmModel.setAnswer1(ans1);
                    qcmModel.setAnswer2(ans2);
                    qcmModel.setAnswer3(ans3);
                    qcmModel.setAnswer4(ans4);
                    qcmModel.setCorrectAnswer(correctAnswer);
                    databaseReference.setValue(qcmModel);

                    Toast.makeText(CreateQuizzActivity.this, R.string.updated_qcm, Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    mQcmAdapter.notifyDataSetChanged();
                }
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Supprimer dans Firebase
                databaseReference.removeValue();
                alertDialog.dismiss();
                mQcmAdapter.notifyDataSetChanged();

                Toast.makeText(CreateQuizzActivity.this, R.string.suppression_qcm, Toast.LENGTH_SHORT).show();


            }
        });


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

