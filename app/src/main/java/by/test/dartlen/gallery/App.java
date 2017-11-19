package by.test.dartlen.gallery;

import android.app.Application;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.greenrobot.greendao.database.Database;

import by.test.dartlen.gallery.data.local.greendao.DaoMaster;
import by.test.dartlen.gallery.data.local.greendao.DaoSession;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

/***
 * Created by Dartlen on 29.10.2017.
 */

public class App extends Application {

    private DaoSession daoSession;
    public static App INSTANCE;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
        cicerone = Cicerone.create();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "gallery.db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(this))
                .build();

        Picasso.setSingletonInstance(picasso);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

}
