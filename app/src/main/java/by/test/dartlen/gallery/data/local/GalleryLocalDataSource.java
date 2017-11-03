package by.test.dartlen.gallery.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.List;

import by.test.dartlen.gallery.data.GalleryDataSource;
import by.test.dartlen.gallery.data.Mapper;
import by.test.dartlen.gallery.data.local.greendao.App;
import by.test.dartlen.gallery.data.local.greendao.DaoSession;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.data.local.greendao.ImagesDao;
import by.test.dartlen.gallery.data.local.greendao.Users;
import by.test.dartlen.gallery.data.local.greendao.UsersDao;
import by.test.dartlen.gallery.data.remote.retrofit.image.DataImage;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.data.remote.retrofit.user.Data;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;

import static android.content.Context.MODE_PRIVATE;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 27.10.2017.
 */

public class GalleryLocalDataSource implements GalleryDataSource{

    private static GalleryLocalDataSource INSTANCE;
    private ImagesDao mImagesDao;
    private UsersDao mUsersDao;
    private Context mContext;
    private SharedPreferences prefs;
    private Mapper mapper;

    private GalleryLocalDataSource(@NonNull Context context){
        checkNotNull(context);
        mContext   = context;
        DaoSession daoSession = ((App)context.getApplicationContext()).getDaoSession();
        mImagesDao = daoSession.getImagesDao();
        mUsersDao  = daoSession.getUsersDao();
        mapper     = new Mapper(context);
    }
    public static GalleryLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GalleryLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void signup(@NonNull LoadLoginCallback callback, @NonNull LoginData loginData) {

    }

    @Override
    public void signin(@NonNull LoadLoginCallback callback, @NonNull LoginData loginData) {

    }


    @Override
    public void getImages(LoadImageCallback callback, int page, String token) {


    }

    @Override
    public void postImage(ImagePostCallback callback, String token, ImageData data) {

    }

    @Override
    public void setImages(List<DataImage> data, String token) {
        for(Images obj: mapper.toImagesFromDataImages(data))
            mImagesDao.insertOrReplace(obj);
        //mapper
        /*String login = prefs.getString("login","");
        List<Users> userId = mUsersDao.queryBuilder()
                .where(UsersDao.Properties.Login.eq(login))
                .orderAsc(UsersDao.Properties.Id)
                .list();

        for (DataImage obj: data)
            mImagesDao.insertOrReplace(
                    new Images(null, obj.getUrl(), obj.getDate(), obj.getLat(), obj.getLng(),
                            userId.get(0).getId()));*/

    }

    @Override
    public void setUser(Data user) {

        List<Users> userId = mUsersDao.queryBuilder()
                .where(UsersDao.Properties.Login.eq(user.getLogin()))
                .orderAsc(UsersDao.Properties.Login)
                .list();

        if(userId.size()==0)
            mUsersDao.insert(new Users(null, user.getLogin(), user.getToken(), user.getUserId()));

        prefs = mContext.getSharedPreferences("by.test.gallery", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("login", user.getLogin());
        editor.putString("token", user.getToken());
        editor.putInt("userid", user.getUserId());
        editor.apply();

    }

    @Override
    public Users getUser() {
        prefs = mContext.getSharedPreferences("by.test.gallery", MODE_PRIVATE);
        String login = prefs.getString("login","");
        List<Users> users = mUsersDao.queryBuilder()
                .where(UsersDao.Properties.Login.eq(login))
                .orderAsc(UsersDao.Properties.Login)
                .list();
        if (users.size()>0){
            return users.get(0);
        }else
        {
            return null;
        }

    }

}
