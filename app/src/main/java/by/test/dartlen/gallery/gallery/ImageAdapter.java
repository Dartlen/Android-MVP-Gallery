package by.test.dartlen.gallery.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.remote.Image;

/***
 * Created by Dartlen on 29.10.2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Image> ImagesList;
    private Context context;
    private boolean isLoadingAdded = false;

    public ImageAdapter(Context context) {
        this.context = context;
        ImagesList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        ImageHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_image, parent, false);
        viewHolder = new ImageHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case ITEM:
                final ImageHolder image = (ImageHolder) holder;

                Image currentImage = ImagesList.get(position);
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                image.mTextView.setText(formatter.format(currentImage.getDate()));
                image.bind(currentImage);

                break;

            case LOADING:

                break;
        }
    }

    @Override
    public int getItemCount() {
        return ImagesList == null ? 0 :ImagesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == ImagesList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Image r) {
        ImagesList.add(r);
        notifyItemInserted(ImagesList.size() - 1);
    }

    public void addAll(List<Image> imageslist) {
        for (Image result : imageslist) {
            add(result);
        }
    }

    public void remove(Image r) {
        int position = ImagesList.indexOf(r);
        if (position > -1) {
            ImagesList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Image("",123L,2.2,2.2));
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = ImagesList.size() - 1;
        Image result = getItem(position);

        if (result != null && result.equals(getItem(0))) {
            ImagesList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Image getItem(int position) {
        return ImagesList.get(position);
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    public void clearAll(){
        ImagesList=null;
    }

    public void setImages(List<Image> data){
        ImagesList = data;
    }
}



