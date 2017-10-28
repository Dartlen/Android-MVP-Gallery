package by.test.dartlen.gallery.data.remote.retrofit;

/**
 * Created by Dartlen on 27.10.2017.
 */

public interface RetrofitResponseCallback<T> {
    void onDataLoaded(T dataResponse);
    void onError(String error);
}
