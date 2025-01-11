package com.epam.gym.atlass_gym.model.mapped;

public class LoginPasswordChange {

    private String login;
    private String password;
    private String newPassword;

    public LoginPasswordChange(String login, String password, String newPassword) {
        this.login = login;
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
