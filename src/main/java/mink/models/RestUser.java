package mink.models;

public class RestUser {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        RestUser.user = user;
    }
}
