package com.myapp.rishabhrawat.womensafety;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button helpbtn;
    TextView idtext;
    Button safebtn;
    private DatabaseReference locationroot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_navigation);

        helpbtn=(Button)findViewById(R.id.helpbtn);
        idtext=(TextView) findViewById(R.id.textid);
        safebtn=(Button)findViewById(R.id.savebtn);

         SharedPreferenceClass sharedPreferenceClass=new SharedPreferenceClass(MainNavigationActivity.this);
        idtext.setText(sharedPreferenceClass.getRandomstring());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void HelpButtonMethod(View view)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainNavigationActivity.this);
        builder1.setMessage("Are you sure you are in emergency situation..");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        SharedPreferenceClass sharedPreferenceClass=new SharedPreferenceClass(MainNavigationActivity.this);
                        //this code gives the notification
                        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("NOTIFICATION").child(sharedPreferenceClass.getRandomstring());
                        Map<String,Object> map =new HashMap<>();
                        map.put("NAME",sharedPreferenceClass.getName());
                        map.put("PHONE",sharedPreferenceClass.getPhone());
                        reference.updateChildren(map);
                        startService(new Intent(MainNavigationActivity.this,MyService.class));


                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void SafeButtonMethod(View view)
    {
        if(isMyServiceRunning(MyService.class))
        {
            //firsty stop the location service
            stopService(new Intent(MainNavigationActivity.this,MyService.class));
            //and delete the location data in the cloud
             deletedata();
        }
        else {
           deletedata();
        }

    }

    public void deletedata()
    {
        SharedPreferenceClass sharedPreferenceClass=new SharedPreferenceClass(MainNavigationActivity.this);
        locationroot = FirebaseDatabase.getInstance().getReference().child("LOCATION").child(sharedPreferenceClass.locationid());
        locationroot.setValue(null);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.userid) {
            Intent intent=new Intent(MainNavigationActivity.this,UserIDActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
        } else if (id == R.id.updateinfo) {
            SharedPreferenceClass sharedPreferenceClass=new SharedPreferenceClass(MainNavigationActivity.this);
            sharedPreferenceClass.setupdate(true);
            Intent intent=new Intent(MainNavigationActivity.this,LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
        } else if (id == R.id.enterdetails) {
            Intent intent=new Intent(MainNavigationActivity.this,UserDataInsertLoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);

        } else if (id == R.id.listuser) {
            Intent intent=new Intent(MainNavigationActivity.this,AddInfoActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
        }
        else if(id==R.id.helpline){
            Intent intent=new Intent(MainNavigationActivity.this,HelplineActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //checking if service is running or not in android
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
