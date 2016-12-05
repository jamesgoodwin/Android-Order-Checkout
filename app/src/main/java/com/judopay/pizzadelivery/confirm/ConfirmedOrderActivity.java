package com.judopay.pizzadelivery.confirm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.judopay.pizzadelivery.R;
import com.judopay.view.SingleClickOnClickListener;

public class ConfirmedOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_confirmed_order);

        View doneButton = findViewById(R.id.done_button);

        doneButton.setOnClickListener(new SingleClickOnClickListener() {
            @Override
            public void doClick() {
                finish();
            }
        });
    }
}
