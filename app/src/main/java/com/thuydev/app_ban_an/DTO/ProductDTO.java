package com.thuydev.app_ban_an.DTO;

public class ProductDTO {
    String _id,Image,NameProduct,IDCategory;
    int Price;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "_id=" + _id + '\n' +
                "Image=" + Image + '\n' +
                "NameProduct=" + NameProduct + '\n' +
                "Price=" + Price +
                '}';
    }


    public ProductDTO() {
    }

    public ProductDTO(String image, String nameProduct, String IDCategory, int price) {
        Image = image;
        NameProduct = nameProduct;
        this.IDCategory = IDCategory;
        Price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getIDCategory() {
        return IDCategory;
    }

    public void setIDCategory(String IDCategory) {
        this.IDCategory = IDCategory;
    }
}
