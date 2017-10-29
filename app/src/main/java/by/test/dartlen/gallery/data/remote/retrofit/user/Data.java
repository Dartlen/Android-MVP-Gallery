package by.test.dartlen.gallery.data.remote.retrofit.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dartlen on 26.10.2017.
 */

public class Data {

    @SerializedName("userId")
    @Expose
    private Integer userId;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("token")
    @Expose
    private String token;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
