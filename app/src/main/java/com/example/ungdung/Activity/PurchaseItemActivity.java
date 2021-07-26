package com.example.ungdung.Activity;



import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.example.ungdung.Model.VatPham;
import com.example.ungdung.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;

public class PurchaseItemActivity extends ChiTietSpActivity {

    Toolbar toolbar;
    BillingClient billingClient;
    ConsumeResponseListener consumeResponseListener;
    TextView txtThongbao,txtName,txtGia;
    ImageView avatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_item);

        initView();
        ActionToolBar();
//        setupBillingClient();

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        VatPham vatPham = (VatPham) bundle.get("thongtinvatpham");
        txtName.setText(vatPham.getTenvatpham());
        txtGia.setText(decimalFormat.format(vatPham.getGiavatpham()) + " VND");
        Picasso.get().load(vatPham.getHinhanhvatpham())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.hot)
                .into(avatar);
    }

    private void initView() {
        txtThongbao = findViewById(R.id.txtthongbao);
        txtName= findViewById(R.id.txtName);
        txtGia= findViewById(R.id.txtGia);
        avatar = findViewById(R.id.avatar);
        toolbar = findViewById(R.id.toolbar_thanhtoan);
    }
    private void ActionToolBar() {
        //truyen vao thanh toolbar
        setSupportActionBar(toolbar);
        // hien thi nut back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setupBillingClient() {
        consumeResponseListener = (ConsumeResponseListener) (billingResult,s)->{
            if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
                Toast.makeText(this, "Consume OK", Toast.LENGTH_SHORT).show();

        };
//        billingClient = Security.getInstance(this, (PurchasesUpdatedListener) this);
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(PurchaseItemActivity.this, "You are disconnected from Billing Service", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBillingSetupFinished(@NonNull @NotNull BillingResult billingResult) {
                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    Toast.makeText(PurchaseItemActivity.this, "Success to connect billing", Toast.LENGTH_SHORT).show();
                    List<Purchase> purchases = billingClient.queryPurchases(BillingClient.SkuType.INAPP)
                                .getPurchasesList();
                    handleItemAlreadyPurchase(purchases);
                }else{
                    Toast.makeText(PurchaseItemActivity.this, "Error code" + billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleItemAlreadyPurchase(List<Purchase> purchases) {
        StringBuilder purchasedItem = new StringBuilder(txtThongbao.getText());
        for(Purchase purchase : purchases)
        {
            if(purchase.getSkus().equals("")){
                ConsumeParams consumeParams = ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();
                billingClient.consumeAsync(consumeParams,consumeResponseListener);
            }
            purchasedItem.append("\n" +purchase.getSkus()).append("\n");

        }
        txtThongbao.setText(purchasedItem.toString());
        txtThongbao.setVisibility(View.VISIBLE);
    }

}