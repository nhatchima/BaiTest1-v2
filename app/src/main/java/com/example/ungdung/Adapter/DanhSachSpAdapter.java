package com.example.ungdung.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ungdung.Activity.ChiTietSpActivity;
import com.example.ungdung.R;
import com.example.ungdung.Model.VatPham;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DanhSachSpAdapter extends RecyclerView.Adapter<DanhSachSpAdapter.ItemHolder> {

    Context context;
    List<VatPham> vatPhams;

    public DanhSachSpAdapter(Context context, List<VatPham> vatPhams) {
        this.context = context;
        this.vatPhams = vatPhams;
    }
    public void setVatPhams(List<VatPham> listVatPham){
        vatPhams = listVatPham;
        notifyDataSetChanged();
    }
    @NonNull
    @NotNull
    @Override
    public DanhSachSpAdapter.ItemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_danhsachvatpham, parent,false);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DanhSachSpAdapter.ItemHolder holder, int position) {
        final VatPham vatPham = vatPhams.get(position);
        holder.txttensp.setText(vatPham.getTenvatpham());
        DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá: "+ decimalFormat.format(vatPham.getGiavatpham())+" VNĐ");
        Picasso.get().load(vatPham.getHinhanhvatpham())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.hot)
                .into(holder.imghinhsp);
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToDetail(vatPham);
            }
        });
    }
    private void onClickGoToDetail(VatPham vatPham){
        Intent intent = new Intent(context, ChiTietSpActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("thongtinvatpham",vatPham);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return vatPhams.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public LinearLayout layoutItem;
        public ImageView imghinhsp;
        public TextView txttensp, txtgiasp;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            layoutItem = itemView.findViewById(R.id.layout_item);
            imghinhsp = itemView.findViewById(R.id.imageviewsp);
            txtgiasp = itemView.findViewById(R.id.txtgiasanpham);
            txttensp = itemView.findViewById(R.id.txttensanpham);
        }
    }
}
