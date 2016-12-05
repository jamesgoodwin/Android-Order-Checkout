package com.judopay.pizzadelivery.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.judopay.pizzadelivery.OrderItem;
import com.judopay.pizzadelivery.R;
import com.judopay.pizzadelivery.checkout.CheckoutActivity;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements OrderItemImageAdapter.OnItemSelected {

    private ArrayList<OrderItem> orderItems;
    private LayerDrawable cartMenuIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);
        setTitle(getString(R.string.judo_pizza));

        this.orderItems = new ArrayList<>();

        RecyclerView pizzaList = (RecyclerView) findViewById(R.id.pizza_list);

        OrderItemImageAdapter adapter = new OrderItemImageAdapter(PizzaItems.getPizzaItems());
        adapter.setOnItemSelected(this);

        pizzaList.setAdapter(adapter);
        pizzaList.setLayoutManager(new GridLayoutManager(this, 2));
        pizzaList.addItemDecoration(new OrderItemDecoration(this, R.dimen.small_padding));
    }

    @Override
    public void onItemSelected(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        setBadgeCount(this, cartMenuIcon, String.valueOf(orderItems.size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_menu, menu);

        cartMenuIcon = (LayerDrawable) menu.findItem(R.id.action_cart).getIcon();
        setBadgeCount(this, cartMenuIcon, String.valueOf(orderItems.size()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(this, CheckoutActivity.class);
                intent.putParcelableArrayListExtra(CheckoutActivity.ORDER_ITEMS_EXTRA, orderItems);

                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    // handle order item clicked
    // add to cart
    // handle click on cart to launch checkoutActivity

}