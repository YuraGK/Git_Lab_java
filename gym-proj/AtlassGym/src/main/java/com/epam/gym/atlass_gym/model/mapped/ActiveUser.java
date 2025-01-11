package com.epam.gym.atlass_gym.model.mapped;

public class ActiveUser {

    private String username;
    private boolean isActive;

    public ActiveUser() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return isActive;
    }
}
