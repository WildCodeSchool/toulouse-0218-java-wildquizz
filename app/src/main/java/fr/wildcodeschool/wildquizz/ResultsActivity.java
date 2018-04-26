package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private TextView scoreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        //Navigation Drawer :
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_results);
        mToggle = new ActionBarDrawerToggle(ResultsActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Navigation View :
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_results);
        navigationView.setNavigationItemSelectedListener(this);

        //TODO: récupérer les infos :

        scoreValue = findViewById(R.id.text_score_value);
        int[] score = getIntent().getIntArrayExtra("scores");
        int scoreTotalQuizz = ScoreClass.foundQuizzScore(score);



        ArrayList<ResultsModel> resultsModelArrayList = new ArrayList<>();
        resultsModelArrayList.add(new ResultsModel("aaa","bbb",R.drawable.logo_check1));
        resultsModelArrayList.add(new ResultsModel("ccc","ddd",R.drawable.logo_cancel2,"fff"));


        ResultsAdapter resultsAdapter = new ResultsAdapter(this, resultsModelArrayList);

        ListView studentsListView
                = (ListView) findViewById(R.id.list_results);

        studentsListView.setAdapter(resultsAdapter);

        //Affichage du profil dans la nav bar :
        View headerLayout = navigationView.getHeaderView(0);
        mDatabase = FirebaseDatabase.getInstance();
        mUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mAvatar = headerLayout.findViewById(R.id.image_header);
        //TODO : faire pareil pour le pseudo
        DatabaseReference pathID = mDatabase.getReference("Users").child(mUid);
        pathID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((dataSnapshot.child("avatar").getValue() != null)){
                    String url = dataSnapshot.child("avatar").getValue(String.class);
                    Glide.with(ResultsActivity.this).load(url).apply(RequestOptions.circleCropTransform()).into(mAvatar);
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
        } else if (id == R.id.quizz_list) {
                Intent goToListQuizzActivity = new Intent(this, DisplayQuizzActivity.class);
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
