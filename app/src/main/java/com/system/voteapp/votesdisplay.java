package com.system.voteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class votesdisplay extends AppCompatActivity {
    TextView yes_display,no_display;
    String usermail;
    int totalyesvalue,totalnovalue;
    String finalyestotalvalue;
    String finalnototalvalue;


    private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private DatabaseReference database;
    private DatabaseReference userid;
    private DatabaseReference total_yes;
    private DatabaseReference total_no;
    private DatabaseReference voting_topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votesdisplay);
        yes_display = findViewById(R.id.yes_display_id);
        no_display = findViewById(R.id.no_display_id);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = mDatabase.getInstance().getReference("VoteApp");
        voting_topic = database.child("Voting_Topic");

        if (user != null) {
            usermail = user.getUid();
        } else {
            Toast.makeText(getApplicationContext(),"no user signed in",Toast.LENGTH_SHORT);
        }











// Attach a listener to read the data at our posts reference
         /*private void checkUserVoted(final String kitambulisho) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int i;
                    i=0;
                    i++;
                    voting_topic.child("total_voters").setValue(i);




                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }*/
        voting_topic.child("total_yes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalyesvalue  = dataSnapshot.getValue(Integer.class);
                finalyestotalvalue = String.valueOf(totalyesvalue);
                yes_display.setText(finalyestotalvalue);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        voting_topic.child("total_no").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalnovalue  = dataSnapshot.getValue(Integer.class);
                finalnototalvalue = String.valueOf(totalnovalue);
                no_display.setText(finalnototalvalue);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });









    }
}
