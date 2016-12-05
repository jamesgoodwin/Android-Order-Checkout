package com.judopay.pizzadelivery.cardscanning;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.judopay.Judo;
import com.judopay.model.Card;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class CardScanningActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 201;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, CardIOActivity.class);
        intent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true);
        intent.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false);

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_CODE == requestCode) {
            CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
            if (scanResult != null) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(Judo.JUDO_CARD, new Card.Builder()
                        .setCardNumber(scanResult.cardNumber)
                        .build());

                setResult(Judo.RESULT_CARD_SCANNED, resultIntent);
            }
            finish();
        }
    }
}
