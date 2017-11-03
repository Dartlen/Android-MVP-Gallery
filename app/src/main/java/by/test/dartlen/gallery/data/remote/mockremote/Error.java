package by.test.dartlen.gallery.data.remote.mockremote;

/**
 * Created by Dartlen on 02.11.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("messege")
    @Expose
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessege(String messege) {
        this.message = messege;
    }

}