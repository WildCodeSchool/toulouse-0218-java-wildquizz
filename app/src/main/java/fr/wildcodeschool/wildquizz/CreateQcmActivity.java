package fr.wildcodeschool.wildquizz;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateQcmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qcm);

        Intent recupMenu = getIntent();

        FloatingActionButton addQcm = findViewById(R.id.floating_add_qcm);
        addQcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCreateQuizz = new Intent(CreateQcmActivity.this, CreateQuizzActivity.class);
                CreateQcmActivity.this.startActivity(goToCreateQuizz);;


            }
        });



    }
}
