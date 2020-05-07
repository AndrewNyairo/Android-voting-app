package com.system.voteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {
    private TextView signuptxtlink;
    private FirebaseAuth fAuth;
    private FirebaseUser user;
    private EditText emaill, passwordd;
    private Button linkit;
    String userauth;
    String admin  = "johnsonyaanga@gmail.com".trim();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = fAuth.getInstance().getCurrentUser();
        if (user != null) {
            userauth = user.getEmail().trim();
        } else {
            Toast.makeText(getApplicationContext(),"no user signed in",Toast.LENGTH_SHORT);
        }
        linkit =  findViewById(R.id.btn_login);
        signuptxtlink = findViewById(R.id.signup_text);
        emaill = findViewById(R.id.email_login);
        passwordd = findViewById(R.id.password_login);
        fAuth = FirebaseAuth.getInstance();


        linkit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


fAuth.signInWithEmailAndPassword(emaill.getText().toString().trim(), passwordd.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            if (admin != userauth){
                Intent i = new Intent(getApplicationContext(), CastVote.class);
                startActivity(i);
                Toast.makeText(Login.this, userauth, Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Login.this,"you are not admin"+ userauth, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

        }else {
            Toast.makeText(Login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
        }

    }
});

            }
        });
        signuptxtlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkit = new Intent(getApplicationContext(),Register.class);
                startActivity(linkit);
            }
        });
    }
}
