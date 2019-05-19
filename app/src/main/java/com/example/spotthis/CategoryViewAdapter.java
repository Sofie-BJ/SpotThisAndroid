package com.example.spotthis;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spotthis.Models.Category;
import com.example.spotthis.Models.Image;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryViewAdapter extends RecyclerView.Adapter<CategoryViewAdapter.CategoryViewHolder> {

    List<Category> categories;
    Context context;

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout parentLayout;
        TextView categoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parentlayout);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }

    CategoryViewAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);

        CategoryViewHolder viewHolder = new CategoryViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder, final int i) {
        categoryViewHolder.categoryName.setText(categories.get(i).getCategoryname());

        categoryViewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryImagesActivity.class);
                intent.putExtra("CATEGORYNAME", categoryViewHolder.categoryName.getText());
                List<Image> images = categories.get(i).getCategoryimages();
                Bundle bundle = new Bundle();
                bundle.putSerializable("IMAGESCATEGORY", (Serializable) images);
                intent.putExtra("BUNDLE", bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}
