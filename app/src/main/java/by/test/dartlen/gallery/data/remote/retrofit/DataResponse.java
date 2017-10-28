package by.test.dartlen.gallery.data.remote.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/***
 * Created by Dartlen on 26.10.2017.
 */

public class DataResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private Data data;



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}