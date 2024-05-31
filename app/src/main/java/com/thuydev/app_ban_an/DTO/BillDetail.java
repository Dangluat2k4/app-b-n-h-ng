package com.thuydev.app_ban_an.DTO;

public class BillDetail {
    String _id,IDUser,IDBill,Date;
    int Amount,Total,Status;

    public BillDetail() {
    }


    public BillDetail(String IDUser, String IDBill, int status, String date, int amount, int total) {
        this.IDUser = IDUser;
        this.IDBill = IDBill;
        Status = status;
        Date = date;
        Amount = amount;
        Total = total;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
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

    public String getIDBill() {
        return IDBill;
    }

    public void setIDBill(String IDBill) {
        this.IDBill = IDBill;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
