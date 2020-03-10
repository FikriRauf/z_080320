package com.example.z_080320;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    TextView registerBtn;
    TextView forgotBtn;

    private Button loginBtn;
    private EditText userNameInput;
    private EditText passWordInput;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        registerBtn = findViewById(R.id.registerMessage02);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotBtn = findViewById(R.id.forgotPassword);
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });



        loginBtn = (Button) findViewById(R.id.loginBtn);
        userNameInput = (EditText) findViewById(R.id.loginEmailInput);
        passWordInput = (EditText) findViewById(R.id.loginPasswordInput);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(userNameInput.getText().toString().trim(),passWordInput.getText().toString().trim());
            }
        });
    }

    private void validate(String userName, String userPassword){

        Log.d("login","EMail is " + userName + "password is " + userPassword);
        if(userName.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(this,"Please do not leave any fields empty",Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(userName, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                Log.d("login","Err in login");
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Login Unsuccessful. Please check Email and Password", Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
        }
    }
}
