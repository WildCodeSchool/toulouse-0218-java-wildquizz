package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class JoinQuizzActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mQuizzRef;
    private ImageView mAvatar;
    private String mUid;
    private TextView mUsername;
    private TextView mScoreValue;
    private EditText mIdentifiantQuizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_quizz);

        setTitle(getString(R.string.title_join_quizz));

        mIdentifiantQuizz = findViewById(R.id.id_quiz);

        Button buttonGoToQuiz  = findViewById(R.id.button_go_quiz);
        buttonGoToQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Récupération de l'identifiant rentré par l'utilisateur :
                final String idQuizzEnter = mIdentifiantQuizz.getText().toString();

                mDatabase = FirebaseDatabase.getInstance();
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                mQuizzRef = mDatabase.getReference("Quizz");
                final DatabaseReference userRef = mDatabase.getReference("Users").child(mUid).child("quizzcreated");
                mQuizzRef.orderByChild("id").equalTo(idQuizzEnter).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //This means the value exist, you could also dataSnaphot.exist()
                        if (dataSnapshot.exists()) {

                            for (DataSnapshot children : dataSnapshot.getChildren()) {
                                database.getReference("Users").child(mUid).child("quizzPlayed").child(idQuizzEnter).setValue(new DisplayQuizzModel());
                                QuizzModel quizzModel = children.getValue(QuizzModel.class);
                                Intent goToSecondSplash = new Intent(JoinQuizzActivity.this, SplashSecondActivity.class);
                                goToSecondSplash.putExtra("id", idQuizzEnter);
                                goToSecondSplash.putExtra("nbQcm", quizzModel.getQcmList().size());
                                JoinQuizzActivity.this.startActivity(goToSecondSplash);
                                finish();
                            }

                            // TODO V2: Vérifier si l'utilisateur ne l'a pas dans les quizzs joués :

                            // TODO V2 : Vérifier si l'utilisateur n'a pas créé le quizz :
                            /*userRef.orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        if (idQuizzEnter.equals(dataSnapshot1.child("id").getValue())) {
                                            Toast.makeText(JoinQuizzActivity.this, "Vous avez déja joué à ce quizz", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(JoinQuizzActivity.this, "ok", Toast.LENGTH_SHORT).show();
                                            for (DataSnapshot children : dataSnapshot.getChildren()) {
                                                database.getReference("Users").child(mUid).child("quizzPlayed").child(idQuizzEnter).setValue(new DisplayQuizzModel());
                                                QuizzModel quizzModel = children.getValue(QuizzModel.class);
                                                Intent goToSecondSplash = new Intent(JoinQuizzActivity.this, SplashSecondActivity.class);
                                                goToSecondSplash.putExtra("id", idQuizzEnter);
                                                int nbQcm = quizzModel.getQcmList().size();
                                                goToSecondSplash.putExtra("nbQcm", nbQcm);
                                                JoinQuizzActivity.this.startActivity(goToSecondSplash);
                                                finish();
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });*/

                        } else {
                            Toast.makeText(JoinQuizzActivity.this, R.string.id_incorrect, Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        //Navigation Drawer :
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_join);
        mToggle = new ActionBarDrawerToggle(JoinQuizzActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Navigation View :
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_join);
        navigationView.setNavigationItemSelectedListener(this);

        //Affichage du profil dans la nav bar :
        View headerLayout = navigationView.getHeaderView(0);
        mDatabase = FirebaseDatabase.getInstance();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAvatar = headerLayout.findViewById(R.id.image_header);
        mUsername = headerLayout.findViewById(R.id.text_username);
        mScoreValue = headerLayout.findViewById(R.id.text_score_value);

        DatabaseReference pathID = mDatabase.getReference("Users").child(mUid);
        pathID.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("avatar").getValue() != null)) {
                    String url = dataSnapshot.child("avatar").getValue(String.class);
                    Glide.with(getApplicationContext()).load(url).apply(RequestOptions.circleCropTransform()).into(mAvatar);
                }
                if ((dataSnapshot.child("username").getValue() != null)) {
                    String username = dataSnapshot.child("username").getValue(String.class);
                    mUsername.setText(username);
                }

                //For Score
                if((dataSnapshot.child("score").getValue() !=null)) {
                    String score = String.valueOf(dataSnapshot.child("score").getValue(int.class));
                    mScoreValue.setText(score);
                }
            }
            
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
        } else if (id == R.id.listquizz) {
                Intent goToListQuizz = new Intent(this, ListQuizzActivity.class);
                this.startActivity(goToListQuizz);
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


}



