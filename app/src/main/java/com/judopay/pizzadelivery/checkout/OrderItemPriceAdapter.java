package com.judopay.pizzadelivery.checkout;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.judopay.pizzadelivery.OrderItem;
import com.judopay.pizzadelivery.R;

import java.util.List;

class OrderItemPriceAdapter extends RecyclerView.Adapter<OrderItemPriceViewHolder> {

    private final List<? extends OrderItem> orderItems;

    OrderItemPriceAdapter(List<? extends OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public OrderItemPriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_order_item_price, parent, false);

        return new OrderItemPriceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderItemPriceViewHolder holder, int position) {
        holder.setOrderItem(orderItems.get(position));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }
}
