package com.myapp.rishabhrawat.womensafety;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationService extends Service {

    SQLiteDataSave dataSave;
    static String getidstr;
    long count;
    DatabaseReference reference;
    Notification.Builder builder;
    NotificationManager manager;
    boolean check=true;
    boolean ifcheck=true;

    public NotificationService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        reference= FirebaseDatabase.getInstance().getReference().child("NOTIFICATION");
        dataSave=new SQLiteDataSave(getApplicationContext());
        dataSave.opendatabase();
        SQLiteDatabase database=dataSave.OpendatabaseforReading();
        count  = DatabaseUtils.queryNumEntries(database,"info");

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new Notification.Builder(getApplicationContext());

        builder.setTicker("WOMEN SAFETY");
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setContentTitle("Emergency Notification");
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.COLOR_DEFAULT | Notification.DEFAULT_VIBRATE);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setLights(Color.YELLOW, 500, 500);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000});
        builder.setSmallIcon(R.mipmap.ic_launcher);


       // manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       // builder = new Notification.Builder(getApplicationContext());


        //generate a pending intent
        Intent notifyintent=new Intent(getApplicationContext(),MapTrackingActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,notifyintent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Toast.makeText(this, "Notification service started", Toast.LENGTH_SHORT).show();



    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

            new Thread() {
                @Override
                public void run() {
                    WORKINBACKGROUND();
                }
            }.start();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void WORKINBACKGROUND() {

            Cursor cursor = dataSave.getAllContacts();
            while (check) {

                if (cursor.moveToFirst()) {
                    do {
                        getidstr = cursor.getString(4);
                        //listener for checking at this node id has changes or not
                        //Toast.makeText(getApplicationContext(), "NOTIFICATION WORKINBACKGROUND"+getidstr, Toast.LENGTH_SHORT).show();
                        Log.e("RISHABHERROR", "NOTIFICATION WORKINBACKGROUND  " + getidstr);


                        reference.child(getidstr).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                if (ifcheck) {

                                    MapTrackingActivity.id = getidstr;
                                    String name = String.valueOf(dataSnapshot.getValue());
                                    Log.e("RISHABH", name);
                                    SharedPreferenceClass prefer = new SharedPreferenceClass(getApplicationContext());
                                    prefer.setlocationid(getidstr);
                                    prefer.setuserpresentlocation(name);
                                    builder.setContentText(String.format(name + " Need a help please rescue the " + name + " click this notification and see the location of " + name));
                                    builder.setStyle(new Notification.BigTextStyle().bigText(name + " Need a help please rescue the " + name + " click this notification and see the location of " + name));
                                    Toast.makeText(NotificationService.this, "Notification occur..", Toast.LENGTH_SHORT).show();
                                    //manager.notify(0,builder.build());

                                    reference.child(getidstr).removeValue();
                                    ifcheck = false;
                                    Log.e("RISHABH", "IF CEHCK IS" + ifcheck);
                                    Log.e("RISHABH", "Remove data from cloud");

                                    for (int a = 0; a <= 7; a++)
                                        manager.notify(0, builder.build());
                                    builder.setAutoCancel(true);
                                    onDestroy();
                                } else {
                                    Log.e("RISHABH", "NOTIFICATION REMOVED");
                                }
                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    while (cursor.moveToNext());

                }


            }

    }


}
