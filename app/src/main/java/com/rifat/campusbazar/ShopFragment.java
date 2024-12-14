package com.rifat.campusbazar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private static final String DATA_URL = "https://api.myjson.online/v1/records/d0e1684b-daca-4794-8be4-cc27c397bc6a";

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_shop, container, false);

        Button btnShowAllProducts = rootView.findViewById(R.id.btnShowAllProducts);

        recyclerView = rootView.findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(requireContext(), productList, this::onProductClick);
        recyclerView.setAdapter(productAdapter);

        btnShowAllProducts.setOnClickListener(v -> fetchProductData());

        return rootView;
    }

    private void fetchProductData() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, DATA_URL, null,
                response -> {
                    List<ProductNetwork> networkProducts = parseJsonResponse(response);
                    updateProductList(networkProducts);
                },
                Throwable::printStackTrace
        );

        requestQueue.add(jsonObjectRequest);
    }

    private List<ProductNetwork> parseJsonResponse(JSONObject response) {
        List<ProductNetwork> networkProducts = new ArrayList<>();
        try {
            JSONObject dataObject = response.getJSONObject("data");
            JSONArray productsArray = dataObject.getJSONArray("products");

            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject productObj = productsArray.getJSONObject(i);
                String name = productObj.getString("name");
                String category = productObj.getString("category");

                JSONObject productInfo = productObj.getJSONObject("product_info");
                String price = productInfo.getString("price");
                String imageUrl = productInfo.getString("imageurl");

                networkProducts.add(new ProductNetwork(name, price, imageUrl, category));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return networkProducts;
    }

    private void updateProductList(List<ProductNetwork> networkProducts) {
        productList.clear();
        for (ProductNetwork networkProduct : networkProducts) {
            productList.add(new Product(
                    networkProduct.getName(),
                    networkProduct.getPrice(),
                    networkProduct.getImageUrl(),
                    networkProduct.getCategory()
            ));
        }
        productAdapter.notifyDataSetChanged();
    }

    private void onProductClick(Product product) {
        // Add product to cart
        CartRepository.getInstance().addItemToCart(product);
        Toast.makeText(requireContext(), product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
    }
}
