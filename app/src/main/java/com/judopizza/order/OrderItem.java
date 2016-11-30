package com.judopizza.order;

import java.math.BigDecimal;

public class OrderItem {

    private String name;
    private String imageUrl;
    private BigDecimal price;

    public OrderItem(String name,  String imageUrl, BigDecimal price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
