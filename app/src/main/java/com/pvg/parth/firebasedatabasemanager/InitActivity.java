package com.pvg.parth.firebasedatabasemanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class InitActivity extends AppCompatActivity {

    EditText urlInput;
    EditText apiInput;
    EditText idInput;

    private String data;

    private FirebaseOptions firebaseOptions;
    private FirebaseApp firebaseApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        urlInput = (EditText) findViewById(R.id.editText1);
        apiInput = (EditText) findViewById(R.id.editText2);
        idInput = (EditText) findViewById(R.id.editText3);

        urlInput.setText("https://test-project-c04a3.firebaseio.com/");
        apiInput.setText("AIzaSyB2dSLaAmA9N-IFFq-M-zVSmH28c4yBirc");
        idInput.setText("1:458692751813:android:c026922d4048b610");

    }

    public void initFirebase(View view)
    {
        String urlString = urlInput.getText().toString();
        String appID = idInput.getText().toString();
        String apiKey = apiInput.getText().toString();

        firebaseOptions = new FirebaseOptions.Builder()
                .setApiKey(apiKey)
                .setApplicationId(appID)
                .setDatabaseUrl(urlString)
                .build();
        firebaseApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "newDB");

        initDatabase();
    }

    private void initDatabase()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance(firebaseApp).getReference();

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
                    data = data + entry.getKey().toString()+" : "+entry.getValue().toString()+"\n";
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
                Toast.makeText(getApplicationContext(),"RETRIVAL OF DATA COMPLETE",Toast.LENGTH_SHORT).show();
                showData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showData()
    {
        Intent intent = new Intent(this, ListData.class);
        intent.putExtra("Data",data);
        finish();
        startActivity(intent);
    }
}
