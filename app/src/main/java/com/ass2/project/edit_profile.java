package com.ass2.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class edit_profile extends AppCompatActivity {

    TextView chngpwd;
    TextView saveprof;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        chngpwd = findViewById(R.id.idchangepwd);
        saveprof = findViewById(R.id.idsaveprofile);
        back = findViewById(R.id.idback);

        chngpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(edit_profile.this,ChnagePassword.class));
            }
        });
        saveprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(edit_profile.this,home_page.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(edit_profile.this,home_page.class));
            }
        });
    }
}