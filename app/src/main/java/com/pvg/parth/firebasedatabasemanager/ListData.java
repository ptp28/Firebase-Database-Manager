package com.pvg.parth.firebasedatabasemanager;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ListData extends AppCompatActivity {

    TextView textView;
    String data="";
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);


        textView = (TextView) findViewById(R.id.dataList);
        textView.setMovementMethod(new ScrollingMovementMethod());

        FirebaseApp secondary = FirebaseApp.getInstance("newDB");
        ref = FirebaseDatabase.getInstance(secondary).getReference();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long numChildren = dataSnapshot.getChildrenCount();
                Toast.makeText(getApplicationContext(), Long.toString(numChildren),Toast.LENGTH_LONG).show();


                Map<String, Object> map = (Map<String, Object>)dataSnapshot.getValue();
                Set set=map.entrySet();
                Iterator iterator=set.iterator();
                while(iterator.hasNext())
                {
                    Map.Entry entry = (Map.Entry)iterator.next();
                    if(entry.getValue() instanceof String ) {
                        data = data + entry.getKey().toString()+" : "+entry.getValue().toString()+"\n";
                    }
                    else
                    {
                        data = data + entry.getKey().toString()+" - "+"\n";
                        String dataSplit[] = entry.getValue().toString().split(",");
                        for(int i=0;i<dataSplit.length;i++)
                        {
                            data = data + "\t"+dataSplit[i]+","+"\n";
                        }
                        data = data + "\n\n";
                    }
                    System.out.println(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(),"RETRIEVAL OF DATA COMPLETE",Toast.LENGTH_SHORT).show();
                displayData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void displayData()
    {
        textView.setText(data);
    }
}
