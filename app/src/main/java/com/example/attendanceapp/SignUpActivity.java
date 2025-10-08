package com.example.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Create Account page OR sign up page

        TextView alreadyHaveAccount ;
        Button signup;
        EditText createusername;
        EditText createpassword;
        EditText confirmpassword;
        Login_Database DB = new Login_Database(this);


        alreadyHaveAccount = findViewById(R.id.alreadyHaveAccount);
        createusername = findViewById(R.id.createusername);
        createpassword = findViewById(R.id.createpassword);
        confirmpassword= findViewById(R.id.confirmpassword);
        signup = findViewById(R.id.signup);


        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {     // account already hai toh login page pe jana
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = createusername.getText().toString();
                String password    = createpassword.getText().toString();
                String confirmpass = confirmpassword.getText().toString();

                if (username.equals("") || password.equals("") )          // check kr rha ki username password blank to nhi
                    Toast.makeText(SignUpActivity.this , "Please fill the information", Toast.LENGTH_SHORT) . show();

                else {
                    if (password.equals(confirmpass)) {
                        Boolean checkuser = DB.checkusername(username);
                        if (checkuser == false) {
                            Boolean insert = DB.insertData(username, password);
                            if (insert == true) {
                                Toast.makeText(SignUpActivity.this, "Registered successfully ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Registeration failed ", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "User already existes ", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                            Toast.makeText(SignUpActivity.this , "Passwords do not match", Toast.LENGTH_SHORT) . show();
                        }
                    }
                    }

        });


    }
}