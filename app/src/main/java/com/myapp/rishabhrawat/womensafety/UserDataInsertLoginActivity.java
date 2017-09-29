package com.myapp.rishabhrawat.womensafety;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class UserDataInsertLoginActivity extends AppCompatActivity {


    EditText name,phone,email,address;
    Button savebutton,cancelbutton;
    ImageView imagephoto;
    private static  final int PICK_IMAGE=100;
    Uri uri;


    public SQLiteDataSave mydatasave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data_insert_login);

        name=(EditText)findViewById(R.id.nameedit);
        phone=(EditText)findViewById(R.id.phoneedit);
        email=(EditText)findViewById(R.id.mailedit);
        address=(EditText)findViewById(R.id.edithome);
        savebutton=(Button)findViewById(R.id.savebtn);
        cancelbutton=(Button)findViewById(R.id.cancelbtn);
        imagephoto=(ImageView)findViewById(R.id.image);


        mydatasave=new SQLiteDataSave(UserDataInsertLoginActivity.this);

        Toast.makeText(this, "This Information is necessary.Because when you are in emergency situation the alert message and your location see these people", Toast.LENGTH_LONG).show();

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(phone.getText().toString()) || TextUtils.isEmpty(phone.getText().toString()) || TextUtils.isEmpty(address.getText().toString())) {
                        Toast.makeText(UserDataInsertLoginActivity.this, "Please enter all values ", Toast.LENGTH_SHORT).show();
                    } else {

                        //convert the image into the bitmap
                        Bitmap bitmap = ((BitmapDrawable) imagephoto.getDrawable()).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] imagearray = stream.toByteArray();

                        mydatasave.opendatabase();
                        //saves the data inside the database
                        mydatasave.InsertData(imagearray,name.getText().toString(), email.getText().toString(), phone.getText().toString(), address.getText().toString());
                        mydatasave.closedatabase();

                        Toast.makeText(UserDataInsertLoginActivity.this, "DATA ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        ClearDatafromEdittext();
                        //firsty stop the previous service
                        if(isMyServiceRunning(NotificationService.class))
                        {
                            stopService(new Intent(UserDataInsertLoginActivity.this,NotificationService.class));
                            startService(new Intent(UserDataInsertLoginActivity.this, NotificationService.class));
                        }
                        else {
                            //starting a service for checking the notification
                            startService(new Intent(UserDataInsertLoginActivity.this, NotificationService.class));
                        }
                        //start a new activity
                        Intent intent = new Intent(UserDataInsertLoginActivity.this, AddInfoActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.exit,R.anim.enter);
                    }
                } catch (Exception ex) {
                    Log.e("RISHABH", ex.toString());
                }

            }

        });


        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imagephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(intent, PICK_IMAGE);
                }
                catch (Exception ex)
                {
                 Log.e("RISHABHERROR",ex.toString());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE)
        {
            uri=data.getData();
            imagephoto.setImageURI(uri);
        }
    }

    public void ClearDatafromEdittext()
    {
        name.setText("");
        phone.setText("");
        email.setText("");
        address.setText("");
        imagephoto.setImageResource(R.drawable.contactname);
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
