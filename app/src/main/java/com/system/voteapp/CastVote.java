package com.system.voteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CastVote extends AppCompatActivity {
FirebaseAuth mAuth;
FirebaseDatabase mDatabase;
DatabaseReference database;
DatabaseReference userid;
DatabaseReference ifvoted;
DatabaseReference Totalvotes;
DatabaseReference total_yes;
DatabaseReference total_no;
DatabaseReference voting_topic;
Boolean ifvotedvalue = false;


Integer yes=0;
Integer no=0;
Button castvote;
String usermail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);
      usermail = mAuth.getInstance().getCurrentUser().getEmail();
       database = mDatabase.getInstance().getReference("VoteApp");
       Totalvotes = voting_topic.child("Total_Votes");

       total_yes = Totalvotes.child("total_yes");
       total_no = Totalvotes.child("Total_no");
       userid = voting_topic.child(usermail);
       ifvoted =userid.child("ifvoted");
       voting_topic = database.child("Voting_Topic");
       castvote = findViewById(R.id.submit_vote);
       castvote.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
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

                if (checked)
                    if (ifvotedvalue==false) {
                        yes = 0;
                        ValueEventListener postListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get Post object and use the values to update the UI
                                ifvotedvalue = dataSnapshot.child("Voting_topic").child(usermail).child("ifvoted").getValue(Boolean.class);
                                yes = dataSnapshot.child("Voting_topic").child("Total_Votes").child("total_yes").getValue(Integer.class);


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Getting Post failed, log a message
                                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                // ...
                            }
                        };
                        database.addValueEventListener(postListener);
                        yes= yes +1;
                        total_yes.setValue(yes);
                        ifvoted.setValue(true);
                        break;
                    }else {
                        Toast.makeText(getApplicationContext(),"you have alredy cast your vote",Toast.LENGTH_LONG).show();
                    }
            case R.id.no:
                if (checked)
                    if (ifvotedvalue==false) {
                        no=0;
                        ValueEventListener postListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // Get Post object and use the values to update the UI
                                ifvotedvalue = dataSnapshot.child("Voting_topic").child(usermail).child("ifvoted").getValue(Boolean.class);
                                no = dataSnapshot.child("Voting_topic").child("Total_Votes").child("total_no").getValue(Integer.class);


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Getting Post failed, log a message
                                //Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                // ...
                            }
                        };
                        database.addValueEventListener(postListener);
                        no= no +1;
                        total_no.setValue(no);
                        ifvoted.setValue(true);
                        break;
                    }else{  Toast.makeText(getApplicationContext(),"you have alredy cast your vote",Toast.LENGTH_LONG).show();
                    }

        }
    }


}
