package by.test.dartlen.gallery;

import android.app.Application;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

/***
 * Created by Dartlen on 29.10.2017.
 */

public class App extends Application {

    public static App INSTANCE;
    private Cicerone<Router> cicerone;
    Picasso picasso;
    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
        cicerone = Cicerone.create();

        picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(this))
                .build();

        Picasso.setSingletonInstance(picasso);
    }

    public Picasso getPicasso(){return picasso;}
    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

}
