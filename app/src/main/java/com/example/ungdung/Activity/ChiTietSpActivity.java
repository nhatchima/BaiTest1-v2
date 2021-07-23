
package com.example.ungdung.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.billingclient.api.BillingClient;
import com.example.ungdung.R;
import com.example.ungdung.Model.VatPham;
import com.razorpay.Checkout;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSpActivity extends AppCompatActivity {

    BillingClient billingClient;
    Toolbar toolbar;
    RelativeLayout btnpay;
    TextView txtname , txtgia;
    ImageView imgavatar;
    Spinner spinner;
//    String tenchitiet = "";
//    int giachitiet = 0;
//    int idsp = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        initView();
        CatchEventSpinner();
        ActionToolBar();
        Checkout.clearUserData(getApplicationContext());

        // Nhậnn thông tin từ DanhSachSp truyền sang
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

        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietSpActivity.this, PurchaseItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("thongtinvatpham",vatPham);
                intent.putExtras(bundle);
                startActivity(intent);
//                if (billingClient.isReady()){
//                    SkuDetailsParams params = SkuDetailsParams.newBuilder()
//                            .setSkusList((Arrays.asList("","")))
//                            .setType(BillingClient.SkuType.INAPP)
//                            .build();
//                    billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
//                        @Override
//                        public void onSkuDetailsResponse(@NonNull @NotNull BillingResult billingResult, @Nullable @org.jetbrains.annotations.Nullable List<SkuDetails> list) {
//                             if(billingResult.getResponseCode()== BillingClient.BillingResponseCode.OK){
////                                 loadProductToRecyclerView(list);
//                             }else{
//                                 Toast.makeText(ChiTietSpActivity.this, "Error code ", Toast.LENGTH_SHORT).show();
//                             }
//                        }
//                    });
//                }
//                Toast.makeText(ChiTietSpActivity.this, "Thanh toan", Toast.LENGTH_SHORT).show();
            }
        });
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
}