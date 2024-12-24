package com.example.contactbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditPage extends AppCompatActivity {

    EditText name, number;
    Button SaveEdit;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        SaveEdit = findViewById(R.id.SaveEdit);

        user = (User) getIntent().getSerializableExtra("userModel");


        name.setText(user.getName());
        number.setText(user.getNumber());


        SaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setName(name.getText().toString());
                user.setNumber(number.getText().toString());

                DatabaseReference databaseUsers = FirebaseDatabase.getInstance().getReference();
                databaseUsers.child("users").child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EditPage.this, "User data update successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditPage.this, "Failed to update user data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}