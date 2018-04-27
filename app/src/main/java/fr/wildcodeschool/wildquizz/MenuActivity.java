package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ok";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    private ImageView mAvatar;
    private String mUid;
    private TextView mUsername;
    private TextView mScoreValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle(getString(R.string.title_menu_activity));

        //Navigation Drawer :
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_menu);
        mToggle = new ActionBarDrawerToggle(MenuActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button buttonGoToJoin = findViewById(R.id.button_join_quiz);
        buttonGoToJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join = new Intent(MenuActivity.this, JoinQuizzActivity.class);
                MenuActivity.this.startActivity(join);
            }
        });

        Button goToCreateQuizz = findViewById(R.id.button_create_quiz);
        goToCreateQuizz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create = new Intent(MenuActivity.this, CreateQuizzActivity.class);
                MenuActivity.this.startActivity(create);
            }
        });

        //Navigation View :
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
                //For avatar
                if ((dataSnapshot.child("avatar").getValue() != null)){
                    String url = dataSnapshot.child("avatar").getValue(String.class);
                    Glide.with(MenuActivity.this).load(url).apply(RequestOptions.circleCropTransform()).into(mAvatar);
                }
                //For Username
                if ((dataSnapshot.child("Name").getValue() != null)){
                    String username = dataSnapshot.child("Name").getValue(String.class);
                    mUsername.setText(username);
                }
                //For Score
                if ((dataSnapshot.child("score").getValue() != null)){
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
        }  else if (id == R.id.listquizz) {
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
