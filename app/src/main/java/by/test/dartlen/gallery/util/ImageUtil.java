package by.test.dartlen.gallery.util;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.remote.Image;

/***
 * Created by Dartlen on 30.10.2017.
 */

public final class ImageUtil {

    private ImageUtil() {

    }

    public static void loadImage(@NonNull ImageView imageView, @NonNull Image images) {
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
                        App.INSTANCE.getPicasso()
                                .load(url)
                                .error(R.drawable.avd_hide_password_1)
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    }
                });
    }

}

