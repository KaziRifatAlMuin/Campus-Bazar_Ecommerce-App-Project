package com.rifat.campusbazar;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.rifat.campusbazar.databinding.OrderItemBinding;
import java.util.List;
import java.util.Map;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder> {

    private final List<Order> orderList;

    public MyOrdersAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderItemBinding binding = OrderItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new MyOrdersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class MyOrdersViewHolder extends RecyclerView.ViewHolder {

        private final OrderItemBinding binding;

        public MyOrdersViewHolder(@NonNull OrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Order order) {
            binding.orderTimestamp.setText(order.getTimestamp());
            binding.orderTotalPrice.setText("Total: ৳" + order.getTotalPrice());

            StringBuilder items = new StringBuilder();
            for (Map<String, Object> item : order.getItems()) {
                items.append("- ").append(item.get("name")).append("\n");
            }

            binding.orderItemsList.setText(items.toString().trim());
        }
    }
}
