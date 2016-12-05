package com.judopay.pizzadelivery.checkout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.judopay.pizzadelivery.OrderItem;
import com.judopay.pizzadelivery.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    public static final String ORDER_ITEMS_EXTRA = "OrderItems";

    private static DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,###.00");

    private RecyclerView orderItemsList;
    private View payButton;
    private TextView subtotalText;
    private TextView totalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.review_your_order));
        setContentView(R.layout.activity_checkout);

        orderItemsList = (RecyclerView) findViewById(R.id.order_items_list);
        payButton = findViewById(R.id.pay_button);
        subtotalText = (TextView) findViewById(R.id.subtotal_text);
        totalText = (TextView) findViewById(R.id.total_text);

        ArrayList<? extends OrderItem> orderItems = getIntent().getParcelableArrayListExtra(ORDER_ITEMS_EXTRA);
        initializeView(orderItems);
    }

    private void initializeView(List<? extends OrderItem> orderItems) {
        OrderItemPriceAdapter adapter = new OrderItemPriceAdapter(orderItems);

        orderItemsList.setAdapter(adapter);
        orderItemsList.setLayoutManager(new LinearLayoutManager(this));
        orderItemsList.setHasFixedSize(true);
        orderItemsList.setNestedScrollingEnabled(false);
        orderItemsList.setOverScrollMode(View.OVER_SCROLL_NEVER);

        BigDecimal subtotal = getSubtotal(orderItems);

        subtotalText.setText("£" + NUMBER_FORMAT.format(subtotal));
        BigDecimal total = subtotal.add(new BigDecimal("0.25"));

        totalText.setText("£" + NUMBER_FORMAT.format(total));

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // call payment screen
            }
        });
    }

    private BigDecimal getSubtotal(List<? extends OrderItem> orderItems) {
        BigDecimal subtotal = new BigDecimal(0);
        for(OrderItem orderItem : orderItems) {
            subtotal = subtotal.add(orderItem.getPrice());
        }
        return subtotal;
    }

    public void showOrderConfirmation() {

    }

}
