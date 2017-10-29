package by.test.dartlen.gallery.data.remote.retrofit.image;

/***
 * Created by Dartlen on 28.10.2017.
 */


import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;

public class ResponseDataImagePost {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("data")
    @Expose
    private DataImage data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DataImage getData() {
        return data;
    }

    public void setData(DataImage data) {
        this.data = data;
    }

}
