package com.thuydev.app_ban_an.Account;

public class LoginResponse {
    private Account account;
    private String token;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
