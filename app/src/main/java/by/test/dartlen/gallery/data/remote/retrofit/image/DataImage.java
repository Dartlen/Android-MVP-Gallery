package by.test.dartlen.gallery.data.remote.retrofit.image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dartlen on 28.10.2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataImage {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("date")
    @Expose
    private Integer date;

    @SerializedName("lat")
    @Expose
    private Float lat;

    @SerializedName("lng")
    @Expose
    private Float lng;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }
}

