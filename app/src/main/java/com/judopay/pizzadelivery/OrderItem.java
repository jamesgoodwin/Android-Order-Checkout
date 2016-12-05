package com.judopay.pizzadelivery;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

public class OrderItem implements Parcelable {

    private String name;
    private int imageRes;
    private BigDecimal price;

    public OrderItem(String name, int imageRes, BigDecimal price) {
        this.name = name;
        this.imageRes = imageRes;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.imageRes);
        dest.writeSerializable(this.price);
    }

    protected OrderItem(Parcel in) {
        this.name = in.readString();
        this.imageRes = in.readInt();
        this.price = (BigDecimal) in.readSerializable();
    }

    public static final Parcelable.Creator<OrderItem> CREATOR = new Parcelable.Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel source) {
            return new OrderItem(source);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
}
