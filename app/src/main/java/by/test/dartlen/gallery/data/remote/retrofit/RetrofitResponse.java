package by.test.dartlen.gallery.data.remote.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * Created by Dartlen on 27.10.2017.
 */

public class RetrofitResponse<T> implements Callback<T> {
    private RetrofitResponseCallback<T> callback;

    public RetrofitResponse(RetrofitResponseCallback<T> callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T dataResponse = response.body();
        ResponseBody errorBody = response.errorBody();

        if (dataResponse != null) {
            callback.onDataLoaded(dataResponse);
        } else if (errorBody != null) {
            callback.onError(generateError(errorBody));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        callback.onError(generateError(t));
    }

    private String generateError(ResponseBody errorBody) {

        return errorBody.toString();
    }

    private String generateError(Throwable throwable) {
        return throwable.getMessage();
    }
}
