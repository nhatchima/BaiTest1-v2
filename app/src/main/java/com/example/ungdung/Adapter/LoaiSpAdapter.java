package com.example.ungdung.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ungdung.Model.LoaiVatPham;
import com.example.ungdung.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoaiSpAdapter extends BaseAdapter {

    List<LoaiVatPham> loaiVatPhams;
    Context context;

    public LoaiSpAdapter(List<LoaiVatPham> loaiVatPhams, Context context) {
        this.loaiVatPhams = loaiVatPhams;
        this.context = context;
    }

    public void setLoaiVatPhams(List<LoaiVatPham> listLoaiVatPham){
        loaiVatPhams = listLoaiVatPham;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return loaiVatPhams.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiVatPhams.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public  class ViewHolder {
        TextView tvName;
        ImageView imgAvatar;

    }
    public void release(){
        context = null;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder= null;
        if(view ==null){
            viewHolder= new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.lv_loaivatpham,null);
            viewHolder.tvName = (TextView) view.findViewById(R.id.txtName);
            viewHolder.imgAvatar =(ImageView) view.findViewById(R.id.avatar);
            view.setTag(viewHolder);


        } else{
            viewHolder= (ViewHolder)view.getTag();
        }
        LoaiVatPham loaiVatPham=(LoaiVatPham) getItem(i);
        viewHolder.tvName.setText(loaiVatPham.getName());
        Picasso.get().load(loaiVatPham.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_baseline_arrow_back_24)
                .into(viewHolder.imgAvatar);

        return view;
    }
}
