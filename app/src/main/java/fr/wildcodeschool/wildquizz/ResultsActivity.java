package fr.wildcodeschool.wildquizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;

    private ImageView mAvatar;
    private String mUid;
    private TextView mScoreValue;
    private TextView mValueScore;
    private TextView mUsername;
    private TextView navBarScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        setTitle(getString(R.string.title_quizz_results));

        mDatabase = FirebaseDatabase.getInstance();

        //Navigation Drawer :
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_results);
        mToggle = new ActionBarDrawerToggle(ResultsActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Navigation View :
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_results);
        navigationView.setNavigationItemSelectedListener(this);

        mScoreValue = findViewById(R.id.value_score);
        mValueScore = findViewById(R.id.score_value);
        final int[] scores = getIntent().getIntArrayExtra("scores");
        final int nbQcm = getIntent().getIntExtra("nbQcm", 0);
        final int scoreTotalQuizz = ScoreClass.foundQuizzScore(scores);
        mScoreValue.setText(String.valueOf(scoreTotalQuizz));
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String idQuizz = getIntent().getStringExtra("idQuizz");

        final DatabaseReference quizzRef = mDatabase.getReference("Users").child(mUid).child("quizzPlayed").child(idQuizz);

        final DatabaseReference userRef = mDatabase.getReference("Users").child(mUid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                int score = userModel.setScore(scoreTotalQuizz + userModel.getScore());
                int nbQcmValue = userModel.setNbQcm(nbQcm + userModel.getNbQcm());
                userRef.child("score").setValue(score);
                userRef.child("nbQcm").setValue(nbQcmValue);

                float note = Math.round((float) scoreTotalQuizz/nbQcm * 2f)/2f;
                quizzRef.setValue(new DisplayQuizzModel(idQuizz, scoreTotalQuizz, note));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        final ListView listView = findViewById(R.id.list_results);

        final ArrayList<ResultsModel> resultsList = new ArrayList<>();
        final ResultsAdapter adapter = new ResultsAdapter(this,resultsList);
        listView.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference listResultsRef = mDatabase.getReference("Quizz").child(idQuizz).child("qcmList");
        listResultsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot qcmSnapshot : dataSnapshot.getChildren()) {
                    ResultsModel resultsModel = qcmSnapshot.getValue(ResultsModel.class);
                    resultsList.add(new ResultsModel(resultsModel.getQuestion(),1, scores[i]));
                   i++;
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Affichage du profil dans la nav bar :
        View headerLayout = navigationView.getHeaderView(0);
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAvatar = headerLayout.findViewById(R.id.image_header);
        mUsername = headerLayout.findViewById(R.id.text_username);
        navBarScore = headerLayout.findViewById(R.id.text_score_value);
        DatabaseReference pathID = mDatabase.getReference("Users").child(mUid);
        pathID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("avatar").getValue() != null)){
                    String url = dataSnapshot.child("avatar").getValue(String.class);
                    Glide.with(getApplicationContext()).load(url).apply(RequestOptions.circleCropTransform()).into(mAvatar);
                }
                //For Username
                if ((dataSnapshot.child("username").getValue() != null)){
                    String username = dataSnapshot.child("username").getValue(String.class);
                    mUsername.setText(username);
                }
                //For Score
                if ((dataSnapshot.child("score").getValue() != null)){
                    String score = String.valueOf(dataSnapshot.child("score").getValue(int.class));
                    navBarScore.setText(score);
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
        } /*else if (id == R.id.displayquizz) {
            Intent goToDisplayQuizz = new Intent(this, DisplayQuizzActivity.class);
            this.startActivity(goToDisplayQuizz);
        }*/ else if (id == R.id.listquizz) {
                Intent goToListQuizzActivity = new Intent(this, ListQuizzActivity.class);
                this.startActivity(goToListQuizzActivity);
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
