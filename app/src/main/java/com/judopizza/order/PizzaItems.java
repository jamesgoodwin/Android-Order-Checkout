package com.judopizza.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PizzaItems {

    public static List<OrderItem> getPizzaItems() {
        ArrayList<OrderItem> items = new ArrayList<>();

        items.add(new OrderItem("Nduja", "https://media-cdn.tripadvisor.com/media/photo-s/08/cf/32/a1/pizza-nduja.jpg", new BigDecimal(9)));
        items.add(new OrderItem("Nduja", "https://media-cdn.tripadvisor.com/media/photo-s/08/cf/32/a1/pizza-nduja.jpg", new BigDecimal(9)));
        items.add(new OrderItem("Nduja", "https://media-cdn.tripadvisor.com/media/photo-s/08/cf/32/a1/pizza-nduja.jpg", new BigDecimal(9)));
        items.add(new OrderItem("Nduja", "https://media-cdn.tripadvisor.com/media/photo-s/08/cf/32/a1/pizza-nduja.jpg", new BigDecimal(9)));
        items.add(new OrderItem("Nduja", "https://media-cdn.tripadvisor.com/media/photo-s/08/cf/32/a1/pizza-nduja.jpg", new BigDecimal(9)));
        items.add(new OrderItem("Nduja", "https://media-cdn.tripadvisor.com/media/photo-s/08/cf/32/a1/pizza-nduja.jpg", new BigDecimal(9)));
        items.add(new OrderItem("Nduja", "https://media-cdn.tripadvisor.com/media/photo-s/08/cf/32/a1/pizza-nduja.jpg", new BigDecimal(9)));

        return items;
    }

}