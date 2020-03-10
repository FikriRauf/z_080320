package com.example.z_080320;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView registerBtn;
    TextView forgotBtn;

    private Button loginBtn;
    private EditText userNameInput;
    private EditText passWordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerBtn = findViewById(R.id.registerMessage02);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotBtn = findViewById(R.id.forgotPassword);
        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });



        loginBtn = (Button) findViewById(R.id.loginBtn);
        userNameInput = (EditText) findViewById(R.id.loginEmailInput);
        passWordInput = (EditText) findViewById(R.id.loginPasswordInput);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(userNameInput.getText().toString(),passWordInput.getText().toString());
            }
        });
    }

    private void validate(String userName, String userPassword){
        if(userName.equals("user") && userPassword.equals("password123")){
//            Intent intent = new Intent(MainActivity.this, homePageActivity.class);
//            startActivity(intent);
        }
    }
}
