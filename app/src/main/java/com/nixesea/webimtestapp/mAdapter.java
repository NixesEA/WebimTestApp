package com.nixesea.webimtestapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


class mAdapterHolder extends RecyclerView.ViewHolder {

    public TextView itemName;
    public TextView itemOnline;
    public ImageView itemImage;

    mAdapterHolder(View itemView) {
        super(itemView);

        itemName = itemView.findViewById(R.id.item_name);
        itemOnline = itemView.findViewById(R.id.item_online);
        itemImage = itemView.findViewById(R.id.item_photo);
    }
}

public class mAdapter extends RecyclerView.Adapter<mAdapterHolder> {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<UserModel> friendsList;

    public mAdapter(Context context, ArrayList<UserModel> list) {
        this.context = context;
        this.friendsList = list;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public mAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.friend_item, parent, false);

        return new mAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final mAdapterHolder holder, int position) {
        holder.itemName.setText(friendsList.get(position).getName());
        if (friendsList.get(position).isOnline()){
            holder.itemOnline.setText("online");
        }else {
            holder.itemOnline.setText("offline");
        }

        Glide.with(context)
                .load(friendsList.get(position).getPhotoURL())
                .into(holder.itemImage);

    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }
}