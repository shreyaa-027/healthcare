package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HealthArticlesDetailsActivity extends AppCompatActivity {
    TextView tv1;
    ImageView img;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_articles_details);

        tv1=findViewById(R.id.textViewHAD);
        btnBack=findViewById(R.id.buttonHADBack);
        img=findViewById(R.id.imageView);

        Intent intent = getIntent();
        tv1.setText(intent.getStringExtra("text1"));

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            int resId = bundle.getInt("text2");
            int resId2 = bundle.getInt("text3");
            int resId3 = bundle.getInt("text4");
            img.setImageResource(resId);
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HealthArticlesDetailsActivity.this, HealthArticleActivity.class));
            }
        });

    }
}