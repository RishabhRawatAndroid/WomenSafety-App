package com.myapp.rishabhrawat.womensafety;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HelplineActivity extends AppCompatActivity {

    ListView helplist;
    int[] number;
    ArrayList<String> help_line_no_with_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);

        helplist=(ListView)findViewById(R.id.helplistview);

        help_line_no_with_name =new ArrayList<>();
        help_line_no_with_name.add(String.format("Women helpline no.\n 1091"));
        help_line_no_with_name.add(String.format("Anti staking no. \n 1096"));
        help_line_no_with_name.add(String.format("Police Control room \n 100"));

        number=new int[]{1091,1096,100};

        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(HelplineActivity.this,android.R.layout.simple_list_item_1,help_line_no_with_name);
        helplist.setAdapter(stringArrayAdapter);

        helplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(String.valueOf("tel"+number[position])));
                startActivity(intent);
            }
        });
    }

}
