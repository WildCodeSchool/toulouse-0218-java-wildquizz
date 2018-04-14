package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class CreateQuizzActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    public TextView idQuizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quizz);

        //récupérer les données du menu pour la génération de quizz
        idQuizz = findViewById(R.id.tv_id_generate);
        Intent recupCreationQuizz = getIntent();
        idQuizz.setText(generateString1(3)+generateString2(2)+generateString3(3));



        Intent recupMenu = getIntent();
        FloatingActionButton addQcm = findViewById(R.id.floating_add_qcm);
        addQcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCreateQcm = new Intent(CreateQuizzActivity.this, CreateQcmActivity.class);
                CreateQuizzActivity.this.startActivity(goToCreateQcm);;
            }
        });

        ArrayList<QcmModel> qcmModels = loadQcmsFromDB();
        QcmAdapter adapter = new QcmAdapter(this, 0, qcmModels);
        ListView lvListRoom = findViewById(R.id.list_qcm);
        lvListRoom.setAdapter(adapter);

        //Navigation Drawer :
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_create);
        mToggle = new ActionBarDrawerToggle(CreateQuizzActivity.this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Navigation View :
        NavigationView navigationView = findViewById(R.id.nav_view_create);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //String 1 composé de 3 chiffres
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
    //String 2 composé de 2 lettres
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
    //String 3 composé de 3 chiffres
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

    private ArrayList<QcmModel> loadQcmsFromDB() {
        ArrayList<QcmModel> qcmModels = new ArrayList<>();
        DbHelper mDbHelper = new DbHelper(CreateQuizzActivity.this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                DbContract.QcmEntry._ID,
                DbContract.QcmEntry.COLUMN_NAME_QCM,
        };
        Cursor cursor = db.query(
                DbContract.QcmEntry.TABLE_NAME,
                projection,
                null, null, null, null, null
        );

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.QcmEntry._ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.QcmEntry.COLUMN_NAME_QCM));
            QcmModel qcmModel = new QcmModel(id,name);
            qcmModels.add(qcmModel);
        }
        cursor.close();

        return qcmModels;
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

