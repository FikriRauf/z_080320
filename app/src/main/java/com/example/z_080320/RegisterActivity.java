package com.example.z_080320;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    Button loginBtn, register;
    EditText inputEmail, inputPass,userName;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        inputEmail = findViewById(R.id.registerEmailInput);
        inputPass = findViewById(R.id.registerPasswordInput);
        userName = findViewById(R.id.registerUserNameInput);
        final TextView info = findViewById(R.id.infoTxt);
        register = findViewById(R.id.registerBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim();
                String password = inputPass.getText().toString().trim();
                final String user = userName.getText().toString().trim();
                if(password.length()<= 6){
                    info.setVisibility(View.VISIBLE);
                    info.setText("Password should be more than Six Characters");
                }else{
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Log.d("userId","USer id is " + currentuser);
                                MainViewModel main = new MainViewModel(email.toLowerCase(),user);
                                mDatabase.child(currentuser).setValue(main);
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user
                                Toast.makeText(getApplicationContext(),"Registration unsuccessful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
