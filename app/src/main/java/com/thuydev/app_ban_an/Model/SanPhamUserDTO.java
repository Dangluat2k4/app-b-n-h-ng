package com.thuydev.app_ban_an.Model;

public class SanPhamUserDTO {
    String NameProduct, Image;
    int Price;

    public SanPhamUserDTO() {
    }

    @Override
    public String toString() {
        return "SanPhamUserDTO{" +
                "NameProduct='" + NameProduct + '\n' +
                "Image='" + Image + '\n' +
                "Price=" + Price +
                '}';
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
