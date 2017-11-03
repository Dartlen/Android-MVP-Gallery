package by.test.dartlen.gallery.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemClick;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.local.greendao.Images;

/***
 * Created by Dartlen on 29.10.2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> {

    private List<Images> mData = new ArrayList<Images>();
    private LayoutInflater mInflater;

    ImageAdapter(Context context, Fragment fragment) {
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_image, parent, false);
        return ImageHolder.create(view.getContext());
    }

    @Override
    public void onBindViewHolder(ImageHolder holder,final int position) {
        Images animal = mData.get(position);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        holder.mTextView.setText(formatter.format(animal.getDate()));
        holder.bind(animal);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void add(Images images) {
        mData.add(images);
        notifyDataSetChanged();
    }

    /*public void changeDataSet(@NonNull List<Images> images) {
        mData.clear();
        mData.addAll(images);
        notifyDataSetChanged();
    }*/
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

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(int position);
    }

}
