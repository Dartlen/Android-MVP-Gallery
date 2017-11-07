package by.test.dartlen.gallery.data.remote.mockremote;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import by.test.dartlen.gallery.data.remote.retrofit.UserService;
import by.test.dartlen.gallery.data.remote.retrofit.image.DataImage;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImage;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImagePost;
import by.test.dartlen.gallery.data.remote.retrofit.user.Data;
import by.test.dartlen.gallery.data.remote.retrofit.user.DataResponse;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;

/***
 * Created by Dartlen on 02.11.2017.
 */

public class MockUserServiceResponse implements UserService{

    private final BehaviorDelegate<UserService> delegate;

    public MockUserServiceResponse(BehaviorDelegate<UserService> service){
        this.delegate = service;
    }

    @Override
    public Call<DataResponse> Signin(LoginData data) {

        if(data.getLogin().equals("")&&data.getPassword().equals("")) {
            DataResponse dataResponse = new DataResponse();
            Data dater = new Data();
            dataResponse.setStatus(200);
            dater.setLogin("dartlen2");
            dater.setToken("TTlobxhNFvm2zO2Jwm3uniSJKOaTzHltywQAvqNZ73hTVfqmmwPCFFS8UDJ7IUx3");
            dater.setUserId(443);
            dataResponse.setData(dater);
            return delegate.returning(Calls.response(dataResponse)).Signin(data);
        }else {
        /*Error error = new Error();
        error.setCode(404);
        error.setMessege("ploho");

        String json = "";


        Gson value = new Gson();
        json = value.toJson(error);
        Response response = Response.error(400, ResponseBody.create(MediaType.parse("application/json"), json));*/


            Error error = new Error();
            error.setCode(404);
            error.setMessege("Quote Not Found");
            //QuoteOfTheDayErrorResponse quoteOfTheDayErrorResponse = new QuoteOfTheDayErrorResponse();
            //quoteOfTheDayErrorResponse.setError(error);

            //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            Gson value = new Gson();

            String json = value.toJson(error);
            /*Response response = Response.error(404, ResponseBody.create(MediaType.parse("application/json"), json));
            try {*/
            //json = ow.writeValueAsString(quoteOfTheDayErrorResponse);
            Response response = Response.error(404, ResponseBody.create(MediaType.parse("application/json"), json));
            return delegate.returning(Calls.response(response)).Signin(data);
           /* } catch (Exception e) {
                //Log.e(TAG, "JSON Processing exception:",e);
                //return //Calls.failure(e);
            }

            //return delegate.returningResponse(dataResponse).Signin(data);
        }
        return delegate.returning(Calls.response(response)).Signin(data);*/
        }

    }

    @Override
    public Call<DataResponse> Signup(LoginData data) {
        return null;
    }

    @Override
    public Call<ResponseDataImage> getImages(int p, String token) {
        if(token.equals("TTlobxhNFvm2zO2Jwm3uniSJKOaTzHltywQAvqNZ73hTVfqmmwPCFFS8UDJ7IUx3")
                && p==1){
            ResponseDataImage responseDataImage = new ResponseDataImage();
            DataImage dataImage = new DataImage();
            responseDataImage.setStatus(200);
            dataImage.setUrl("https://cs8.pikabu.ru/post_img/2016/01/18/5/1453101874187862206.jpg");
            dataImage.setId(1);
            dataImage.setLng(27.624822f);
            dataImage.setLat(53.848864f);
            dataImage.setDate(1073741823);
            DataImage dataImage1 = new DataImage();
            dataImage1.setUrl("http://flytothesky.ru/wp-content/uploads/2013/08/4-600x450.jpeg");
            dataImage1.setId(1);
            dataImage1.setLng(27.552601f);
            dataImage1.setLat(53.910766f);
            dataImage1.setDate(1073741823);

            DataImage dataImage2 = new DataImage();
            dataImage2.setUrl("http://flytothesky.ru/wp-content/uploads/2013/08/4-600x450.jpeg");
            dataImage2.setId(1);
            dataImage2.setLng(27.552601f);
            dataImage2.setLat(53.910766f);
            dataImage2.setDate(1073741823);

            DataImage dataImage3 = new DataImage();
            dataImage3.setUrl("http://flytothesky.ru/wp-content/uploads/2013/08/4-600x450.jpeg");
            dataImage3.setId(1);
            dataImage3.setLng(27.552601f);
            dataImage3.setLat(53.910766f);
            dataImage3.setDate(1073741823);

            DataImage dataImage4 = new DataImage();
            dataImage4.setUrl("http://flytothesky.ru/wp-content/uploads/2013/08/4-600x450.jpeg");
            dataImage4.setId(1);
            dataImage4.setLng(27.552601f);
            dataImage4.setLat(53.910766f);
            dataImage4.setDate(1073741823);
            List<DataImage> list = new ArrayList<DataImage>();
            list.add(dataImage);
            list.add(dataImage1);
            list.add(dataImage2);
            list.add(dataImage3);
            list.add(dataImage4);
            responseDataImage.setData(list);
            return delegate.returning(Calls.response(responseDataImage)).getImages(p, token);
        }else if (token.equals("TTlobxhNFvm2zO2Jwm3uniSJKOaTzHltywQAvqNZ73hTVfqmmwPCFFS8UDJ7IUx3")
                && p==2){
            ResponseDataImage responseDataImage = new ResponseDataImage();
            DataImage dataImage = new DataImage();
            responseDataImage.setStatus(200);
            dataImage.setUrl("https://cs8.pikabu.ru/post_img/2016/01/18/5/1453101874187862206.jpg");
            dataImage.setId(1);
            dataImage.setLng(27.6222f);
            dataImage.setLat(53.8864f);
            dataImage.setDate(1073741823);
            DataImage dataImage1 = new DataImage();
            dataImage1.setUrl("http://flytothesky.ru/wp-content/uploads/2013/08/4-600x450.jpeg");
            dataImage1.setId(1);
            dataImage1.setLng(27.5521f);
            dataImage1.setLat(53.910766f);
            dataImage1.setDate(1073741823);
            List<DataImage> list = new ArrayList<DataImage>();
            list.add(dataImage);
            list.add(dataImage1);
            responseDataImage.setData(list);
            return delegate.returning(Calls.response(responseDataImage)).getImages(p, token);
        }else if (token.equals("TTlobxhNFvm2zO2Jwm3uniSJKOaTzHltywQAvqNZ73hTVfqmmwPCFFS8UDJ7IUx3")
                && p==3){
            ResponseDataImage responseDataImage = new ResponseDataImage();
            DataImage dataImage = new DataImage();
            responseDataImage.setStatus(200);
            dataImage.setUrl("https://cs8.pikabu.ru/post_img/2016/01/18/5/1453101874187862206.jpg");
            dataImage.setId(1);
            dataImage.setLng(27.6222f);
            dataImage.setLat(53.8864f);
            dataImage.setDate(1073741823);
            DataImage dataImage1 = new DataImage();
            dataImage1.setUrl("http://flytothesky.ru/wp-content/uploads/2013/08/4-600x450.jpeg");
            dataImage1.setId(1);
            dataImage1.setLng(27.5521f);
            dataImage1.setLat(53.910766f);
            dataImage1.setDate(1073741823);
            List<DataImage> list = new ArrayList<DataImage>();
            list.add(dataImage);
            list.add(dataImage1);
            responseDataImage.setData(list);
            return delegate.returning(Calls.response(responseDataImage)).getImages(p, token);
        }
        return delegate.returning(Calls.response(new ResponseDataImage())).getImages(p, token);
    }

    @Override
    public Call<ResponseDataImagePost> postImage(String token, ImageData data) {
        return null;
    }

}
