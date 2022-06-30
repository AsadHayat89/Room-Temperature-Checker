package com.example.temparaturechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class popRating extends AppCompatActivity {

    private Button submit;
    private RatingBar Rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_rating);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int w=dm.widthPixels;
        int he=dm.heightPixels;
        getWindow().setLayout((int)(w*.8),(int)(w*.6));

        submit=findViewById(R.id.btnaddrating);
        Rb=findViewById(R.id.RAratingBar);

        Rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ratin=(int) rating;
                
                Toast.makeText(popRating.this,"Thanks for response",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(popRating.this,MainActivity.class);
                startActivity(i);

            }
        });
    }
}