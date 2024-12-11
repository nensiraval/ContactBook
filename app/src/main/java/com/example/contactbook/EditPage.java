package com.example.contactbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditPage extends AppCompatActivity {

    EditText name, number;
    Button SaveEdit;
    private String userId;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        SaveEdit = findViewById(R.id.SaveEdit);

        String uname = getIntent().getStringExtra("name");
        String unumber = getIntent().getStringExtra("number");

//        Intent intent = getIntent();
//        String uname = intent.getStringExtra("name");
//        String unumber = intent.getStringExtra("number");
//        userId = intent.getStringExtra("userId");

        name.setText(uname);
        number.setText(unumber);

//        if (userId == null || uname == null || unumber == null) {
//            Log.e("++++++++", "onCreate: " );
//            Toast.makeText(this, "Error: Missing data", Toast.LENGTH_SHORT).show();
//            finish(); // Close the activity if data is missing
//            return;
//        }

       // mDatabase = FirebaseDatabase.getInstance().getReference("users").child(userId);

        SaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPage.this, Userlist.class));
//                String newName = name.getText().toString();
//                String newNumber = number.getText().toString();
//
//                mDatabase.child("name").setValue(newName);
//                mDatabase.child("number").setValue(newNumber).addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(EditPage.this, "Contact updated successfully", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(EditPage.this, Userlist.class));
//                        finish();
//                    } else {
//                        Toast.makeText(EditPage.this, "Failed to update contact", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }
}