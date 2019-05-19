package com.example.spotthis;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageURI(Uri.parse(images.get(position).getUri()));
        holder.textView.setText(images.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.imagename);

        }
    }
}
