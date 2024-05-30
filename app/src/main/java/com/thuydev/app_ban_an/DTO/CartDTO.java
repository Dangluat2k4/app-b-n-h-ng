package com.thuydev.app_ban_an.DTO;

public class CartDTO {
    String _id;
    String IDUser;
    String IDProduct;
    String Size;
    Integer Amount;


    public CartDTO() {
    }

    public CartDTO(String IDUser, String IDProduct, String size, Integer amount) {
        this.IDUser = IDUser;
        this.IDProduct = IDProduct;
        Size = size;
        Amount = amount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getIDUser() {
        return IDUser;
    }

    public void setIDUser(String IDUser) {
        this.IDUser = IDUser;
    }

    public String getIDProduct() {
        return IDProduct;
    }

    public void setIDProduct(String IDProduct) {
        this.IDProduct = IDProduct;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
    }
}
