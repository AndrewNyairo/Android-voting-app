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
import com.google.firebase.database.Query;
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
String user_email;
String usermail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);
        user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            usermail = user.getUid();
            user_email = user.getEmail();
        } else {
            Toast.makeText(getApplicationContext(),"no user signed in",Toast.LENGTH_SHORT);
        }

        database = mDatabase.getInstance().getReference("VoteApp");
        voting_topic = database.child("Voting_Topic");
        users = voting_topic.child("users");
        total_yes = voting_topic.child("total_yes");
        total_no = voting_topic.child("total_no");
        castvote = findViewById(R.id.logout_user);
        castvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
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
                    voting_topic.child("total_yes").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //checkIfHasVoted(usermail);

                            yes = dataSnapshot.getValue(Integer.class);
                            yes = yes + 1;
                            total_yes.setValue(yes);
                            addVoter(usermail);
                            Toast.makeText(getApplicationContext()," you have succesifuly cast your vote",Toast.LENGTH_SHORT).show();
                            Intent success = new Intent(getApplicationContext(),votesdisplay.class);
                            startActivity(success);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }


            case R.id.no:
                if (checked){

                    voting_topic.child("total_no").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           // checkIfHasVoted(usermail);
                            no = dataSnapshot.getValue(Integer.class);
                            no = no + 1;
                            total_no.setValue(no);
                            addVoter(usermail);
                            Toast.makeText(getApplicationContext()," you have succesifuly cast your vote",Toast.LENGTH_SHORT).show();
                            Intent success = new Intent(getApplicationContext(),votesdisplay.class);
                            startActivity(success);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

                    }

    }

    private void checkIfHasVoted() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
         Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT);
            }
        };
        users.child(usermail).addListenerForSingleValueEvent(eventListener);
    }

    private void addVoter(String useridd) {
        userid voter = new userid(useridd);
        users.child(useridd).setValue("voted");

    }


}
