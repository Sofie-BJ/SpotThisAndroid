package com.example.spotthis;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoryViewAdapter extends RecyclerView.Adapter<CategoryViewAdapter.CategoryViewHolder> {

    List<Image> images;

    CategoryViewAdapter(List<Image> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, true);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        Image image = images.get(i);
        Uri path = Uri.parse(image.getUri());
        categoryViewHolder.categoryImage.setImageURI(path);
        categoryViewHolder.categoryName.setText(image.getCategories().get(0));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        View view;
        CardView cardView;
        ImageView categoryImage;
        TextView categoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                //    intent.putExtra(Intent.EXTRA_TEXT, )
                }
            });
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            categoryImage = (ImageView) itemView.findViewById(R.id.category_photo);
            categoryName = (TextView) itemView.findViewById(R.id.category_name);



        }
    }
}
