package com.example.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {
    private String[][] doctor_details1 =
            {
                    {"Doctor Name : Ajit Saste", "Hospital Address : Pimpri", "Exp : 5yrs", "Mobile No. : 7878787878", "680"},
                    {"Doctor Name : Prasad Pawar", "Hospital Address : Pune", "Exp : 15yrs", "Mobile No. : 9090909090", "900"},
                    {"Doctor Name : Swapnil Kale", "Hospital Address : Chinchwad", "Exp : 8yrs", "Mobile No. : 7171717171", "500"},
                    {"Doctor Name : Ashok Joshi", "Hospital Address : Kolhapur", "Exp : 7yrs", "Mobile No. : 8282828282", "800"},

            };
    private String[][] doctor_details2 =
            {
                    {"Doctor Name : Sneha Mhatre", "Hospital Address : Pimpri", "Exp : 5yrs", "Mobile No. : 7878787878", "680"},
                    {"Doctor Name : Rutuja Pawar", "Hospital Address : Pune", "Exp : 15yrs", "Mobile No. : 9090909090", "900"},
                    {"Doctor Name : Swarali Kale", "Hospital Address : Chinchwad", "Exp : 8yrs", "Mobile No. : 7171717171", "500"},
                    {"Doctor Name : Siya Joshi", "Hospital Address : Kolhapur", "Exp : 7yrs", "Mobile No. : 8282828282", "800"},

            };
    private String[][] doctor_details3 =
            {
                    {"Doctor Name : Seema Patil", "Hospital Address : Pimpri", "Exp : 5yrs", "Mobile No. : 7878787878", "680"},
                    {"Doctor Name : Riya Rajput", "Hospital Address : Pune", "Exp : 15yrs", "Mobile No. : 9090909090", "900"},
                    {"Doctor Name : Shreya Sharma", "Hospital Address : Chinchwad", "Exp : 8yrs", "Mobile No. : 7171717171", "500"},
                    {"Doctor Name : Neha Suryavanshi", "Hospital Address : Kolhapur", "Exp : 7yrs", "Mobile No. : 8282828282", "800"},

            };
    private String[][] doctor_details4 =
            {
                    {"Doctor Name : Kalyani Patil", "Hospital Address : Pimpri", "Exp : 5yrs", "Mobile No. : 7878787878", "680"},
                    {"Doctor Name : Ram Suryavanshi", "Hospital Address : Pune", "Exp : 15yrs", "Mobile No. : 9090909090", "900"},
                    {"Doctor Name : Laxman Kale", "Hospital Address : Chinchwad", "Exp : 8yrs", "Mobile No. : 7171717171", "500"},
                    {"Doctor Name : Ashok Patel", "Hospital Address : Kolhapur", "Exp : 7yrs", "Mobile No. : 8282828282", "800"},

            };
    private String[][] doctor_details5 =
            {
                    {"Doctor Name : Sarasvati Kapoor", "Hospital Address : Pimpri", "Exp : 5yrs", "Mobile No. : 7878787878", "680"},
                    {"Doctor Name : Prasad Japtap", "Hospital Address : Pune", "Exp : 15yrs", "Mobile No. : 9090909090", "900"},
                    {"Doctor Name : Raj Sharma", "Hospital Address : Chinchwad", "Exp : 8yrs", "Mobile No. : 7171717171", "500"},
                    {"Doctor Name : Maya Mohite", "Hospital Address : Kolhapur", "Exp : 7yrs", "Mobile No. : 8282828282", "800"},

            };
    TextView tv;
    Button btn;
    HashMap<String, String> item;
    String[][] doctor_details = {};
    ArrayList list;
    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        tv = findViewById(R.id.textView_DDtitle);
        btn = findViewById(R.id.buttonDD_back);

        Intent it = getIntent();
        String title = it.getStringExtra("title");
        tv.setText(title);

        if (title.compareTo("Family Physicians") == 0){
            doctor_details = doctor_details1;}

        else
            if (title.compareTo("Dietician") == 0){
            doctor_details = doctor_details2;}

        else
            if (title.compareTo("surgeon") == 0){
            doctor_details = doctor_details3;}

        else
            if (title.compareTo("dentist") == 0){
            doctor_details = doctor_details4;}

        else {
            doctor_details = doctor_details5;}


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));

            }
        });

        list = new ArrayList();
        for (int i = 0; i < doctor_details.length; i++)
        {
            item = new HashMap<String, String>();
            item.put( "line1",doctor_details[i][0]);
            item.put( "line2",doctor_details[i][1]);
            item.put( "line3",doctor_details[i][2]);
            item.put( "line4",doctor_details[i][3]);
            item.put( "line5","Cons Fees : "+doctor_details[i][4]+"/-");
            list.add(item);

        }
        sa = new SimpleAdapter(this,list,
                R.layout.multi_lines,
                new String[]{"line1","line2","line3","line4","line5"},
                new int[] {R.id.line_a,R.id.line_b,R.id.line_c,R.id.line_d,R.id.line_e});
        ListView lst = findViewById(R.id.listViewDDDetails);
        lst.setAdapter(sa);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(DoctorDetailsActivity.this,BookAppointmentActivity.class);
                it.putExtra("text1",title);
                it.putExtra("text2",doctor_details[i][0]);
                it.putExtra("text3",doctor_details[i][1]);
                it.putExtra("text4",doctor_details[i][3]);
                it.putExtra("text5",doctor_details[i][4]);
                startActivity(it);
            }
        });
    }
}