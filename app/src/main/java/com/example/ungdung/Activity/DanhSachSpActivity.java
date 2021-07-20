package com.example.ungdung.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdung.Adapter.DanhSachSpAdapter;
import com.example.ungdung.R;
import com.example.ungdung.Util.CheckConnection;
import com.example.ungdung.Util.Server;
import com.example.ungdung.Model.VatPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DanhSachSpActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<VatPham> vatPhams;
    DanhSachSpAdapter spAdapter;
    int idkiem = 0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_sp);

        initView();
        GetIdLoaiSanPham();
//        GetDuLieuSpMoiNhat();
        GetData(page);
        ActionToolBar();

    }
    //    private void GetDuLieuSpMoiNhat() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.pathspmoinhat, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response != null) {
//                    int id = 0;
//                    String tenVatPham = "";
//                    Integer giaVatPham = 0;
//                    String hinhanhVatPham = "";
//                    int IdVatPham = 0;
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            id = jsonObject.getInt("id");
//                            IdVatPham = jsonObject.getInt("idloaivatpham");
//                            tenVatPham = jsonObject.getString("tenvatpham");
//                            giaVatPham = jsonObject.getInt("giavatpham");
//                            hinhanhVatPham = jsonObject.getString("anhvatpham");
//                            vatPhams.add(new VatPham(id, IdVatPham,tenVatPham,giaVatPham, hinhanhVatPham));
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
    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.pathspkiem + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String tenVatPham = "";
                int giaVatPham = 0;
                String hinhanhVatPham = "";
                int IdVatPham = 0;
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            IdVatPham = jsonObject.getInt("idloaivatpham");
                            tenVatPham = jsonObject.getString("tenvatpham");
                            giaVatPham = jsonObject.getInt("giavatpham");
                            hinhanhVatPham = jsonObject.getString("anhvatpham");
                            vatPhams.add(new VatPham(id, IdVatPham,tenVatPham,giaVatPham, hinhanhVatPham));
                            spAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    CheckConnection.ShowToast(getApplicationContext(), "Loading Full");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idloaivatpham", String.valueOf(idkiem));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void GetIdLoaiSanPham() {
        idkiem = getIntent().getIntExtra("idloaivatpham", -1);
        Log.d("giatriloaisanpham", idkiem + "");
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
        toolbar = (Toolbar) findViewById(R.id.toolbar_danhsachvatpham);
        recyclerView = (RecyclerView) findViewById(R.id.lvchitietsp);
        vatPhams = new ArrayList<>();
        spAdapter = new DanhSachSpAdapter(getApplicationContext(),vatPhams);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(spAdapter);

    }
}