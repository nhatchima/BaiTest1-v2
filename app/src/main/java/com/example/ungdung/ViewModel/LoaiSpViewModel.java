package com.example.ungdung.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ungdung.Model.LoaiVatPham;
import com.example.ungdung.Model.VatPham;
import com.example.ungdung.R;

import java.util.ArrayList;
import java.util.List;

public class LoaiSpViewModel extends ViewModel {

    private MutableLiveData<List<LoaiVatPham>> mListLoaiSpLiveData;
    private MutableLiveData<List<VatPham>> mListVatPhamLiveData;
    private List<LoaiVatPham> loaiVatPhamList;
    private List<VatPham> vatPhamList;
    public static final int RC_SIGN_IN = 0;

    public LoaiSpViewModel() {
        mListLoaiSpLiveData = new MutableLiveData<>();
        mListVatPhamLiveData = new MutableLiveData<>();
        initData();
    }

    public MutableLiveData<List<VatPham>> getListVatPhamLiveData() {
        return mListVatPhamLiveData;
    }

    public MutableLiveData<List<LoaiVatPham>> getListLoaiSpLiveData() {
        return mListLoaiSpLiveData;
    }
    public void initData(){
        loaiVatPhamList = new ArrayList<>();
        vatPhamList = new ArrayList<>();

        loaiVatPhamList.add(new LoaiVatPham(1,1,"Kiếm", R.drawable.sword1));
        loaiVatPhamList.add(new LoaiVatPham(2,2,"Khiên", R.drawable.shield1));

        mListLoaiSpLiveData.setValue(loaiVatPhamList);



    }
    public void returnData(int id ){
        if (id == 1){
            vatPhamList.add(new VatPham(1,1,"Kiếm gỗ","300.000 " ,R.drawable.sword2));
            vatPhamList.add(new VatPham(2,1,"Kiếm laze","200.000 " , R.drawable.sword3));
            vatPhamList.add(new VatPham(3,1,"Kiếm katana","100.000 " , R.drawable.sword4));
        } else{
            vatPhamList.add(new VatPham(4,2,"Khiên gỗ", "300.000 " ,R.drawable.shield2));
            vatPhamList.add(new VatPham(5,2,"Khiên bạc", "200.000 " ,R.drawable.shield3));
            vatPhamList.add(new VatPham(6,2,"Khiên đồng","100.000 " ,R.drawable.shield4));

        }
        mListVatPhamLiveData.setValue(vatPhamList);
    }



//    public void fetchData(){
//        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.pathloaisp, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                List<LoaiVatPham> loaiVatPhams = new ArrayList<>();
//                int id=0;
//                String loaisanpham ="";
//                String hinhanhsanpham= "";
//                if(response!=null){
//                    for(int i=0;i<response.length();i++){
//                        try{
//                            JSONObject jsonObject=response.getJSONObject(i);
//                            id=jsonObject.getInt("idloaivatpham");
//                            loaisanpham=jsonObject.getString("tenloaivatpham");
//                            hinhanhsanpham=jsonObject.getString("hinhloaivatpham");
//                            loaiVatPhams.add(new LoaiVatPham(id,loaisanpham,hinhanhsanpham));
//                            mListLoaiSpLiveData.setValue(loaiVatPhams);
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
}
