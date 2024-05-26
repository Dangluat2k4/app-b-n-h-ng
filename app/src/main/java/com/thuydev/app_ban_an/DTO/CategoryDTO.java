package com.thuydev.app_ban_an.DTO;

public class CategoryDTO {
    String _id,NameCategory;

    public CategoryDTO() {
    }

    public CategoryDTO(String _id, String nameCategory) {
        this._id = _id;
        NameCategory = nameCategory;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNameCategory() {
        return NameCategory;
    }

    public void setNameCategory(String nameCategory) {
        NameCategory = nameCategory;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "_id='" + _id + '\'' +
                ", NameCategory='" + NameCategory + '\'' +
                '}';
    }
}
