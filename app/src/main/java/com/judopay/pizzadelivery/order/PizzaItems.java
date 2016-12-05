package com.judopay.pizzadelivery.order;

import com.judopay.pizzadelivery.OrderItem;
import com.judopay.pizzadelivery.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PizzaItems {

    public static List<OrderItem> getPizzaItems() {
        ArrayList<OrderItem> items = new ArrayList<>();

        items.add(new OrderItem("Margherita", R.drawable.margherita_pizza, new BigDecimal(9)));
        items.add(new OrderItem("Marinara", R.drawable.marinara_pizza, new BigDecimal(9)));
        items.add(new OrderItem("Mushroom", R.drawable.mushroom_pizza, new BigDecimal(9)));
        items.add(new OrderItem("Olive and anchovies", R.drawable.olive_pizza, new BigDecimal(9)));
        items.add(new OrderItem("Pepperoni", R.drawable.pepperoni_pizza, new BigDecimal(9)));

        return items;
    }

}