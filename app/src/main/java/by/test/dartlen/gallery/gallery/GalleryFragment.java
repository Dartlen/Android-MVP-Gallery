package by.test.dartlen.gallery.gallery;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.local.greendao.Images;

import static android.content.Context.MODE_PRIVATE;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 28.10.2017.
 */

public class GalleryFragment extends Fragment implements GalleryContract.View {

    private GalleryContract.Presenter mPresenter;
    private ImageAdapter adapter;
    private SharedPreferences prefs;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    public GalleryFragment(){}

    @Override
    public void setPresenter(GalleryContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.gallery_fragment, container, false);

        ButterKnife.bind(this, root);
        adapter = createAdapter(root, new ArrayList<Images>(0));
        showLogin(getContext());



        return root;
    }

    @Override
    public void showImages(List<Images> data) {
        adapter.changeDataSet(data);
    }

    @NonNull
    private ImageAdapter createAdapter(View root, List<Images> data) {
        TypedValue typedValue = new TypedValue();
        int actionBarHeight = root.getContext().getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true)
                ? TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics())
                : 0;
        int imageHeight = (int) ((getResources().getDisplayMetrics().heightPixels - actionBarHeight))/10;

        int imageWidth = getResources().getDisplayMetrics().widthPixels / 3;

        // data to populate the RecyclerView with
        //String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

        // set up the RecyclerView
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.rvNumbers);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        adapter = new ImageAdapter(getContext(), data, imageHeight, imageWidth);
        //adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        return adapter;
    }

    @Override
    public void showLogin(Context context){
        prefs = context.getSharedPreferences("by.test.gallery", MODE_PRIVATE);
        String login = prefs.getString("login","");
        //context.
    }
}
