package com.example.temparaturechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class setting extends AppCompatActivity {
    ImageButton rat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        rat=findViewById(R.id.ratee);
        rat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(setting.this, popRating.class);
                startActivity(i);
            }
        });
    }
}