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
import android.widget.TextView;

import java.util.Random;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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
    }

    //Importation de la fonctionnalité de génération de l'id
    private String generateString1(int length) {
        //char[] chars = "abcdefghijklmnopqrstuvwxyz123456789".toCharArray();
        char[] char1 = "123456789".toCharArray();
        StringBuilder stringBuilder1 = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            char c = char1[random.nextInt(char1.length)];
            stringBuilder1.append(c);
        }
        return stringBuilder1.toString();
    }
    private String generateString2(int length) {
        char[] char2 = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder stringBuilder2 = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            char c = char2[random.nextInt(char2.length)];
            stringBuilder2.append(c);
        }
        return stringBuilder2.toString();
    }
    private String generateString3(int length) {
        char[] char3 = "123456789".toCharArray();
        StringBuilder stringBuilder3 = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            char c = char3[random.nextInt(char3.length)];
            stringBuilder3.append(c);
        }
        return stringBuilder3.toString();
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
