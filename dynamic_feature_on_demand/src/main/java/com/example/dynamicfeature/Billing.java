package com.example.dynamicfeature;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.example.ungdung.Model.VatPham;
import com.google.android.play.core.splitcompat.SplitCompat;
import com.squareup.picasso.Picasso;

public class Billing extends AppCompatActivity implements BillingProcessor.IBillingHandler {

    public static final String PREF_FILE = "MyPref";
    public static final String PURCHASE_KEY = "purchase";
    public static final String PRODUCT_ID = "com.twouyu.coin001";
    private String sl = "";
    TextView txtTen, txtGia, txtSl, txtStatus;
    ImageView imgAvatar;
    RelativeLayout btnPay;
    private BillingProcessor bp;
    private TransactionDetails purchaseTransactionDetails = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        initView();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        VatPham vatPham = (VatPham) bundle.get("thongtinvatpham");

        sl = String.valueOf(bundle.get("soluong"));
        txtTen.setText(vatPham.getTenvatpham());
        txtGia.setText((vatPham.getGiavatpham())+" VNĐ");
        txtSl.setText(sl);
        Picasso.get().load(vatPham.getHinhanhvatpham())
                .placeholder(com.example.ungdung.R.drawable.ic_launcher_background)
                .error(com.example.ungdung.R.drawable.hot)
                .into(imgAvatar);

        bp = new BillingProcessor(this, "license key", this);
        bp.initialize();
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SplitCompat.install(this);
    }
    private boolean hasSubscription() {
        if (purchaseTransactionDetails != null) {
            return purchaseTransactionDetails.purchaseInfo != null;
        }
        return false;
    }
    @Override
    public void onBillingInitialized() {

        Log.d("Billing", "onBillingInitialized: ");

        String premium = getResources().getString(R.string.premium);

        purchaseTransactionDetails = bp.getSubscriptionTransactionDetails(premium);

        bp.loadOwnedPurchasesFromGoogle();

        btnPay.setOnClickListener(v -> {
            if (bp.isSubscriptionUpdateSupported()) {
                bp.subscribe(this, premium);
            } else {
                Log.d("Billing", "onBillingInitialized: Subscription updated is not supported");
            }
        });

        if (hasSubscription()) {
            txtStatus.setText("Status: Premium");
        } else {
            txtStatus.setText("Status: Free");
        }
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Log.d("MainActivity", "onProductPurchased: ");
    }

    @Override
    public void onPurchaseHistoryRestored() {
        Log.d("MainActivity", "onPurchaseHistoryRestored: ");

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Log.d("MainActivity", "onBillingError: ");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initView() {
        txtTen = (TextView) findViewById(R.id.txttensp);
        txtGia = (TextView) findViewById(R.id.txtgiasp);
        txtSl = (TextView) findViewById(R.id.soluongsp);
        txtStatus = (TextView) findViewById(R.id.status);
        btnPay = (RelativeLayout) findViewById(R.id.googlePayButton);
        imgAvatar = (ImageView) findViewById(R.id.imageview_billing);
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }

}