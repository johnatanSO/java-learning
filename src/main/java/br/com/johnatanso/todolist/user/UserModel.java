package br.com.johnatanso.todolist.user;

import java.util.UUID;

public class UserModel {
    public String username;
    public String email;
    public String password;
    public UUID _id;

    public UserModel(String username, String email, String password) {
        this._id = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
    };
}