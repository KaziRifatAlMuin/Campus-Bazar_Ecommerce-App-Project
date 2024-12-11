package com.rifat.campusbazar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rifat.campusbazar.R;
import com.rifat.campusbazar.Category;
import com.rifat.campusbazar.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private RecyclerView categoryRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);

        // Set the category heading
        TextView categoryHeading = root.findViewById(R.id.header_category);
        categoryHeading.setText("Category"); // Setting the correct heading

        // Set up RecyclerView
        categoryRecyclerView = root.findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryRecyclerView.setAdapter(new CategoryAdapter(getContext(), getCategories()));

        return root;
    }

    private List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Tech", R.drawable.ic_tech));
        categories.add(new Category("Books", R.drawable.ic_books));
        categories.add(new Category("Equipments", R.drawable.ic_equipments));
        categories.add(new Category("Clothes", R.drawable.ic_clothes));
        categories.add(new Category("Others", R.drawable.ic_others));
        return categories;
    }
}
