package com.example.jumann;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private Context context;

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder,  int position) {
        Item item = itemList.get(position);
        holder.headerTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());
// Inside your RecyclerView Adapter's onBindViewHolder method
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the clicked item's ID
                int itemId = itemList.get(position).getId();

                // Open the NewsItemActivity and pass the ID
                Intent intent = new Intent(v.getContext(), NewsItemActivity.class);
                intent.putExtra("news_id", itemId);
                v.getContext().startActivity(intent);
            }
        });

        // Load the image using the image path
        String imagePath = item.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                Glide.with(context)
                        .load(item.getImagePath())
                        .centerCrop()
                        .placeholder(R.drawable.img1) // Placeholder image while loading
                        .error(R.drawable.img1) // Error image if loading fails
                        .into(holder.photoImageView);
            } else {
                // Image file does not exist, handle the error case
                holder.photoImageView.setImageResource(R.drawable.img1);
            }
        } else {
            // Image path is null or empty, handle the error case
            holder.photoImageView.setImageResource(R.drawable.img1);
        }
    }




    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView photoImageView;
        TextView headerTextView;
        TextView descriptionTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            photoImageView = itemView.findViewById(R.id.photoImageView);
            headerTextView = itemView.findViewById(R.id.headerTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
