package com.rifat.campusbazar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<Integer> sliderImages;

    public SliderAdapter(List<Integer> sliderImages) {
        this.sliderImages = sliderImages;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.sliderImageView.setImageResource(sliderImages.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderImages.size();
    }

    public static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView sliderImageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            sliderImageView = itemView.findViewById(R.id.sliderImage);
        }
    }
}
