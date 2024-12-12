package com.example.contactbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Userlist extends AppCompatActivity {
    ImageView more;
    RecyclerView recycleview;
    ArrayList<User> list;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    ImageView plus;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Userlist.this, Add_contact.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);

        recycleview = findViewById(R.id.recycleview);
        plus = findViewById(R.id.plus);
        more = findViewById(R.id.more);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        list = new ArrayList<>();
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list);
        recycleview.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    list.add(user);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Userlist.this, Add_contact.class));
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Userlist.this, more);
                popupMenu.inflate(R.menu.search);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.logout) {
                            SharedPreferences preferences = getSharedPreferences("MyData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();

                            editor.putBoolean("data", true);
                            editor.apply();

                            startActivity(new Intent(Userlist.this, Login_Page.class));
                        }
                        return false;
                    }
                });
            }
        });

    }
}