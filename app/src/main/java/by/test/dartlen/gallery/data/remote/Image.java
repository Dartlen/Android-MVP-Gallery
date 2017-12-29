package by.test.dartlen.gallery.data.remote;

import com.google.firebase.database.IgnoreExtraProperties;

/***
 * Created by Dartlen on 28.12.2017.
 */

@IgnoreExtraProperties
public class Image {
    private String url;
    private Long date;
    private Double lat;
    private Double lng;

    public Image(){}

    public Image(String url, Long date, Double lat, Double lng){
        this.url  = url;
        this.date = date;
        this.lat  = lat;
        this.lng  = lng;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Long getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
