package com.example.healthcare;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {
        EditText edUserName, edEmail, edPassword, edConfrim;
        Button btn;
        TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edUserName = findViewById(R.id.editTextRegUsername);
        edEmail = findViewById(R.id.editTextRegEmail);
        edConfrim = findViewById(R.id.editTextRegConfirmPassword);
        edPassword = findViewById(R.id.editTextRegPassword);
        btn = findViewById(R.id.Registerbtn);
        tv = findViewById(R.id.textViewHaveAcc);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, loginActivity2.class));
            }
        });
        btn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUserName.getText().toString();
                String password = edPassword.getText().toString();
                String email = edEmail.getText().toString();
                String confirm = edConfrim.getText().toString();
                Database db  = new Database(getApplicationContext(),"healthcare",null,1);
                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill all details", Toast.LENGTH_SHORT).show();

                } else {
                    if (password.compareTo(confirm) == 0) {
                        if(isValid(password)){
                            db.register(username,email,password);
                            Toast.makeText(getApplicationContext(), "Record Inserted Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, loginActivity2.class));
                        }
                    }

                    else {
                        Toast.makeText(getApplicationContext(), "Password and Confrim Password didn't match", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        }));

    }
        public  static  boolean isValid(String passwordhere){
        int f1=0,f2=0,f3=0;
        if(passwordhere.length()<0){
            return false;
        }else {
            for (int p=0;p<passwordhere.length();p++)
            {
                if(Character.isLetter(passwordhere.charAt(p))){
                    f1=1;
                }
        }
            for (int r=0;r<passwordhere.length();r++)
            {
                if(Character.isDigit(passwordhere.charAt(r))){
                    f2=1;
                }
            }
            for (int s=0;s<passwordhere.length();s++)
            {

                    char c = passwordhere.charAt(s);
                    if (c>=33&&c<=46||c==64) {
                        f3 = 1;
                    }
                }
            if(f1==1 && f2==1 && f3==1){
                return  true;
            }
            else{
                return  false;
            }
            }

        }

}