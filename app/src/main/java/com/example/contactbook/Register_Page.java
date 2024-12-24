package com.example.contactbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Register_Page extends AppCompatActivity {
    TextInputEditText user, emailtext, passtext;
    private FirebaseAuth mAuth;
    TextView login;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        user = findViewById(R.id.user);
        emailtext = findViewById(R.id.emailtext);
        passtext = findViewById(R.id.passtext);

        mAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register_Page.this, Login_Page.class));
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailtext.getText().toString();
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailtext.setError("Please enter a valid email address");
                    return;
                }

                String number = passtext.getText().toString();
                if (!number.isEmpty()) {
                    passtext.setError("number if required");
                } else if (number.length() < 6)
                {
                    passtext.setError("Limited Digits 6");
                    return;
                }
                String mail = emailtext.getText().toString().trim();
                String pwd = passtext.getText().toString().trim();
                if (!mail.isEmpty() && !pwd.isEmpty()) {
                    setRegister(mail, pwd);
                } else {
                    Toast.makeText(Register_Page.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(Register_Page.this, Login_Page.class));
            }
        });
    }
    void setRegister(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("+++++++++++", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Register_Page.this, "Authentication Sucess.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w("+++++++++", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register_Page.this, "Authentication failed.",
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
        }
    }
}