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

public class DisplayQuizzActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    private ImageView mAvatar;
    private String mUid;
    private TextView mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_quizz);
        setTitle(getString(R.string.title_display_quizz_played));


        ListView lvDisplay = findViewById(R.id.list_quizz);
        ArrayList<DisplayQuizzModel> results = new ArrayList<>();
        try{
            results.add(new DisplayQuizzModel(0,100,4.0, 4));
            results.add(new DisplayQuizzModel(1,200,5.0,5));
            results.add(new DisplayQuizzModel(3,600,2.0, 2.0));
            results.add(new DisplayQuizzModel(4,600,2.0, 2.0));
        } catch (Exception e) {

        }
        DisplayQuizzAdapter adapter = new DisplayQuizzAdapter(this, results);
        lvDisplay.setAdapter(adapter);


        //Navigation Drawer :
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_display_quizz);
        mToggle = new ActionBarDrawerToggle(DisplayQuizzActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Navigation View :
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_display_quizz);
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
                    Glide.with(DisplayQuizzActivity.this).load(url).apply(RequestOptions.circleCropTransform()).into(mAvatar);
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
