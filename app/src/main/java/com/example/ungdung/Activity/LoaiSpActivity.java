package com.example.ungdung.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ungdung.Adapter.LoaiSpAdapter;
import com.example.ungdung.MainActivity;
import com.example.ungdung.Model.LoaiVatPham;
import com.example.ungdung.R;
import com.example.ungdung.Util.CheckConnection;
import com.example.ungdung.ViewModel.LoaiSpViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.ungdung.Activity.GoogleSignInActivity.mGoogleSignInClient;


public class LoaiSpActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnSignOut;
    CircleImageView profileAvatar;
    public static TextView profileName, profileEmail;
    ListView listView;
    List<LoaiVatPham> LoaiVatPhams;
    private LoaiSpAdapter dsAdapter;
    private LoaiSpViewModel loaiSpViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_sp);

        initView();
        ActionToolBar();
//        GetDuLieuSpMoiNhat();
//        CatchOnItemListView();
//        GetDulieuLoaiSp();

        loaiSpViewModel = new ViewModelProvider(this).get(LoaiSpViewModel.class);
        loaiSpViewModel.getListLoaiSpLiveData().observe(this, new Observer<List<LoaiVatPham>>() {
            @Override
            public void onChanged(List<LoaiVatPham> loaiVatPhams) {
                dsAdapter.setLoaiVatPhams(loaiVatPhams);
                CatchOnItemListView(loaiVatPhams);
            }
        });

        loaiSpViewModel.fetchData();
        //Get du lieu tai khoan Google
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            profileName.setText(acct.getDisplayName());
            profileEmail.setText(acct.getEmail());
            Picasso.get().load(acct.getPhotoUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.hot)
                    .into(profileAvatar);
        }

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.btnsignout:
                        signOut();
                        revokeAccess();
                        FirebaseAuth.getInstance().signOut();
                        break;
                    // ...
                }
            }
        });

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LoaiSpActivity.this, "Đăng Xuất Thành Công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
    }
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LoaiSpActivity.this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void CatchOnItemListView(List<LoaiVatPham> loaiVatPhams) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(LoaiSpActivity.this, DanhSachSpActivity.class);
                            //truyen du lieu giua cac activity
                            intent.putExtra("idloaivatpham",loaiVatPhams.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast(getApplicationContext(),"Check Internet Connection");
                        }
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(LoaiSpActivity.this, DanhSachSpActivity.class);
                            intent.putExtra("idloaivatpham",loaiVatPhams.get(i).getId());
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast(getApplicationContext(),"Check Internet Connection");
                        }
                        break;
                    default:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(LoaiSpActivity.this, LoaiSpActivity.class);
                            startActivity(intent);
                        }else{
                            CheckConnection.ShowToast(getApplicationContext(),"Check Internet Connection");
                        }
                }
            }
        });
    }
//    private void GetDuLieuSpMoiNhat() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathspmoinhat, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response != null) {
//                    int id = 0;
//                    String tenVatPham = "";
//                    int giaVatPham = 0;
//                    String hinhanhVatPham = "";
//                    int IdVatPham = 0;
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            id = jsonObject.getInt("idvatpham");
//                            tenVatPham = jsonObject.getString("tenvatpham");
//                            giaVatPham = jsonObject.getInt("giavatpham");
//                            hinhanhVatPham = jsonObject.getString("anhvatpham");
//                            IdVatPham = jsonObject.getInt("idloaivatpham");
//                            vatPhams.add(new VatPham(id, IdVatPham, tenVatPham, giaVatPham, hinhanhVatPham));
//                            spAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//    }
//
//    private void GetDulieuLoaiSp(){
//        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.pathloaisp, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if(response!=null){
//                    for(int i=0;i<response.length();i++){
//                        try{
//                            JSONObject jsonObject=response.getJSONObject(i);
//                            id=jsonObject.getInt("idloaivatpham");
//                            loaisanpham=jsonObject.getString("tenloaivatpham");
//                            hinhanhsanpham=jsonObject.getString("hinhloaivatpham");
//                            LoaiVatPhams.add(new LoaiVatPham(id,loaisanpham,hinhanhsanpham));
//                            dsAdapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
//    }

    private void initView() {
        profileAvatar = findViewById(R.id.profileavatar);
        profileName = findViewById(R.id.profilename);
        profileEmail = findViewById(R.id.profileemail);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        btnSignOut = (Button) findViewById(R.id.btnsignout);
        listView= (ListView) findViewById(R.id.lvdanhsachsp);

        LoaiVatPhams = new ArrayList<>();
        dsAdapter = new LoaiSpAdapter(LoaiVatPhams,getApplicationContext());
        listView.setAdapter(dsAdapter);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dsAdapter !=null){
            dsAdapter.release();
        }
    }
}