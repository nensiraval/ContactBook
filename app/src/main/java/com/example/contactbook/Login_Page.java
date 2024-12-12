package com.example.contactbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

public class Login_Page extends AppCompatActivity {
    TextInputEditText EmailEditText, passEditext;
    SignInButton bt_sign_in;
    GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;
    TextView signUpText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        EmailEditText = findViewById(R.id.EmailEditText);
        passEditext = findViewById(R.id.passEditext);
        signUpText = findViewById(R.id.signUpText);
        loginButton = findViewById(R.id.loginButton);
        bt_sign_in = findViewById(R.id.bt_sign_in);
        mAuth = FirebaseAuth.getInstance();

        askNotificationPermission();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("200939957625-ttcq05gd0d8gbthl8bsrc0bi0d9b8osv.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(Login_Page.this, googleSignInOptions);

        bt_sign_in.setOnClickListener((View.OnClickListener) view -> {
            // Initialize sign in intent
            Intent intent = googleSignInClient.getSignInIntent();
            // Start activity for result
            startActivityForResult(intent, 100);
        });


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
                SharedPreferences.Editor editor = preferences.edit();

                editor.putBoolean("data", true);
                editor.apply();

                String number = passEditext.getText().toString();
                if (!number.isEmpty()) {
                    passEditext.setError("number if required");
                } else if (number.length() < 6) {
                    passEditext.setError("Limited Digits 6");
                    return;
                }
                String mail = EmailEditText.getText().toString().trim();
                String password = passEditext.getText().toString().trim();
                if (!mail.isEmpty() && !password.isEmpty()) {
                    login(mail, password);
                } else {
                    Toast.makeText(Login_Page.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            // check condition
            if (signInAccountTask.isSuccessful()) {
                // When google sign in successful initialize string
                String s = "Google sign in successful";
                // Display Toast
                displayToast(s);
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential
                        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Check condition
                                if (task.isSuccessful()) {
                                    // When task is successful redirect to profile activity display Toast
                                    startActivity(new Intent(Login_Page.this, Userlist.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                    displayToast("Firebase authentication successful");
                                } else {
                                    // When task is unsuccessful display Toast
                                    displayToast("Authentication Failed :" + task.getException().getMessage());
                                    Log.e("++++++++++++", "failed: ");
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    void login(String email, String password) {
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

                            startActivity(new Intent(Login_Page.this, Userlist.class));
                            finish();
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


    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    });
    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){

            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {

            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
}
