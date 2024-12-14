package com.rifat.campusbazar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final Context context;
    private final List<Category> categories;
    private final Fragment parentFragment;

    public CategoryAdapter(Context context, List<Category> categories, Fragment parentFragment) {
        this.context = context;
        this.categories = categories;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryImage.setImageResource(category.getImageResId());
        holder.categoryName.setText(category.getName());
        holder.viewProductsButton.setOnClickListener(v -> {
            // Create a new instance of ShopFragment
            ShopFragment shopFragment = new ShopFragment();

            // Pass the category name to the ShopFragment using a Bundle
            Bundle bundle = new Bundle();
            bundle.putString("categoryName", category.getName());
            shopFragment.setArguments(bundle);

            // Replace the current fragment with ShopFragment
            ((FragmentActivity) context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(parentFragment.getId(), shopFragment)
                    .addToBackStack(null) // Add this transaction to the back stack
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;
        Button viewProductsButton;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryName);
            viewProductsButton = itemView.findViewById(R.id.viewProductsButton);
        }
    }
}