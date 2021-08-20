package com.name.dynamicfeature;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.android.play.core.splitcompat.SplitCompat;
import com.name.ungdung.Model.VatPham;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Billing extends AppCompatActivity implements PurchasesUpdatedListener {


    public BillingClient billingClient;
    ConsumeResponseListener listener;
    List<SkuDetails> skuDetails;

    private String sl = "";
    TextView txtTen, txtGia, txtSl, txtStatus;
    ImageView imgAvatar;
    RelativeLayout btnPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        initView();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        skuDetails = new ArrayList<>();
        VatPham vatPham = (VatPham) bundle.get("thongtinvatpham");

        sl = String.valueOf(bundle.get("soluong"));
        txtTen.setText(vatPham.getTenvatpham());
        txtGia.setText((vatPham.getGiavatpham()) + " VNĐ");
        txtSl.setText(sl);
        Picasso.get().load(vatPham.getHinhanhvatpham())
                .placeholder(com.name.ungdung.R.drawable.ic_launcher_background)
                .error(com.name.ungdung.R.drawable.hot)
                .into(imgAvatar);

    }
    public void onClick(View v) {
//        if (!readyToPurchase) {
//            showToast("Billing not initialized.");
//            return;
//        }
        switch (v.getId()) {
            case R.id.googlePayButton:
//                bp.purchase(this,PRODUCT_ID);
                setBillingClient();
                break;
            default:
                break;
        }
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SplitCompat.install(this);
    }
    private void setBillingClient() {

        listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull @org.jetbrains.annotations.NotNull BillingResult billingResult, @NonNull @org.jetbrains.annotations.NotNull String s) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    Toast.makeText(Billing.this, "OK", Toast.LENGTH_SHORT).show();
                }
            }
        };
        billingClient = BillingClientClientSetup.getInstance(this, this);
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(Billing.this, "Billing service disconnected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBillingSetupFinished(@NonNull @NotNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){

                    //QueryResult
                    List<String> skuList = new ArrayList<> ();
                    skuList.add("com.twouyu.coin001");
                    skuList.add("com.twouyu.coin005");
                    skuList.add("com.twouyu.coin010");
                    skuList.add("com.twouyu.coin025");
                    skuList.add("com.twouyu.coin050");
                    skuList.add("com.twouyu.coin100");
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                    billingClient.querySkuDetailsAsync(params.build(),
                            new SkuDetailsResponseListener() {
                                @Override
                                public void onSkuDetailsResponse(@NotNull BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                                    // Process the result.
                                    if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){

                                        if (skuDetailsList != null && skuDetailsList.size() > 0) {
                                            skuDetails = skuDetailsList;
                                            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                                    .setSkuDetails(skuDetails.get(skuDetails.size() - 1))
                                                    .build();

                                            int responseCode = billingClient.launchBillingFlow(Billing.this, billingFlowParams).getResponseCode();
                                            Log.d("TAG", "onSkuDetailsResponse: " + responseCode);
                                        } else
                                            Toast.makeText(Billing.this, "Error", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                } else Toast.makeText(Billing.this, "Error code: " + billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void handlePurchase(Purchase purchase) {

        ConsumeParams consumeParams = ConsumeParams.newBuilder()
                .setPurchaseToken(purchase.getPurchaseToken())
                .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, @NotNull String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {

                    Toast.makeText(Billing.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
    }
    @Override
    public void onPurchasesUpdated(@NonNull @NotNull BillingResult billingResult, List<Purchase> list) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
            for (Purchase purchase : list) {
                handlePurchase(purchase);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
        } else {
            // Handle any other error codes.
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
//    @Override
//    protected void onResume() {
//        super.onResume();
//        updateTextViews();
//    }
//
//    @Override
//    public void onDestroy() {
//        if (bp != null) {
//            bp.release();
//            finish();
//        }
//        super.onDestroy();
//    }
}