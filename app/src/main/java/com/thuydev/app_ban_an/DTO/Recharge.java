package com.thuydev.app_ban_an.DTO;

import java.util.List;

public class Recharge {
  String _id,IDUser,Email,Time;
  Long Money,Status;

    public Recharge() {
    }

    public Recharge(String IDUser, String email, String time, Long money) {
        this.IDUser = IDUser;
        Email = email;
        Time = time;
        Money = money;
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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public Long getMoney() {
        return Money;
    }

    public void setMoney(Long money) {
        Money = money;
    }

    public Long getStatus() {
        return Status;
    }

    public void setStatus(Long status) {
        Status = status;
    }
}
