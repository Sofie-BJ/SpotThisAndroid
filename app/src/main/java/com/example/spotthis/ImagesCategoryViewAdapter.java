package com.example.spotthis;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.spotthis.Models.Image;

import java.net.URI;
import java.util.List;

public class ImagesCategoryViewAdapter extends RecyclerView.Adapter<ImagesCategoryViewAdapter.ViewHolder> {

    List<Image> images;
    Context context;

    public ImagesCategoryViewAdapter(List<Image> images, Context context) {
        this.images = images;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_images_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (images != null) {
            holder.imageView.setImageURI(Uri.parse(images.get(position).getUri()));
            holder.textView.setText(images.get(position).getDescription());

            holder.linearLayoutCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ImageDetailActivity.class);
                    intent.putExtra("IMAGE", images.get(position));
                    context.startActivity(intent);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayoutCard;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayoutCard = itemView.findViewById(R.id.linearlayoutCard);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.imagename);

        }
    }
}
