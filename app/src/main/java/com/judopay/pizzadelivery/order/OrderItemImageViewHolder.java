package com.judopay.pizzadelivery.order;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.judopay.pizzadelivery.OrderItem;
import com.judopay.pizzadelivery.R;
import com.squareup.picasso.Picasso;

import rx.Observable;
import rx.Subscriber;

class OrderItemImageViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;

    OrderItemImageViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    public Observable<Void> onClick() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(null);
                        }
                    }
                });
            }
        });
    }

    void setOrderItem(OrderItem orderItem) {
        Picasso.with(imageView.getContext())
                .load(orderItem.getImageRes())
                .resize(600, 600).into(imageView);
    }
}
