package by.test.dartlen.gallery.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.Mapper;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.data.remote.retrofit.image.DataImage;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 28.10.2017.
 */

public class GalleryFragment extends Fragment implements GalleryContract.View, AddItemsImage.OnAddItemListener
         {

    private GalleryContract.Presenter mPresenter;
    private Mapper mapper;

    public int NUM_ITEMS_PAGE = 3;

    private ImageAdapter mImageAdapter;
    private List<Images> listList;
    private int paginationCounter = 0;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

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
        mImageAdapter = new ImageAdapter(getContext(),this);
        mapper = new Mapper(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        //paginationCounter=0;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.gallery_fragment, container, false);

        ButterKnife.bind(this, root);

        recycleViewSetup(recycleView);

        recycleView.setAdapter(mImageAdapter);

        mPresenter.loadImages();

        ItemClickSupport.addTo(recycleView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                    }
                });

        ItemClickSupport.addTo(recycleView)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                        return true;
                    }
                });

        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 3 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    new AddItemsImage(GalleryFragment.this).execute();
                }
            }
        });

        return root;
    }

    public void recycleViewSetup(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager mGridLayout = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mGridLayout);
    }

    @Override
    public void showImages(List<DataImage> data) {
        this.listList = mapper.toImagesFromDataImages(data);
        addItemToAdapter();
    }

    private void addItemToAdapter() {
        paginationCounter += NUM_ITEMS_PAGE;
        for (int i = paginationCounter - NUM_ITEMS_PAGE; i < paginationCounter; i++) {
            progressBar.setVisibility(View.VISIBLE);
            if (i < listList.size() && listList.get(i) != null)
                mImageAdapter.add( listList.get(i));
        }
    }

    @Override
    public void onFinishImage() {
        addItemToAdapter();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStartImage() {
        progressBar.setVisibility(View.VISIBLE);
    }

}
