package com.system.voteapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class votesdisplay extends AppCompatActivity {
    TextView yes_display,no_display;
    String usermail;
    String totalyesvalue;
    String totalnovalue;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference database;
    DatabaseReference userid;
    DatabaseReference ifvoted;
    DatabaseReference Totalvotes;
    DatabaseReference total_yes;
    DatabaseReference total_no;
    DatabaseReference voting_topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votesdisplay);
        yes_display = findViewById(R.id.yes_display_id);
        no_display = findViewById(R.id.no_display_id);
        usermail = mAuth.getInstance().getCurrentUser().getEmail();
        database = mDatabase.getInstance().getReference("VoteApp");
        Totalvotes = voting_topic.child("Total_Votes");
        userid = voting_topic.child(usermail);
        voting_topic = database.child("Voting_Topic");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                totalyesvalue = dataSnapshot.child("Voting_topic").child("Total_Votes").child("total_yes").getValue(String.class);
                totalnovalue = dataSnapshot.child("Voting_topic").child("Total_Votes").child("Total_no").getValue(String.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                // ...
            }
        };

        yes_display.setText(totalyesvalue);
        no_display.setText(totalnovalue);

    }
}
