package by.test.dartlen.gallery.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import by.test.dartlen.gallery.data.local.greendao.App;
import by.test.dartlen.gallery.data.local.greendao.DaoSession;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.data.local.greendao.Users;
import by.test.dartlen.gallery.data.local.greendao.UsersDao;
import by.test.dartlen.gallery.data.remote.retrofit.image.DataImage;

import static android.content.Context.MODE_PRIVATE;

/***
 * Created by Dartlen on 30.10.2017.
 */

public class Mapper {

    private UsersDao mUsersDao;
    private Users user;
    private SharedPreferences prefs;

    public Mapper(Context context){
        DaoSession daoSession = ((App)context.getApplicationContext()).getDaoSession();
        mUsersDao  = daoSession.getUsersDao();
        prefs = context.getSharedPreferences("by.test.gallery", MODE_PRIVATE);
        String login = prefs.getString("login","");
        List<Users> users = mUsersDao.queryBuilder()
                .where(UsersDao.Properties.Login.eq(login))
                .orderAsc(UsersDao.Properties.Id)
                .list();
        if(users.size()==0){

        }else {
            user=users.get(0);
        }
    }

    public List<Images> toImagesFromDataImages(List<DataImage> dataImages){
        if(dataImages==null || dataImages.size()==0){
            return new ArrayList<Images>(0);
        }else{
            List<Images> result = new ArrayList<Images>(0);
            for (DataImage obj: dataImages)
                result.add(
                        new Images(null, obj.getUrl(), obj.getDate(), obj.getLat(), obj.getLng(),
                                user.getId()));
            return result;
        }
    }

        //TODO: проверить работоспособность
    public List<DataImage> toDataImagefromImages(List<Images> images){
        if(images == null || images.size()== 0 ){
            return new ArrayList<DataImage>(0);
        }else{
            List<DataImage> result = new ArrayList<DataImage>(0);
            for (Images obj: images){
                DataImage tmpDataImage = new DataImage();
                tmpDataImage.setId(1);
                tmpDataImage.setUrl(obj.getUrl());
                tmpDataImage.setDate(obj.getDate());
                tmpDataImage.setLat(obj.getLat());
                tmpDataImage.setLng(obj.getLng());
                result.add(tmpDataImage);
            }
            return result;
        }
        }
}
