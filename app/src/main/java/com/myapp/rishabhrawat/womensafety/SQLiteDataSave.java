package com.myapp.rishabhrawat.womensafety;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Rishabh Rawat on 7/3/2017.
 */

public class SQLiteDataSave {

    static final String NAME="Name";
    static final String EMAIL="Email";
    static final String PHONENO="PhoneNo";
    static final String ADDRESS="Address";
    static final String IMAGE="Image";
    static final String ID="id";
    static int count=0;
    //Database name and its version
    static final String DATABASE_NAME="MyUserDetailsDB";
    static final int DATABASE_VERSION=1;
    static final String DATABASE_TABLE="info";

    //create table query
    static final String DATABASE_CREATE="create table info(id integer primary key autoincrement , Name text not null ,Email text not null , PhoneNo text , Address text not null , Image BLOB not null);";

    //Create context and Sqlite database
    Context context;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelperClass databasehelperclass;

    public SQLiteDataSave(Context context)
    {
        this.context=context;
        databasehelperclass=new DatabaseHelperClass(context);

    }


    class DatabaseHelperClass extends SQLiteOpenHelper
    {
        public DatabaseHelperClass(Context context) {
            super(context, DATABASE_NAME,null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DATABASE_CREATE);
            }
            catch (Exception ex)
            {
                Toast.makeText(context, "SOME ERROR IS OCCURED WHILE CREATING DATABASE", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS info");
            onCreate(db);
        }
    }


    //this function is used to open the database
    public SQLiteDataSave opendatabase() throws SQLException
    {
        sqLiteDatabase=databasehelperclass.getWritableDatabase();
        return this;
    }

    public SQLiteDatabase OpendatabaseforReading() throws SQLException
    {
        sqLiteDatabase=databasehelperclass.getReadableDatabase();
        return sqLiteDatabase;
    }

    public void closedatabase()
    {
        databasehelperclass.close();
    }


    //Inserting the data inside the database
    public long InsertData(byte[] image,String name,String email,String phoneno,String address)
    {

        //when we inserting data in the sqlite database we use a ContentValue class
        ContentValues values=new ContentValues();
        //values.put("ID",count);
        values.put("NAME",name);
        values.put("EMAIL",email);
        values.put("PHONENO",phoneno);
        values.put("ADDRESS",address);
        values.put("IMAGE",image);

        //if some occur are found then it return -1
        return sqLiteDatabase.insertOrThrow(DATABASE_TABLE,null,values);

    }


    //Delete a particular data
    public Boolean DeleteParticularData(int Phone)
    {
        return sqLiteDatabase.delete(DATABASE_TABLE,PHONENO+"="+Phone,null)>0;
    }

    //Reterive a particular data
    public Cursor RetriveParticularContact(int Phone)
    {
        Cursor cursor=sqLiteDatabase.query(true,DATABASE_TABLE,new String[]{NAME,EMAIL,PHONENO,ADDRESS,IMAGE},Phone+"="+Phone,null,null,null,null,null);
        return cursor;
    }

    //Updating the data
    public boolean updateContact(String phoneid,String name, String email,String phoneno,String address)
    {
        ContentValues args = new ContentValues();
        args.put(NAME, name);
        args.put(EMAIL, email);
        args.put(PHONENO,phoneno);
        args.put(ADDRESS,address);
        return sqLiteDatabase.update(DATABASE_TABLE, args, PHONENO + "=" + phoneid, null) > 0;
    }

    //Get all contact
    public Cursor getAllContacts()
    {
        return sqLiteDatabase.query(DATABASE_TABLE, new String[] {IMAGE,NAME,
                EMAIL,PHONENO,ADDRESS}, null, null, null, null, null);
    }

    public Cursor getMydata(String sql)
    {
      SQLiteDatabase database=databasehelperclass.getReadableDatabase();
        return database.rawQuery(sql,null);
    }

}
