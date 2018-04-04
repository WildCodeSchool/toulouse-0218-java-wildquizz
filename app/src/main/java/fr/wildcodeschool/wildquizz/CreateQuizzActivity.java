package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class CreateQuizzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quizz);

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

    }

