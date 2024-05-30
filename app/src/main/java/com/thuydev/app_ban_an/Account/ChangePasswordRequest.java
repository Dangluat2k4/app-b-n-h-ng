package com.thuydev.app_ban_an.Account;

public class ChangePasswordRequest {
    private String email, newPassword;
    public ChangePasswordRequest(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "email='" + email + '\'' +
                "newPassword='" + newPassword + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
