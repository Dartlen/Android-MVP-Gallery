package by.test.dartlen.gallery.gallery;

/***
 * Created by Dartlen on 29.10.2017.
 */

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.util.Image;


public class ImageHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageView)
    ImageView mImageView;

    @BindView(R.id.info_text)
    TextView mTextView;


    @NonNull
    public static ImageHolder create(@NonNull Context context) {
        View view = View.inflate(context, R.layout.item_image, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        imageView.requestLayout();



        return new ImageHolder(view);
    }

    public ImageHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }

    public void bind(@NonNull Images images) {
        Image.loadImage(mImageView, images.getUrl());
    }


}

