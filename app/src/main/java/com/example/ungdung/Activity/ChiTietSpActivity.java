
package com.example.ungdung.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.example.ungdung.Model.VatPham;
import com.example.ungdung.R;
import com.example.ungdung.Util.BillingClientSetUp;
import com.razorpay.Checkout;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChiTietSpActivity extends AppCompatActivity implements PurchasesUpdatedListener {

    private BillingClient billingClient;
    Toolbar toolbar;
    RelativeLayout btnpay;
    TextView txtname , txtgia;
    ImageView imgavatar;
    Spinner spinner;
    ConsumeResponseListener listener;
    List<SkuDetails> skuDetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        initView();
        CatchEventSpinner();
        ActionToolBar();
        Checkout.clearUserData(getApplicationContext());

        // Nhậnn thông tin từ DanhSachSp truyền sang để hiển thị chi tiết vật phẩm
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        VatPham vatPham = (VatPham) bundle.get("thongtinvatpham");
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        txtname.setText(vatPham.getTenvatpham());
        txtgia.setText(decimalFormat.format(vatPham.getGiavatpham()) + " VND");
        Picasso.get().load(vatPham.getHinhanhvatpham())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.hot)
                .into(imgavatar);

        skuDetails = new ArrayList<>();
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(ChiTietSpActivity.this, PurchaseItemActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("thongtinvatpham",vatPham);
//                intent.putExtras(bundle);
//                startActivity(intent);
                setBillingClient();

            }
        });
    }
    private void setBillingClient() {

        listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(@NonNull @org.jetbrains.annotations.NotNull BillingResult billingResult, @NonNull @org.jetbrains.annotations.NotNull String s) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    Toast.makeText(ChiTietSpActivity.this, "OK", Toast.LENGTH_SHORT).show();
                }
            }
        };
        billingClient = BillingClientSetUp.getInstance(this, this);
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                Toast.makeText(ChiTietSpActivity.this, "Billing service disconnected", Toast.LENGTH_SHORT).show();
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

                                            int responseCode = billingClient.launchBillingFlow(ChiTietSpActivity.this, billingFlowParams).getResponseCode();
                                            Log.d("TAG", "onSkuDetailsResponse: " + responseCode);
                                        } else
                                            Toast.makeText(ChiTietSpActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                } else Toast.makeText(ChiTietSpActivity.this, "Error code: " + billingResult.getResponseCode(), Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(ChiTietSpActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
    }
    @Override
    public void onPurchasesUpdated(@NonNull @NotNull BillingResult billingResult,  @org.jetbrains.annotations.Nullable List<Purchase> list) {
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
    //                  Razor Payment
//                Checkout checkout = new Checkout();
//
//                checkout.setKeyID("rzp_test_bDkqwPKKfkmVI5");
////                checkout.setImage(Integer.parseInt(hinhanh));
//
//                JSONObject object = new JSONObject();
//                try {
//                    object.put("name",tenchitiet);
//                    object.put("description","Số Lượng" + soluong);
//                    object.put("image",hinhanh);
//                    object.put("theme.color","#0093DD");
//                    object.put("currency","INR");
//                    object.put("amount",giasanpham);
//                    object.put("prefill.contact","0397625171");
//                    object.put("prefill.email","datnguyen@gmail.com");
//                    checkout.open(ChiTietSpActivity.this,object);
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
    public void CatchEventSpinner() {
        Integer[] soluong = new Integer [] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter= new ArrayAdapter<Integer>(getApplicationContext(),android.R.layout.simple_spinner_item,soluong);
        spinner.setAdapter(arrayAdapter);

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
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_chitietvatpham);
        btnpay = (RelativeLayout) findViewById(R.id.googlePayButton);
        txtname = (TextView) findViewById(R.id.txtchitietsp);
        txtgia = (TextView) findViewById(R.id.txtgiachitietsp);
        spinner = (Spinner) findViewById(R.id.spinnerchitietsp);
        imgavatar = (ImageView) findViewById(R.id.imageview_chitietsp);

    }
//    @Override
//    public void onPaymentSuccess(String s) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Payment ID");
//        builder.setMessage(s);
//        builder.show();
//    }
//
//    @Override
//    public void onPaymentError(int i, String s) {
//        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(billingClient!=null){
            billingClient.endConnection();
            }
        }

}