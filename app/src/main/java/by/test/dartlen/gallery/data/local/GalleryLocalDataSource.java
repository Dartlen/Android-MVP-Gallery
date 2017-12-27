package by.test.dartlen.gallery.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.List;

import by.test.dartlen.gallery.data.Mapper;
import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.data.local.greendao.DaoSession;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.data.local.greendao.ImagesDao;
import by.test.dartlen.gallery.data.local.greendao.Users;
import by.test.dartlen.gallery.data.local.greendao.UsersDao;
import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;
import by.test.dartlen.gallery.data.remote.retrofit.image.DataImage;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImage;
import by.test.dartlen.gallery.data.remote.retrofit.user.Data;


import static android.content.Context.MODE_PRIVATE;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 27.10.2017.
 */

public class GalleryLocalDataSource{

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

    public void getImages(GalleryRemoteDataSource.LoadImageCallback callback, int page, String token) {
        /*List<Images> imagesData = mImagesDao.queryBuilder()
                .orderAsc(ImagesDao.Properties.Id)
                .list();
        ResponseDataImage tmpData = new ResponseDataImage();
        tmpData.setData(new Mapper(mContext).toDataImagefromImages(imagesData));
        if(tmpData.getData().size()==0)
            callback.onError("Null size");
        tmpData.setStatus(200);
        callback.onDataLoaded(tmpData);*/

    }

    public void postImage(GalleryRemoteDataSource.ImagePostCallback callback, String token, ImageData data) {
        //TODO: добавить изображение в локальную бд
    }

    public void setImages(List<DataImage> data, String token) {
        /*for(Images obj: mapper.toImagesFromDataImages(data))
            mImagesDao.insertOrReplace(obj);*/
    }

    public void setUser(Data user) {

        /*List<Users> userId = mUsersDao.queryBuilder()
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
        editor.apply();*/

    }

    public Users getUser() {
        /*prefs = mContext.getSharedPreferences("by.test.gallery", MODE_PRIVATE);
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
        }*/
        return null;
    }

    public List<DataImage> toDataImages(List<Images> list) {
        return new Mapper(mContext).toDataImagefromImages(list);
    }

    public List<Images> toImages(List<DataImage> list) {
        return new Mapper(mContext).toImagesFromDataImages(list);
    }
}
