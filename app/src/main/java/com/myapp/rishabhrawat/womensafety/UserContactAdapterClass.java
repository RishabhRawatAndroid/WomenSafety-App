package com.myapp.rishabhrawat.womensafety;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Rishabh Rawat on 7/3/2017.
 */

public class UserContactAdapterClass extends BaseAdapter {

    private Context context;
    private ArrayList<UserContact> userdetails;
    private int layout;

    public UserContactAdapterClass(Context context, ArrayList<UserContact> userdetails, int layout) {
        this.context = context;
        this.userdetails = userdetails;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return userdetails.size();
    }

    @Override
    public Object getItem(int position) {
        return userdetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return userdetails.indexOf(getItem(position));
    }

    public class Holder
    {
        ImageView photo;
        ImageView delete;
        ImageView edit;
        TextView name;
        TextView phone;
        TextView email;
        TextView address;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        Holder hold = null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (row == null) {
            row = inflater.inflate(layout, null);
            hold = new Holder();
            hold.photo = (ImageView) row.findViewById(R.id.imageView);
            hold.delete = (ImageView) row.findViewById(R.id.imagedelete);
            hold.edit = (ImageView) row.findViewById(R.id.imageedit);
            hold.name = (TextView) row.findViewById(R.id.nametextview);
            hold.phone = (TextView) row.findViewById(R.id.phonetextview);
            hold.email = (TextView) row.findViewById(R.id.emailtextview);
            hold.address = (TextView) row.findViewById(R.id.addresstextView);


            UserContact contact = userdetails.get(position);
            hold.delete.setImageResource(R.drawable.delete_white);
            hold.edit.setImageResource(R.drawable.editpen);
            hold.name.setText(contact.getUname());
            hold.email.setText(contact.getUemail());
            hold.phone.setText(contact.getUphone());
            hold.address.setText(contact.getUaddress());
            row.setTag(hold);
            //byte[] imagedata=contact.getUimage();
            //Bitmap bitmap= BitmapFactory.decodeByteArray(imagedata,0,imagedata.length);
            //hold.photo.setImageBitmap(bitmap);



            hold.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Delete Button click...", Toast.LENGTH_SHORT).show();
                }
            });


            hold.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Edit Button Click...", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
             {
                hold=(Holder)row.getTag();
            }
            return row;
        }

}
