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

public class JoinQuizzActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_quizz);

        //Recuperation of intent
        Intent intent = getIntent();

        setTitle(getString(R.string.text_join_quiz));

        Button buttonGoToQuiz  = findViewById(R.id.button_go_quiz);
        buttonGoToQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intentGoToQuiz = new Intent(JoinQuizActivity.this, .class);
                //JoinQuizActivity.this.startActivity(intentGoToQuiz);
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
            //Intent goToCreate = new Intent(this, .class);
            //this.startActivity(goToCreate);
        } else if (id == R.id.profile) {
            //Intent goToProfile = new Intent(this, .class);
            //this.startActivity(goToProfile);
        } else if (id == R.id.displayquizz) {
            //Intent goToDisplayQuizz = new Intent(this, .class);
            //this.startActivity(goToDisplayQuizz);
        } else if (id == R.id.logout) {
            Intent logOut = new Intent(this, MainActivity.class);
            this.startActivity(logOut);
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



