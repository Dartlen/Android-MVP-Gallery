package by.test.dartlen.gallery.util;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import by.test.dartlen.gallery.BuildConfig;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.gallery.MainPageActivity;


/***
 * Created by Dartlen on 30.10.2017.
 */

public final class Image {

    private Image() {
    }

    public static void loadImage(@NonNull ImageView imageView, @NonNull Images images) {
        loadImage(imageView, images.getUrl());
    }

    public static void loadImage(@NonNull ImageView imageView, @NonNull String url) {
        Picasso.with(imageView.getContext())
                .load(url)
                .noFade()
                .into(imageView);
    }

    public static void fetch(@NonNull String url) {
        Picasso.with(MainPageActivity.getAppContext())
                .load(url)
                .fetch();
    }
}

