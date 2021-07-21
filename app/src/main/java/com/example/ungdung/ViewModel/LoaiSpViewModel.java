package com.example.ungdung.ViewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ungdung.Adapter.LoaiSpAdapter;
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
    private List<LoaiVatPham> mListLoaiSp;
    LoaiSpAdapter dsAdapter;

    int id=0;
    String loaisanpham ="";
    String hinhanhsanpham= "";

    public LoaiSpViewModel() {
        mListLoaiSpLiveData = new MutableLiveData<>();

        GetDulieuLoaiSp();
        initData();

    }
    private void initData() {
        mListLoaiSp = new ArrayList<>();
        dsAdapter = new LoaiSpAdapter(mListLoaiSp,getApplicationContext());
        mListLoaiSpLiveData.setValue(mListLoaiSp);
    }


    public MutableLiveData<List<LoaiVatPham>> getListLoaiSpLiveData() {
        return mListLoaiSpLiveData;
    }
    private void GetDulieuLoaiSp(){
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.pathloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    for(int i=0;i<response.length();i++){
                        try{
                            JSONObject jsonObject=response.getJSONObject(i);
                            id=jsonObject.getInt("idloaivatpham");
                            loaisanpham=jsonObject.getString("tenloaivatpham");
                            hinhanhsanpham=jsonObject.getString("hinhloaivatpham");
                            mListLoaiSp.add(new LoaiVatPham(id,loaisanpham,hinhanhsanpham));
                            dsAdapter.notifyDataSetChanged();
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
