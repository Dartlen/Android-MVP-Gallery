package by.test.dartlen.gallery.util;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import by.test.dartlen.gallery.BuildConfig;
import by.test.dartlen.gallery.R;
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

    public static void loadImage(@NonNull final ImageView imageView, @NonNull final String url) {
        Picasso.with(imageView.getContext())
                .load(url)

                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        /*Picasso.with(MainPageActivity.getAppContext())
                                .load(url)
                                .error(R.drawable.avd_hide_password_1)
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });*/
                    }
                });
    }

}

