package by.test.dartlen.gallery.data.local.greendao;

import android.app.Application;
import org.greenrobot.greendao.database.Database;

/**
 * Created by Dartlen on 29.10.2017.
 */


public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "gallery.db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
