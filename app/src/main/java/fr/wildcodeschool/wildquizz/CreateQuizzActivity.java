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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

import static fr.wildcodeschool.wildquizz.R.layout.item_qcm;

public class CreateQuizzActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    public TextView mIdQuizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quizz);

        //récupérer les données du menu pour la génération de quizz
        mIdQuizz = findViewById(R.id.tv_id_generate);
        Intent recupCreationQuizz = getIntent();
        String key1 = "123456789";
        String key2 = "abcdefghijklmnopqrstuvwxyz";
        mIdQuizz.setText(String.format("%s%s%s", generateString(3, key1), generateString(2, key2), generateString(3, key1)));

        Intent recupMenu = getIntent();
        FloatingActionButton addQcm = findViewById(R.id.floating_add_qcm);
        addQcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCreateQcm = new Intent(CreateQuizzActivity.this, CreateQcmActivity.class);
                CreateQuizzActivity.this.startActivity(goToCreateQcm);;
            }
        });

        final ArrayList<QcmModel> qcmModels = loadQcmsFromDB();
        QcmAdapter adapter = new QcmAdapter(this, 0, qcmModels);
        ListView lvListRoom = findViewById(R.id.list_qcm);
        lvListRoom.setAdapter(adapter);


        lvListRoom.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                QcmModel qcmModel = qcmModels.get(i);
                showUpdateDialog(qcmModel.getNameQcm(),qcmModel.getQuestion(),qcmModel.getAnswer1());

                return false;
            }
        });




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

    private boolean UpdateQcm(String qcm, String ask, String ans1, String ans2, String ans3, String ans4){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Quizz").child(id);

        QcmModel qcmModel = new QcmModel();

        databaseReference.setValue(quizz);

        Toast.makeText(this, "Votre QCM a été mis à jour", Toast.LENGTH_SHORT).show();

    }


    private void showUpdateDialog(String qcm, String ask, String ans1, String ans2, String ans3, String ans4) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog,null);

        dialogBuilder.setView(dialogView);

        final TextView tvQcmNameValue = (TextView) dialogView.findViewById(R.id.text_qcm);
        final EditText editQcmNameValue = (EditText) dialogView.findViewById(R.id.edit_name_qcm);
        final TextView tvQuestionValue = (TextView) dialogView.findViewById(R.id.text_question);
        final EditText editQuestionValue = (EditText) dialogView.findViewById(R.id.edit_question);
        final TextView tvAnswer1Value = (TextView) dialogView.findViewById(R.id.tv_answer1);
        final EditText editAnswer1Value = (EditText) dialogView.findViewById(R.id.edit_answer_1);
        final TextView tvAnswer2Value = (TextView) dialogView.findViewById(R.id.tv_answer2);
        final EditText editAnswer2Value = (EditText) dialogView.findViewById(R.id.edit_answer_2);
        final TextView tvAnswer3Value = (TextView) dialogView.findViewById(R.id.tv_answer3);
        final EditText editAnswer3Value = (EditText) dialogView.findViewById(R.id.edit_answer_3);
        final TextView tvAnswer4Value = (TextView) dialogView.findViewById(R.id.tv_answer4);
        final EditText editAnswer4Value = (EditText) dialogView.findViewById(R.id.edit_answer_4);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.button_update);
        final ListView listqcmValue = (ListView) dialogView.findViewById(R.id.list_qcm);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        dialogBuilder.setTitle("Updating qcm"+ qcm);
        dialogBuilder.setTitle("Updating question"+ ask);
        dialogBuilder.setTitle("Updating answer1"+ ans1);
        dialogBuilder.setTitle("Updating answer2"+ ans2);
        dialogBuilder.setTitle("Updating answer3"+ ans3);
        dialogBuilder.setTitle("Updating answer4"+ ans4);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();





    }
}

