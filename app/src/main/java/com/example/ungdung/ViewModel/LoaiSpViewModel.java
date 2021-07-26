package com.example.ungdung.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdung.Model.LoaiVatPham;
import com.example.ungdung.Util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class LoaiSpViewModel extends ViewModel {

    private MutableLiveData<List<LoaiVatPham>> mListLoaiSpLiveData;


    public LoaiSpViewModel() {
        mListLoaiSpLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<LoaiVatPham>> getListLoaiSpLiveData() {
        return mListLoaiSpLiveData;
    }

    public void fetchData(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.pathloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<LoaiVatPham> loaiVatPhams = new ArrayList<>();
                int id=0;
                String loaisanpham ="";
                String hinhanhsanpham= "";
                if(response!=null){
                    for(int i=0;i<response.length();i++){
                        try{
                            JSONObject jsonObject=response.getJSONObject(i);
                            id=jsonObject.getInt("idloaivatpham");
                            loaisanpham=jsonObject.getString("tenloaivatpham");
                            hinhanhsanpham=jsonObject.getString("hinhloaivatpham");
                            loaiVatPhams.add(new LoaiVatPham(id,loaisanpham,hinhanhsanpham));
                            mListLoaiSpLiveData.setValue(loaiVatPhams);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
