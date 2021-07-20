package com.example.ungdung.Model;

import java.io.Serializable;

public class VatPham implements Serializable {
    private int id;
    private int idloaivatpham;
    private String tenvatpham;
    private Integer giavatpham;
    private String hinhanhvatpham;

    public VatPham(int id, int idloaivatpham, String tenvatpham, Integer giavatpham, String hinhanhvatpham) {
        this.id = id;
        this.idloaivatpham = idloaivatpham;
        this.tenvatpham = tenvatpham;
        this.giavatpham = giavatpham;
        this.hinhanhvatpham = hinhanhvatpham;
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

    public Integer getGiavatpham() {
        return giavatpham;
    }

    public void setGiavatpham(Integer giavatpham) {
        this.giavatpham = giavatpham;
    }

    public String getHinhanhvatpham() {
        return hinhanhvatpham;
    }

    public void setHinhanhvatpham(String hinhanhvatpham) {
        this.hinhanhvatpham = hinhanhvatpham;
    }
}
