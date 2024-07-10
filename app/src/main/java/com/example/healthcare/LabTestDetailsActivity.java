package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LabTestDetailsActivity extends AppCompatActivity {
    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
    Button btnAddtoCart, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);
        tvPackageName = findViewById(R.id.textViewLTPackageName);
        tvTotalCost  = findViewById(R.id.textViewTotalCost);
        edDetails =   findViewById(R.id.editTextTextMultiLine);
        btnBack =   findViewById(R.id.buttonLT_back);
        btnAddtoCart=   findViewById(R.id.buttonLTGotoCart);

        edDetails.setKeyListener(null);

        Intent intent=getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText(intent.getStringExtra("text3")+ "/-");

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class));
            }
        });
        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username","").toString();
            String product = tvPackageName.getText().toString();
            float price = Float.parseFloat(intent.getStringExtra("text3").toString());

            Database db = new Database(getApplicationContext(),"healtlhcare",null,1);

            if(db.checkCart(username,product)==1){
                Toast.makeText(getApplicationContext(),"Product Already Added",Toast.LENGTH_SHORT).show();;

            }else {
                db.addCart(username,product,price,"lab");
                Toast.makeText(getApplicationContext(),"Record Inserted to Cart",Toast.LENGTH_SHORT).show();;
                startActivity((new Intent(LabTestDetailsActivity.this,LabTestActivity.class)));
            }

            }
        });
    }
}