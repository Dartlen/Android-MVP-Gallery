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
    private Long date;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;

    public ImageData(String image,Long date, Double lat, Double lng){
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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}
