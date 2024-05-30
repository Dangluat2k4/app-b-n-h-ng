package com.thuydev.app_ban_an.DTO;

public class ProductCart {
    String IDProduct;
    int Amount;
    int Price;

    public String getIDProduct() {
        return IDProduct;
    }

    public ProductCart(String IDProduct, int amount) {
        this.IDProduct = IDProduct;
        Amount = amount;
    }

    public ProductCart(String IDProduct, int amount, int price) {
        this.IDProduct = IDProduct;
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
