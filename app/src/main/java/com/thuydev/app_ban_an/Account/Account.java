package com.thuydev.app_ban_an.Account;

import java.util.List;

public class Account {
    String _id, Email, Password, FullName,NumberPhone,Avatar,MyAddress;
    int Status,Credit,Level;
    List<String> Address;
    public Account account = this;

    public Account() {
    }

    public Account(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getMyAddress() {
        return MyAddress;
    }

    public void setMyAddress(String myAddress) {
        MyAddress = myAddress;
    }

    public List<String> getAddress() {
        return Address;
    }

    public void setAddress(List<String> address) {
        Address = address;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getCredit() {
        return Credit;
    }

    public void setCredit(int credit) {
        Credit = credit;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getNumberPhone() {
        return NumberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        NumberPhone = numberPhone;
    }

    public void SetAccount(Account nAccount){
        this.account = nAccount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "_id='" + _id + '\'' +
                ", Email='" + Email + '\'' +
                ", FullName='" + FullName + '\'' +
                ", NumberPhone='" + NumberPhone + '\'' +
                ", Avatar='" + Avatar + '\'' +
                ", MyAddress='" + MyAddress + '\'' +
                '}';
    }
}
