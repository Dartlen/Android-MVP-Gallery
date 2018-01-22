package by.test.dartlen.gallery.gallery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.remote.Image;
import by.test.dartlen.gallery.util.PaginationScrollListener;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 28.10.2017.
 */

public class GalleryFragment extends Fragment implements GalleryContract.View{

    private GalleryContract.Presenter mPresenter;

    private ImageAdapter mAdapter;
    private GridLayoutManager gridLayoutManager;

    private List<Image> ImagesList;
    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 1;
    private int currentPage = PAGE_START;
    AlertDialog alert;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        ButterKnife.bind(this, root);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        //App.INSTANCE.getRouter().navigateTo("picture", musicRecyclerAdapter.getItem(position));
                    }
                });

        ItemClickSupport.addTo(recyclerView)
                .setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                        mPresenter.onItemLongClicked(position);
                        return true;
                    }
                });

        recycleViewSetup(recyclerView);

        mAdapter = new ImageAdapter(getContext());

        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading=true;
                if(mAdapter.getItemCount()<ImagesList.size()) {
                    currentPage += 1;
                    loadNextPage();
                }
                isLoading=false;
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        });
        mPresenter.loadImages();

        return root;
    }

    public void recycleViewSetup(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(),3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void showLogin(String login) {
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navLogin = (TextView) headerView.findViewById(R.id.header_login);
        navLogin.setText(login);
    }

    @Override
    public void showImages(List<Image> dataImages) {
        ImagesList  = dataImages;
        TOTAL_PAGES = dataImages.size()/12;
        mAdapter.clearAll();
        mAdapter.notifyDataSetChanged();
        loadFirstPage();
    }
    private void loadFirstPage() {
        //progressBar.setVisibility(View.GONE);
        if(ImagesList.size()<12)
            mAdapter.setImages(ImagesList);
        else
            mAdapter.setImages(ImagesList.subList(0,12));

        if (currentPage <= TOTAL_PAGES)
            ;//mAdapter.addLoadingFooter();
        else isLastPage = true;

    }

    private void loadNextPage() {
        //mAdapter.removeLoadingFooter();
        isLoading = false;
        mAdapter.clearAll();
        mAdapter.notifyDataSetChanged();
        if(currentPage*12>ImagesList.size()) {
            mAdapter.setImages(ImagesList);
        }else{
            mAdapter.setImages(ImagesList.subList(0, currentPage * 12));
            isLastPage=true;
        }
        if (currentPage != TOTAL_PAGES)
            ;//mAdapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void showDialogQuestion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        alert = builder.create();

        builder.setMessage(R.string.message_question)
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.onClickedQuestionCancel();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.onClickedQuestionOk();
                    }
                }).show();
    }

    @Override
    public void hideQuestionDialog() {
        alert.cancel();
    }
}





