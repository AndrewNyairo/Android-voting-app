package com.system.voteapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
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
FirebaseAuth.AuthStateListener authStateListener;
DatabaseReference voting_topic;
DatabaseReference total_yes;
DatabaseReference total_no;
DatabaseReference users;
Integer yes = 0;
Integer no = 0;
String usermail;
Button voteoption1,voteoption2;
TextView mitext;
TextView votetitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_vote);
        mitext = findViewById(R.id.showitmen);
        votetitle = findViewById(R.id.voting_title_id);
        voteoption1 = findViewById(R.id.vote_option1);
        voteoption2 = findViewById(R.id.vote_option2);
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
        Toolbar mytool = findViewById(R.id.my_toolbar);
        setSupportActionBar(mytool);
        Intent i = getIntent();
        String me = i.getStringExtra("topic");
        votetitle.setText(me);


        voting_topic.child("voting_title").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                votetitle.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



        voteoption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog2();
            }
        });
        voteoption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });




    }


    private void customDialog2() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(CastVote.this);
        LayoutInflater inflater  = LayoutInflater.from(CastVote.this);
        View myview = inflater.inflate(R.layout.confirm_vote, null);
        final AlertDialog dialog = mydialog.create();
        dialog.setView(myview);

        Button kubali = (Button) myview.findViewById(R.id.btn_yes);
        Button kataa = (Button) myview.findViewById(R.id.btn_no);


        kataa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        kubali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                voting_topic.child("total_yes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        yes = dataSnapshot.getValue(Integer.class);
                        yes = yes + 1;
                        total_yes.setValue(yes);
                        addVoter(usermail);
                        Toast.makeText(getApplicationContext()," you have succesifully cast your vote",Toast.LENGTH_SHORT).show();
                        Intent success = new Intent(getApplicationContext(),votesdisplay.class);
                        startActivity(success);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });






            }
        });
        dialog.show();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return  true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id== R.id.admin){
            Intent i = new Intent(getApplicationContext(),Login.class);
            startActivity(i);
        }

        else if(id==R.id.result){
            Intent i = new Intent(getApplicationContext(), votesdisplay.class);
            startActivity(i);        }

        else if(id==R.id.logout_app){
            Intent i = new Intent(getApplicationContext(),Login.class);
            startActivity(i);
        }
        return true;

    }
    private void addVoter(String useridd) {
        users.child(useridd).setValue(useridd);

    }
    private void customDialog(){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(CastVote.this);
        LayoutInflater inflater  = LayoutInflater.from(CastVote.this);
        View myview = inflater.inflate(R.layout.confirm_vote, null);
        final AlertDialog dialog = mydialog.create();
        dialog.setView(myview);

        Button kubali = (Button) myview.findViewById(R.id.btn_yes);
        Button kataa = (Button) myview.findViewById(R.id.btn_no);



        kubali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                voting_topic.child("total_no").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        no = dataSnapshot.getValue(Integer.class);
                        no = no + 1;
                        total_no.setValue(no);
                        addVoter(usermail);
                        Toast.makeText(getApplicationContext()," you have succesifully cast your vote",Toast.LENGTH_SHORT).show();
                        Intent success = new Intent(getApplicationContext(),votesdisplay.class);
                        startActivity(success);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });






            }
        });
        kataa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }



}
