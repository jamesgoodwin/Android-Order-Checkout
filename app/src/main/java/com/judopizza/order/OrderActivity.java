package com.judopizza.order;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.judopizza.R;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle(getString(R.string.judo_pizza));

        RecyclerView pizzaList = (RecyclerView) findViewById(R.id.pizza_list);

        pizzaList.setAdapter(new OrderItemAdapter());
        pizzaList.setLayoutManager(new GridLayoutManager(this, 2));
    }

    // handle order item clicked
    // add to cart
    // handle click on cart to launch checkoutActivity

}