package com.system.voteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CastVote extends AppCompatActivity {
FirebaseAuth mAuth;
FirebaseUser user;
FirebaseDatabase  mDatabase;
DatabaseReference database;
DatabaseReference voting_topic;
DatabaseReference total_yes;
DatabaseReference total_no;
DatabaseReference users;


Integer yes = 0;
Integer no = 0;
Button castvote;
String usermail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);
        user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            usermail = user.getUid();
        } else {
            Toast.makeText(getApplicationContext(),"no user signed in",Toast.LENGTH_SHORT);
        }

        database = mDatabase.getInstance().getReference("VoteApp");
        voting_topic = database.child("Voting_Topic");
        users = voting_topic.child("users");
        total_yes = voting_topic.child("total_yes");
        total_no = voting_topic.child("total_no");
        castvote = findViewById(R.id.submit_vote);


       castvote.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               addVoter(usermail);

               Intent success = new Intent(getApplicationContext(),votesdisplay.class);
               startActivity(success);
           }
       });





    }




    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {

            case R.id.yes:

                if (checked){
                  addVoter(usermail);
                    

                    voting_topic.child("total_yes").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            yes = dataSnapshot.getValue(Integer.class);
                            yes = yes + 1;
                            total_yes.setValue(yes);
                            Toast.makeText(getApplicationContext()," you have succesifuly cast your vote",Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }


            case R.id.no:
                if (checked){
                    addVoter(usermail);
                    voting_topic.child("total_no").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            no = dataSnapshot.getValue(Integer.class);
                            no = no + 1;
                            total_no.setValue(no);
                            Toast.makeText(getApplicationContext()," you have succesifuly cast your vote",Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

                    }

    }

    public void addVoter(String useridd){

        users.child(useridd);
    }











}
