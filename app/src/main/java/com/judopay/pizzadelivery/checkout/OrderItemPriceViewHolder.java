package com.judopay.pizzadelivery.checkout;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.judopay.pizzadelivery.OrderItem;
import com.judopay.pizzadelivery.R;

import java.text.DecimalFormat;

class OrderItemPriceViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameText;
    private final TextView priceText;

    private static DecimalFormat FORMAT = new DecimalFormat("#,###.00");

    OrderItemPriceViewHolder(View itemView) {
        super(itemView);

        this.nameText = (TextView) itemView.findViewById(R.id.order_item_name_text);
        this.priceText = (TextView) itemView.findViewById(R.id.order_item_price_text);
    }

    void setOrderItem(OrderItem orderItem) {
        this.nameText.setText(orderItem.getName());
        this.priceText.setText("£" + FORMAT.format(orderItem.getPrice()));
    }

}
