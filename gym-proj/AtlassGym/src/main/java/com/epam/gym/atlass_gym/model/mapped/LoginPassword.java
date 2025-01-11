package com.epam.gym.atlass_gym.model.mapped;

public class LoginPassword {
    private String login;
    private String password;

    public LoginPassword(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
