package com.example.ungdung.Model;

public class LoaiVatPham {
    private int id;
    private int idloaivatpham;
    private String name;
    private int image;

    public LoaiVatPham(int id, int idloaivatpham, String name, int image) {
        this.id = id;
        this.idloaivatpham = idloaivatpham;
        this.name = name;
        this.image = image;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
