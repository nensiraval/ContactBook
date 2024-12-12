package com.example.contactbook;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    static Context context;
    private DatabaseReference mDatabase;

    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<User> list;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        User user = list.get(position);
        holder.name.setText(user.getName());
        holder.number.setText(user.getNumber());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditPage.class);

                intent.putExtra("userModel", user);

                context.startActivity(intent);
            }
        });
        holder.delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_delete);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                dialog.setCancelable(false);
                TextView tidelete = dialog.findViewById(R.id.tidelete);
                Button cancel = dialog.findViewById(R.id.cancel);
                Button delete = dialog.findViewById(R.id.delete);

                tidelete.getText();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }

                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDatabase = FirebaseDatabase.getInstance().getReference();

                        mDatabase = FirebaseDatabase.getInstance().getReference()
                                .child("users").child(user.getId());
                        mDatabase.removeValue();
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, number, edit, delet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textname);
            number = itemView.findViewById(R.id.textnumber);
            edit = itemView.findViewById(R.id.edit);
            delet = itemView.findViewById(R.id.delet);

        }
    }
}