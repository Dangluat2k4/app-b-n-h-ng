package com.thuydev.app_ban_an.DTO;

public class ProductCart {
    String IDProduct,Size;
    int Amount;
    int Price;

    public String getIDProduct() {
        return IDProduct;
    }

    public ProductCart(String IDProduct, int amount) {
        this.IDProduct = IDProduct;
        Amount = amount;
    }

    public ProductCart() {
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public ProductCart(String IDProduct, int amount, int price) {
        this.IDProduct = IDProduct;
        Amount = amount;
        Price = price;
    }

    public ProductCart(String IDProduct, String size, int amount, int price) {
        this.IDProduct = IDProduct;
        Size = size;
        Amount = amount;
        Price = price;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public void setIDProduct(String IDProduct) {
        this.IDProduct = IDProduct;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
