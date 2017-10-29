package by.test.dartlen.gallery.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.local.greendao.Images;

/***
 * Created by Dartlen on 29.10.2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> {

    private List<Images> mData = new ArrayList<Images>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private int mImageHeight;
    private int mImageWidth;

    ImageAdapter(Context context, List<Images> data, int  imageHeight, int imageWidth) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mImageHeight = imageHeight;
        this.mImageWidth = imageWidth;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_image, parent, false);
        return ImageHolder.create(view.getContext(), mImageHeight, mImageWidth);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        Images animal = mData.get(position);
        //holder.mTextView.setText(animal);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void changeDataSet(@NonNull List<Images> movies) {
        mData.clear();
        mData.addAll(movies);
        notifyDataSetChanged();
    }
    /*public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = (TextView) itemView.findViewById(R.id.info_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }*/

    /*String getItem(int id) {
        return mData.get();
    }*/

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
