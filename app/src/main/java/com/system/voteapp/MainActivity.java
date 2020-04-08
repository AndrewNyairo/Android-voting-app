package com.system.voteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
EditText voting_topic_edittext;
Button submit_topic_btn;
String usermail;
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
        setContentView(R.layout.activity_main);
        usermail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        database = mDatabase.getInstance().getReference("VoteApp");
        Totalvotes = voting_topic.child("Total_Votes");
        userid = voting_topic.child(usermail);
        voting_topic = database.child("Voting_Topic");

        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
        voting_topic_edittext = findViewById(R.id.voting_topic_id);
        submit_topic_btn = findViewById(R.id.post_topic_id);

        submit_topic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voting_topic.setValue(voting_topic_edittext.getText().toString().trim());
                Intent success = new Intent(getApplicationContext(),CastVote.class);
                startActivity(success);
            }
        });


    }
}
