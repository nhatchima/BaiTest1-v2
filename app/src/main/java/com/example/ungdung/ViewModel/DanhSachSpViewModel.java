package com.example.ungdung.ViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdung.Model.LoaiVatPham;
import com.example.ungdung.Model.VatPham;
import com.example.ungdung.Util.CheckConnection;
import com.example.ungdung.Util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DanhSachSpViewModel extends ViewModel {
    private MutableLiveData<List<VatPham>> mutableLiveData = new MutableLiveData<List<VatPham>>();

    public DanhSachSpViewModel() {
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<VatPham>> getMutableLiveData() {
        return mutableLiveData;
    }
    public void fetchData(int countPage, int idkiem) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.pathspkiem + String.valueOf(countPage);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<VatPham> vatPhams = new ArrayList<>();
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

                        }
                        mutableLiveData.setValue(vatPhams);
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
}
