package com.thuydev.app_ban_an.DTO;

import java.util.List;

public class Bill {
    String _id,IDUser,IDSeller;
    List<ProductCart> IDProduct;

    public Bill() {
    }

    public Bill(String IDUser, String IDSeller, List<ProductCart> IDProduct) {
        this.IDUser = IDUser;
        this.IDSeller = IDSeller;
        this.IDProduct = IDProduct;
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

    public String getIDSeller() {
        return IDSeller;
    }

    public void setIDSeller(String IDSeller) {
        this.IDSeller = IDSeller;
    }

    public List<ProductCart> getIDProduct() {
        return IDProduct;
    }

    public void setIDProduct(List<ProductCart> IDProduct) {
        this.IDProduct = IDProduct;
    }
}
