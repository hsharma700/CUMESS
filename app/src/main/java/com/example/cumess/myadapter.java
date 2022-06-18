package com.example.cumess;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final model model)
    {
        holder.name.setText(model.getName());
        holder.pri.setText(model.getPri());
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

                        holder.edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                                        .setExpanded(true,1100)
                                        .create();


                                View myview=dialogPlus.getHolderView();
                                EditText purl=myview.findViewById(R.id.uimgurl);
                                EditText name=myview.findViewById(R.id.uname);
                                EditText price=myview.findViewById(R.id.upri);
                                Button submit=myview.findViewById(R.id.usubmit);


                                purl.setText(model.getPurl());
                                name.setText(model.getName());
                                price.setText(model.getPri());


                                dialogPlus.show();

                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Map<String,Object> map=new HashMap<>();
                                        map.put("purl",purl.getText().toString());
                                        map.put("name",name.getText().toString());
                                        map.put("pri",price.getText().toString());

                                        FirebaseDatabase.getInstance().getReference().child("items")
                                                .child(getRef(position).getKey()).updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        dialogPlus.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        dialogPlus.dismiss();
                                                    }
                                                });
                                    }
                                });

                            }
                        });


                        holder.delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                                builder.setTitle("Delete Item");
                                builder.setMessage("Item Will be Deleted");

                                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        FirebaseDatabase.getInstance().getReference().child("items")
                                                .child(getRef(position).getKey()).removeValue();
                                    }
                                });

                                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                builder.show();
                            }
                        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView name,pri;
        ImageView edit, delete;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img=(CircleImageView)itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            pri=(TextView)itemView.findViewById(R.id.pritext);

            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);

        }
    }
}
