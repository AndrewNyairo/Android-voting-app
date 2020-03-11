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

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText EeditText, PeditText2, PeditText1;

    TextView LtextView4;

    Button Rbutton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();

        EeditText   = findViewById(R.id.editText);
        PeditText2   = findViewById(R.id.editText2);
        PeditText1   = findViewById(R.id.editText1);
        Rbutton   = findViewById(R.id.button);
        LtextView4   = findViewById(R.id.textView4);


        Rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
String email = EeditText.getText().toString().trim();
String password = PeditText2.getText().toString().trim();

 if (email.isEmpty()){
     EeditText.setError("Password box empty");
 }
 if(password.isEmpty()||password.length() < 6){
     PeditText1.setError("Password should be more that six characters");


 }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                            Intent loginlink = new Intent(getApplicationContext(),Login.class);
                            startActivity(loginlink);


                        }else{
                            Toast.makeText(Register.this,"Try again"+ task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });


    }
}
