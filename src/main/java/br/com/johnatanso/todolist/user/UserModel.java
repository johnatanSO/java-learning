package br.com.johnatanso.todolist.user;

import java.util.UUID;

public class UserModel {
    private final UUID _id;
    public String username;
    public String email;
    public String password;

    public UserModel(String username, String email, String password) {
        this._id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
    };

    public UUID getId() {
        return this._id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}