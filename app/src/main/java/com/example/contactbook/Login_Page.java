package com.example.contactbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Page extends AppCompatActivity {
    TextInputEditText EmailEditText, passEditext;
    private FirebaseAuth mAuth;TextView signUpText;Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        EmailEditText = findViewById(R.id.EmailEditText);
        passEditext = findViewById(R.id.passEditext);
        signUpText = findViewById(R.id.signUpText);
        loginButton = findViewById(R.id.loginButton);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Page.this, Register_Page.class));
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor =preferences.edit();

                editor.putBoolean("data",true);
                editor.apply();

                String number = passEditext.getText().toString();
                if (number.isEmpty())
                {
                    passEditext.setError("number if required");
                } else if (number.length() != 4)
                {
                    passEditext.setError("Limited Digits 4");
                    return;
                }
                String mail = EmailEditText.getText().toString().trim();
                String password = passEditext.getText().toString().trim();
                if (!mail.isEmpty() && !password.isEmpty()) {
                    login(mail, password);
                }
                else {
                    Toast.makeText(Login_Page.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    void login(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("========", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login_Page.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("____________}", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login_Page.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already signed in; navigate accordingly
        }
    }
}
