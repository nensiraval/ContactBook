package com.example.contactbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_contact extends AppCompatActivity {
        Button save,view;
        EditText fname,phone,surname;

        DatabaseReference databaseUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        save = findViewById(R.id.save);
        view = findViewById(R.id.view);
        fname = findViewById(R.id.fname);
        phone = findViewById(R.id.phone);
        surname = findViewById(R.id.surname);


    databaseUsers = FirebaseDatabase.getInstance().getReference();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phone.getText().toString();
                if (number.isEmpty()) {
                    phone.setError("Number is required");
                    return;
                } else if (number.length() != 10) {
                    phone.setError("Limited Digits 10");
                    return;
                }

                // If validation passes, insert data
                InsertData();
                startActivity(new Intent(Add_contact.this,Userlist.class));
            }
        });
    view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Add_contact.this,Userlist.class));
            finish();
        }
    });

    }

    private void InsertData() {
        String firstName = fname.getText().toString().trim();
        String lastName = surname.getText().toString().trim();
        String fullName = firstName + " " + lastName;
        String userNumber = phone.getText().toString().trim();
        String id = databaseUsers.push().getKey();

        User user = new User(fullName, userNumber);
        databaseUsers.child("users").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Add_contact.this, "User data inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Add_contact.this, "Failed to insert user data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}