package com.troy.sportsbetting;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BetActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    TextView textView;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);
        textView = findViewById(R.id.betTextView);
        basicReadWrite();
        betinside();
    }

    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance("https://sports-bettings-737b8-default-rtdb.europe-west1.firebasedatabase.app").getReference("bets");
// Read from the database




}

    public void betinside() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    String data= dataSnapshot.getValue().toString();
                    textView.setText(data);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
}