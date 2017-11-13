package by.test.dartlen.gallery.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
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
import by.test.dartlen.gallery.util.PaginationScrollListener;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 28.10.2017.
 */

public class GalleryFragment extends Fragment implements GalleryContract.View{

    private GalleryContract.Presenter mPresenter;

    private Mapper mapper;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private ImageAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.progressBar2)
    ProgressBar progressBarImages;

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
        mapper = new Mapper(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isLastPage)
            mPresenter.start();
        isLastPage=false;
        isLoading=false;
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

        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        ((MainPageActivity) getActivity()).openPicture(adapter.getItem(position));

                    }
                });

        ItemClickSupport.addTo(recyclerView)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {

                        return true;
                    }
                });

        adapter = new ImageAdapter(getContext() );

        linearLayoutManager = new LinearLayoutManager(getContext());

        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager mGridLayout = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mGridLayout);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(mGridLayout) {
            @Override
            protected void loadMoreItems(final int page) {
                isLoading = true;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        progressBarImages.setVisibility(View.VISIBLE);

                        mPresenter.loadNextPage(page, new GalleryContract.CallbackImages() {
                            @Override
                            public void load(List<DataImage> result) {
                                isLoading = false;
                                List<Images> tmpimages = mapper.toImagesFromDataImages(result);
                                if(tmpimages.size()==0)
                                    isLastPage=true;
                                adapter.addAll(tmpimages);
                                progressBarImages.setVisibility(View.INVISIBLE);
                            }
                        });

                    }
                }, 1000);
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        return root;
    }

    @Override
    public void showFirstPage(List<DataImage> result) {
        adapter.addAll(mapper.toImagesFromDataImages(result));
        progressBar.setVisibility(View.INVISIBLE);
    }
}
