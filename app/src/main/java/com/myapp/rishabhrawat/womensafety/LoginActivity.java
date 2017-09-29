package com.myapp.rishabhrawat.womensafety;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText name,gmail,phone,address;

    Button loginbtn;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name=(EditText)findViewById(R.id.nametext);
        gmail=(EditText)findViewById(R.id.mail);
        phone=(EditText)findViewById(R.id.mobiletext);
        address=(EditText)findViewById(R.id.address);
        loginbtn=(Button)findViewById(R.id.loginbutton);


    }

    public void LoginButtonMethod(View view) {

        SharedPreferenceClass preferenceclass=new SharedPreferenceClass(LoginActivity.this);

        if(!preferenceclass.getupdate()) {
            if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(gmail.getText().toString()) || TextUtils.isEmpty(phone.getText().toString()) || TextUtils.isEmpty(address.getText().toString())) {
                Toast.makeText(this, "Please enter all details..", Toast.LENGTH_SHORT).show();
            } else {

                //show the progress bar
                ShowProgressBar();

                String uname = name.getText().toString();
                String ugmail = gmail.getText().toString();
                String uphone = phone.getText().toString();
                String uaddress = address.getText().toString();
                String randomstr = RandomStringGenerator.generateRandomString();


                SharedPreferenceClass mypreference = new SharedPreferenceClass(LoginActivity.this);
                mypreference.LoginPreferenceSavedData(this, uname, ugmail, uphone, uaddress, randomstr);
                dialog.dismiss();
                Toast.makeText(this, "Successfully information stored in local storage", Toast.LENGTH_SHORT).show();


                SharedPreferenceClass sharedPreferenceClass = new SharedPreferenceClass(LoginActivity.this);
                if (!sharedPreferenceClass.getFlag()) {

                    MAKE_A_TWO_NODE_IN_CLOUD(randomstr);

                    sharedPreferenceClass.setFlag(true);
                    Intent intent = new Intent(LoginActivity.this, MainNavigationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainNavigationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                    sharedPreferenceClass.setFlag(true);
                }
            }
        }

        else {
            if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(gmail.getText().toString()) || TextUtils.isEmpty(phone.getText().toString()) || TextUtils.isEmpty(address.getText().toString())) {
                Toast.makeText(this, "Please enter all details..", Toast.LENGTH_SHORT).show();
            } else {

                //show the progress bar
                ShowProgressBar();

                String uname = name.getText().toString();
                String ugmail = gmail.getText().toString();
                String uphone = phone.getText().toString();
                String uaddress = address.getText().toString();

                SharedPreferenceClass preclass = new SharedPreferenceClass(LoginActivity.this);
                String random = preclass.getRandomstring();

                preclass.LoginPreferenceSavedData(this, uname, ugmail, uphone, uaddress, random);
                dialog.dismiss();
                Toast.makeText(this, "Successfully information updated in local storage", Toast.LENGTH_SHORT).show();

                preclass.setupdate(false);
                Intent intent = new Intent(LoginActivity.this, MainNavigationActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        }
    }

    private void MAKE_A_TWO_NODE_IN_CLOUD(String randomstr) {
        DatabaseReference locationroot= FirebaseDatabase.getInstance().getReference().child("LOCATION").child(randomstr);
        locationroot.setValue(" ");
        DatabaseReference notificationroot=FirebaseDatabase.getInstance().getReference().child("NOTIFICATION").child(randomstr);
        notificationroot.setValue(" ");
    }



    private void ShowProgressBar() {
        dialog=new ProgressDialog(LoginActivity.this);
        dialog.setTitle("Please Wait..");
        dialog.setMessage("Your Information stored in Local Storage...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
        try
        {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex)
        {
            Toast.makeText(this, "Something goes wrong please try again later...", Toast.LENGTH_SHORT).show();
        }
    }
}
