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
    EditText branchInput;

    private FirebaseOptions firebaseOptions;
    private FirebaseApp firebaseApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        urlInput = (EditText) findViewById(R.id.editText1);
        apiInput = (EditText) findViewById(R.id.editText2);
        idInput = (EditText) findViewById(R.id.editText3);
        branchInput = (EditText) findViewById(R.id.editText4);

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

    private void initDatabase() {
        DatabaseReference ref;
        if (branchInput.getText().toString().equals("")) {
            ref = FirebaseDatabase.getInstance(firebaseApp).getReference();
        } else {
            ref = FirebaseDatabase.getInstance(firebaseApp).getReference(branchInput.getText().toString());
        }

        showData();
    }

    private void showData()
    {
        Intent intent = new Intent(this, ListData.class);
        startActivity(intent);
    }
}
