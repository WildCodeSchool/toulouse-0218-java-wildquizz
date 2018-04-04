package fr.wildcodeschool.wildquizz;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class CreateQcmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qcm);

        Intent recupQcm = getIntent();

        Button validateQcm = findViewById(R.id.button_val);
        validateQcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameQcm = findViewById(R.id.edit_name_qcm);
                addQcmToDB(nameQcm.getText().toString());
                Intent goToCreateQuizz = new Intent(CreateQcmActivity.this, CreateQuizzActivity.class);
                CreateQcmActivity.this.startActivity(goToCreateQuizz);

            }
        });

    }

    private void addQcmToDB(String name) {
        DbHelper mDbHelper = new DbHelper(CreateQcmActivity.this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues qcm = new ContentValues();

        qcm.put(DbContract.QcmEntry.COLUMN_NAME_QCM,name);
        long newPersonId = db.insert(DbContract.QcmEntry.TABLE_NAME, null, qcm);

    }





}
