package com.system.voteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
EditText voting_topic_edittext;
Button submit_topic_btn;
String votingtopictext;
String usermail;
   private  FirebaseAuth mAuth;
   private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private DatabaseReference database;
    private DatabaseReference voting_topic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = mDatabase.getInstance().getReference("VoteApp");
        voting_topic = database.child("Voting_Topic");
        voting_topic_edittext = findViewById(R.id.voting_topic_id);
        votingtopictext = voting_topic_edittext.getText().toString().trim();
        submit_topic_btn = findViewById(R.id.post_topic_id);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

        submit_topic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent success = new Intent(getApplicationContext(),CastVote.class);
                startActivity(success);
            }
        });


    }
}
