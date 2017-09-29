package com.myapp.rishabhrawat.womensafety;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class UserIDActivity extends AppCompatActivity {

    TextView t1,t2,t3,t4,t5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_id);

        t1=(TextView)findViewById(R.id.idtext);
        t2=(TextView)findViewById(R.id.nametext);
        t3=(TextView)findViewById(R.id.phonetext);
        t4=(TextView)findViewById(R.id.gmailtext);
        t5=(TextView)findViewById(R.id.addresstext);

        SharedPreferenceClass sharedPreferenceClass=new SharedPreferenceClass(UserIDActivity.this);

        t1.setText(String.format("ID : "+sharedPreferenceClass.getRandomstring()));
        t2.setText(String.format("NAME : "+sharedPreferenceClass.getName()));
        t3.setText(String.format("MOBILE NO : "+sharedPreferenceClass.getPhone()));
        t4.setText(String.format("EMAIL ID : "+sharedPreferenceClass.getEmail()));
        t5.setText(String.format("ADDRESS : "+sharedPreferenceClass.getAddress()));
    }
}
