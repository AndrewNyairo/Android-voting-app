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


public class Login extends AppCompatActivity {
    TextView signuptxtlink;
    private FirebaseAuth fAuth;
    EditText emaill, passwordd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button linkit =  findViewById(R.id.loginbtn);
        signuptxtlink = findViewById(R.id.textView6);
        emaill = findViewById(R.id.editText3);
        passwordd = findViewById(R.id.editText4);
        fAuth = FirebaseAuth.getInstance();


        linkit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


fAuth.signInWithEmailAndPassword(emaill.getText().toString().trim(), passwordd.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
            Toast.makeText(Login.this, "you are in", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }else {
            Toast.makeText(Login.this,"go to hell man, you suck"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
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
