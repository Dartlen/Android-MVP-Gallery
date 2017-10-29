package by.test.dartlen.gallery.data.remote.retrofit.image;

/**
 * Created by Dartlen on 28.10.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageData {

    @SerializedName("base64Image")
    @Expose
    private String base64Image;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("lat")
    @Expose
    private Integer lat;
    @SerializedName("lng")
    @Expose
    private Integer lng;

    public ImageData(String image,Integer date, Integer lat, Integer lng){
        this.base64Image=image;
        this.date=date;
        this.lat=lat;
        this.lng=lng;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer getLng() {
        return lng;
    }

    public void setLng(Integer lng) {
        this.lng = lng;
    }

}
