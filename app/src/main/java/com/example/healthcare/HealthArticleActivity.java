package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class HealthArticleActivity extends AppCompatActivity {

    private String[][] health_Details =
            {
                    {"Walking Daily","","","","Click Here For Details"},
                    {"Home care of COVID-19","","","","Click Here For Details"},
                    {"Stop Smoking","","","","Click Here For Details"},
                    {"Healthy Gut","","","","Click Here For Details"},
            };
    private int[] images = {
            R.drawable.health1,
            R.drawable.health2,
            R.drawable.health3,
            R.drawable.health4,
            R.drawable.health5,

    };
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;
    ListView lst;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_article);

        btnBack = findViewById(R.id.btnHABack);
        lst = findViewById(R.id.listViewHA);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HealthArticleActivity.this, HomeActivity.class));
            }
        });

        list = new ArrayList();
        for(int i=0;i<health_Details.length;i++){
            item = new HashMap<String,String>();
            item.put("line1",health_Details[i][0]);
            item.put("line2",health_Details[i][1]);
            item.put("line3",health_Details[i][2]);
            item.put("line4",health_Details[i][3]);
            item.put("line5",health_Details[i][4]);
            list.add(item);
        }
        sa = new SimpleAdapter(this,list,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[] {R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(HealthArticleActivity.this,HealthArticlesDetailsActivity.class);
                it.putExtra("text1",health_Details[i][0]);
                it.putExtra("text2",images[i]);
                startActivity(it);

            }
        });

    }
}