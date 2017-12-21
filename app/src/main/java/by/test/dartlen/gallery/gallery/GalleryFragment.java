package by.test.dartlen.gallery.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.Mapper;
import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImage;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 28.10.2017.
 */

public class GalleryFragment extends Fragment implements GalleryContract.View{

    private GalleryContract.Presenter mPresenter;

    private Mapper mapper;

    public int NUM_ITEMS_PAGE = 5;

    private ImageAdapter musicRecyclerAdapter;

    private List<Images> listList = new ArrayList<>(0);

    private int paginationCounter = 0;

    private boolean isLoading = true;
    private int pagecounter = 1;

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

    }

    @Override
    public void onPause() {
        super.onPause();
        pagecounter=1;
        paginationCounter = 0;
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
                        App.INSTANCE.getRouter().navigateTo("picture", musicRecyclerAdapter.getItem(position));
                    }
                });

        ItemClickSupport.addTo(recyclerView)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {

                        return true;
                    }
                });

        recycleViewSetup(recyclerView);

        musicRecyclerAdapter = new ImageAdapter(getContext());

        recyclerView.setAdapter(musicRecyclerAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());

                int totalItemCount = layoutManager.getItemCount();

                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
                int childcount = layoutManager.getChildCount();
                int firstVisible = layoutManager.findFirstVisibleItemPosition();

                if (totalItemCount > 0 && endHasBeenReached) {
                        if ((childcount + firstVisible) >= totalItemCount
                                && firstVisible >= 0 ) {
                            if(totalItemCount==lastVisible+1){
                                pagecounter++;
                                mPresenter.loadFirstPage(pagecounter);}
                        }
                }
            }
        });
        mPresenter.loadFirstPage(pagecounter);

        return root;
    }

    public void recycleViewSetup(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    public void addItemToAdapter(ResponseDataImage dataResponse) {
        listList = mapper.toImagesFromDataImages(dataResponse.getData());
        paginationCounter += NUM_ITEMS_PAGE;
        for (int i = paginationCounter - NUM_ITEMS_PAGE; i < paginationCounter; i++) {
            progressBar.setVisibility(View.VISIBLE);
            if (i < listList.size() && listList.get(i) != null)

                musicRecyclerAdapter.add(listList.get(i));
        }
        isLoading=false;
    }
}





