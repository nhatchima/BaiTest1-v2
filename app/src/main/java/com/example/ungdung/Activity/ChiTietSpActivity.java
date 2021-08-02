
package com.example.ungdung.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ungdung.BuildConfig;
import com.example.ungdung.Model.VatPham;
import com.example.ungdung.R;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.api.Billing;
import com.squareup.picasso.Picasso;

public class ChiTietSpActivity extends AppCompatActivity {


    Toolbar toolbar;
    public static RelativeLayout  btnpay;
    TextView txtname , txtgia;
    ImageView imgavatar;
    Spinner spinner;
    SplitInstallManager splitInstallManager;
    ProgressDialog progressDialog;
    private int mySessionId;
    private int soluong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);

        initView();
        CatchEventSpinner();
        ActionToolBar();


        progressDialog = new ProgressDialog(ChiTietSpActivity.this);
        splitInstallManager = SplitInstallManagerFactory.create(this);

        /**
         * Get information put from DanhSachActivity to show data
         */
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        VatPham vatPham = (VatPham) bundle.get("thongtinvatpham");
        txtname.setText(vatPham.getTenvatpham());
        txtgia.setText((vatPham.getGiavatpham())+" VNĐ");
        Picasso.get().load(vatPham.getHinhanhvatpham())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.hot)
                .into(imgavatar);

        /**
         * Call dynamic feature containing function payment
         */
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View  v) {
                soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                startDownloading();
            }
        });
    }
    /**
     *  Creates a listener for request status updates.
     *  Add dynamic module to base module
     *
     */
    private void startDownloading() {
        SplitInstallRequest request = SplitInstallRequest
                .newBuilder()
                .addModule("dynamic_feature_on_demand")
                .build();
        SplitInstallStateUpdatedListener listener = splitInstallSessionState -> {
            if( splitInstallSessionState.sessionId()==mySessionId){
                switch (splitInstallSessionState.status()){
                    case SplitInstallSessionStatus.DOWNLOADING:
                        progressDialog.setTitle("Downloading payment");
                        progressDialog.setMessage("Downloading");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.INSTALLED:
                        Intent intent = new Intent(ChiTietSpActivity.this, Billing.class);
                        Bundle bundle = getIntent().getExtras();
                        VatPham vatPham = (VatPham) bundle.get("thongtinvatpham");
                        bundle.putSerializable("thongtinvatpham",vatPham);
                        bundle.putSerializable("soluong",soluong);
                        intent.putExtras(bundle);
                        intent.setClassName(BuildConfig.APPLICATION_ID,"com.example.dynamicfeature.Billing");
                        startActivity(intent);
                        break;
                    case SplitInstallSessionStatus.CANCELED:
                        break;
                    case SplitInstallSessionStatus.CANCELING:
                        progressDialog.setTitle("Cancel payment");
                        progressDialog.setMessage("Canceling");
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.DOWNLOADED:
                        break;
                    case SplitInstallSessionStatus.FAILED:
                        break;
                    case SplitInstallSessionStatus.INSTALLING:
                        progressDialog.setTitle("Installing payment");
                        progressDialog.setMessage("Installing");
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.PENDING:
                        progressDialog.setTitle("Payment Pending");
                        progressDialog.setMessage("Pending");
                        progressDialog.show();
                        break;
                    case SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION:
                        break;
                    case SplitInstallSessionStatus.UNKNOWN:
                        break;

                }
            }
        };
        splitInstallManager.registerListener(listener);
//        splitInstallManager.startInstall(request).addOnFailureListener(e -> Log.d("GotException","Exception"+e))
//                .addOnSuccessListener(sessionId -> mySessionId = sessionId);
        splitInstallManager.startInstall(request).addOnSuccessListener(new OnSuccessListener<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                Toast.makeText(getApplicationContext(), "Download success", Toast.LENGTH_SHORT).show();
            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), "Download failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    /**
     * Show spinner
     * @return
     */
    public void CatchEventSpinner() {
        Integer[] soluong = new Integer [] {1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter= new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }
    /**
     * Show Toolbar on the top
     */
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

}