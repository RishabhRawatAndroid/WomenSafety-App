package com.myapp.rishabhrawat.womensafety;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class AddInfoActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<UserContact> contactinfo;
    UserContactAdapterClass contactAdapterClass;
    static int check;

    SQLiteDataSave database;
    FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);


        listView = (ListView) findViewById(R.id.datalistview);
        contactinfo = new ArrayList<UserContact>();
        contactAdapterClass = new UserContactAdapterClass(AddInfoActivity.this, contactinfo, R.layout.listviewdata);
        listView.setAdapter(contactAdapterClass);


        database = new SQLiteDataSave(AddInfoActivity.this);
        database.opendatabase();
        Cursor cursor = database.getAllContacts();
        DisplayContact(cursor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionButton = (FloatingActionButton) findViewById(R.id.floatbtn);


    }

    public void FloatButton(View view) {
        Intent intent = new Intent(AddInfoActivity.this, UserDataInsertLoginActivity.class);
        startActivity(intent);
    }

    private void DisplayContact(Cursor cursor) {
        contactinfo.clear();

        if (!cursor.moveToFirst()) {
            Toast.makeText(this, "Wrong to move first", Toast.LENGTH_SHORT).show();
        } else {
            if (cursor.moveToFirst()) {
                do {
                    //retrive all data
                    // String count = cursor.getString(0);
                    String name = cursor.getString(1);
                    String email = cursor.getString(2);
                    String phone = cursor.getString(3);
                    String address = cursor.getString(4);
                   //   byte[] image = cursor.getBlob(5);

                    contactinfo.add(new UserContact(name, phone, email, address));
                    contactAdapterClass.notifyDataSetChanged();
                }
                while (cursor.moveToNext());
                contactAdapterClass.notifyDataSetChanged();

            }
        }
    }
}