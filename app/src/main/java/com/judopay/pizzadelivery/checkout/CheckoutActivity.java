package com.judopay.pizzadelivery.checkout;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wallet.Cart;
import com.google.android.gms.wallet.FullWallet;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.PaymentMethodTokenizationParameters;
import com.google.android.gms.wallet.PaymentMethodTokenizationType;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.gms.wallet.fragment.SupportWalletFragment;
import com.google.android.gms.wallet.fragment.WalletFragmentInitParams;
import com.google.android.gms.wallet.fragment.WalletFragmentMode;
import com.google.android.gms.wallet.fragment.WalletFragmentOptions;
import com.judopay.Judo;
import com.judopay.PaymentActivity;
import com.judopay.model.Currency;
import com.judopay.pizzadelivery.OrderItem;
import com.judopay.pizzadelivery.R;
import com.judopay.pizzadelivery.cardscanning.CardScanningActivity;
import com.judopay.pizzadelivery.confirm.ConfirmedOrderActivity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CheckoutActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final String ORDER_ITEMS_EXTRA = "OrderItems";

    private static final int REQUEST_CODE = 101;
    private static final int MASKED_WALLET_REQUEST = 401;
    private static final int FULL_WALLET_REQUEST = 402;

    private static DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,###.00");

    private RecyclerView orderItemsList;
    private View payButton;
    private TextView subtotalText;
    private TextView totalText;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.review_your_order));
        setContentView(R.layout.activity_checkout);

        orderItemsList = (RecyclerView) findViewById(R.id.order_items_list);
        subtotalText = (TextView) findViewById(R.id.subtotal_text);
        totalText = (TextView) findViewById(R.id.total_text);
        payButton = findViewById(R.id.pay_button);

        ArrayList<? extends OrderItem> orderItems = getIntent().getParcelableArrayListExtra(ORDER_ITEMS_EXTRA);
        initializeView(orderItems);

        if (savedInstanceState == null) {
            createGoogleApiClient();
            initializeAndroidPayButton();
        }
    }

    private void initializeAndroidPayButton() {
        PaymentMethodTokenizationParameters parameters = PaymentMethodTokenizationParameters.newBuilder()
                .setPaymentMethodTokenizationType(PaymentMethodTokenizationType.NETWORK_TOKEN)
                .addParameter("publicKey", getString(R.string.public_key))
                .build();

        WalletFragmentOptions options = WalletFragmentOptions.newBuilder()
                .setEnvironment(WalletConstants.ENVIRONMENT_PRODUCTION)
                .setTheme(WalletConstants.THEME_LIGHT)
                .setMode(WalletFragmentMode.BUY_BUTTON)
                .build();

        SupportWalletFragment walletFragment = SupportWalletFragment.newInstance(options);
        MaskedWalletRequest walletRequest = MaskedWalletRequest.newBuilder()
                .setMerchantName("Judopay Pizza")
                .setCurrencyCode("GBP")
                .setEstimatedTotalPrice("5.00")
                .setPaymentMethodTokenizationParameters(parameters)
                .setCart(Cart.newBuilder()
                        .setCurrencyCode("GBP")
                        .setTotalPrice("5.00")
                        .build())
                .build();

        WalletFragmentInitParams startParams = WalletFragmentInitParams.newBuilder()
                .setMaskedWalletRequest(walletRequest)
                .setMaskedWalletRequestCode(MASKED_WALLET_REQUEST)
                .build();

        walletFragment.initialize(startParams);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.android_pay_button, walletFragment)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case MASKED_WALLET_REQUEST:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    MaskedWallet maskedWallet = data.getParcelableExtra(WalletConstants.EXTRA_MASKED_WALLET);
                    requestFullWallet(maskedWallet);
                }
                break;
            case FULL_WALLET_REQUEST:
                if (resultCode == Activity.RESULT_OK && data.hasExtra(WalletConstants.EXTRA_FULL_WALLET)) {
                    FullWallet fullWallet = data.getParcelableExtra(WalletConstants.EXTRA_FULL_WALLET);
                    // call judoNative SDK to perform payment
                }
                break;
        }
    }

    private void requestFullWallet(MaskedWallet maskedWallet) {
        FullWalletRequest fullWalletRequest = FullWalletRequest.newBuilder()
                .setGoogleTransactionId(maskedWallet.getGoogleTransactionId())
                .setCart(Cart.newBuilder()
                        .setCurrencyCode("GBP")
                        .setTotalPrice("5.00")
                        .build())
                .build();

        Wallet.Payments.loadFullWallet(googleApiClient, fullWalletRequest, FULL_WALLET_REQUEST);
    }

    private void createGoogleApiClient() {
        this.googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wallet.API, new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_PRODUCTION)
                        .build())
                .enableAutoManage(this, this)
                .build();

    }

    private void initializeView(List<? extends OrderItem> orderItems) {
        OrderItemPriceAdapter adapter = new OrderItemPriceAdapter(orderItems);

        orderItemsList.setAdapter(adapter);
        orderItemsList.setLayoutManager(new LinearLayoutManager(this));
        orderItemsList.setHasFixedSize(true);
        orderItemsList.setNestedScrollingEnabled(false);
        orderItemsList.setOverScrollMode(View.OVER_SCROLL_NEVER);

        BigDecimal subtotal = getSubtotal(orderItems);

        subtotalText.setText(getString(R.string.pounds_format, NUMBER_FORMAT.format(subtotal)));
        final BigDecimal total = subtotal.add(new BigDecimal("0.25"));

        totalText.setText(getString(R.string.pounds_format, NUMBER_FORMAT.format(total)));

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                new Judo.Builder()
                        .setApiToken("823Eja2fEM6E9NAE")
                        .setApiSecret("382df6f458294f49f02f073e8f356f8983e2460631ea1b4c8ed4c3ee502dcbe6")
                        .setJudoId("100407-196")
                        .setEnvironment(Judo.SANDBOX)
                        .setAmount(total.toString())
                        .setCurrency(Currency.GBP)
                        .setConsumerRef(UUID.randomUUID().toString())
                        .build();
            }
        });
    }

    private PendingIntent getCardScanningIntent() {
        Intent cardScanIntent = new Intent(CheckoutActivity.this, CardScanningActivity.class);
        return PendingIntent.getActivity(CheckoutActivity.this, 1, cardScanIntent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void showErrorMessage() {
        Toast.makeText(this, "Error performing payment, please try again", Toast.LENGTH_LONG).show();
    }

    private void showOrderConfirmation() {
        Intent intent = new Intent(this, ConfirmedOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_TASK_ON_HOME);

        startActivity(intent);
        finish();
    }

    private BigDecimal getSubtotal(List<? extends OrderItem> orderItems) {
        BigDecimal subtotal = new BigDecimal(0);
        for (OrderItem orderItem : orderItems) {
            subtotal = subtotal.add(orderItem.getPrice());
        }
        return subtotal;
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
