package br.com.johnatanso.todolist.user;

public class UserRepository {
    public UserModel[] users;

    public UserModel createUser(UserModel userData) {

        return new UserModel(
                userData.username,
                userData.email,
                userData.password
        );
    };
}
