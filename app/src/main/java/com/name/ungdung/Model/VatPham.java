package com.name.ungdung.Model;

import java.io.Serializable;

public class VatPham implements Serializable {
    private int id;
    private int idloaivatpham;
    private String tenvatpham;
    private String giavatpham;
    private int hinhanhvatpham;

    public VatPham(int id, int idloaivatpham, String tenvatpham, String giavatpham, int hinhanhvatpham) {
        this.id = id;
        this.idloaivatpham = idloaivatpham;
        this.tenvatpham = tenvatpham;
        this.giavatpham = giavatpham;
        this.hinhanhvatpham = hinhanhvatpham;
    }

    public VatPham() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdloaivatpham() {
        return idloaivatpham;
    }

    public void setIdloaivatpham(int idloaivatpham) {
        this.idloaivatpham = idloaivatpham;
    }

    public String getTenvatpham() {
        return tenvatpham;
    }

    public void setTenvatpham(String tenvatpham) {
        this.tenvatpham = tenvatpham;
    }

    public String getGiavatpham() {
        return giavatpham;
    }

    public void setGiavatpham(String giavatpham) {
        this.giavatpham = giavatpham;
    }

    public int getHinhanhvatpham() {
        return hinhanhvatpham;
    }

    public void setHinhanhvatpham(int hinhanhvatpham) {
        this.hinhanhvatpham = hinhanhvatpham;
    }
}
