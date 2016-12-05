package com.judopay.pizzadelivery.order;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.judopay.pizzadelivery.OrderItem;
import com.judopay.pizzadelivery.R;

import java.util.List;

import rx.functions.Action1;

class OrderItemImageAdapter extends RecyclerView.Adapter<OrderItemImageViewHolder> {

    private final List<OrderItem> orderItems;
    private OnItemSelected onItemSelected;

    OrderItemImageAdapter(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public interface OnItemSelected {
        void onItemSelected(OrderItem orderItem);
    }

    public void setOnItemSelected(OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    @Override
    public OrderItemImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_order_item, parent, false);

        return new OrderItemImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderItemImageViewHolder holder, int position) {
        holder.setOrderItem(orderItems.get(position));

        holder.onClick()
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        if (onItemSelected != null) {
                            onItemSelected.onItemSelected(orderItems.get(holder.getLayoutPosition()));
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }
}
