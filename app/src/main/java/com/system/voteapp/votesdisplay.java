package com.system.voteapp;

import androidx.appcompat.app.AppCompatActivity;
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
    String totalyesvalue;
    String totalnovalue;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private DatabaseReference database;
    private DatabaseReference userid;
    private DatabaseReference ifvoted;
    private DatabaseReference Totalvotes;
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

        /*if (user != null) {
            usermail = user.getEmail().trim();
        } else {
            Toast.makeText(getApplicationContext(),"no user signed in",Toast.LENGTH_SHORT);
        }*/
        //usermail = mAuth.getInstance().getCurrentUser().getEmail();

        database = mDatabase.getInstance().getReference("VoteApp");
        voting_topic = database.child("Voting_Topic");
        Totalvotes = voting_topic.child("Total_Votes");
        usermail = database.push().getKey();
        userid = voting_topic.child(usermail);







// Attach a listener to read the data at our posts reference
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                totalyesvalue = dataSnapshot.child("Voting_topic").child("Total_Votes").child("total_yes").getValue(String.class);
                totalnovalue = dataSnapshot.child("Voting_topic").child("Total_Votes").child("Total_no").getValue(String.class);
                yes_display.setText(totalyesvalue);
                no_display.setText(totalnovalue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });







        yes_display.setText(totalyesvalue);
        no_display.setText(totalnovalue);

    }
}
