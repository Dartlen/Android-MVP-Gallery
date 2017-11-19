package by.test.dartlen.gallery.picture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.util.Image;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 10.11.2017.
 */

public class PictureFragment extends Fragment implements PictureContract.View {

    PictureContract.Presenter mPicturePresenter;

    @BindView(R.id.recyclerComments)
    RecyclerView recyclerComments;
    @BindView(R.id.imageView2)
    ImageView imagePicture;
    @BindView(R.id.textView)
    TextView textView;

    @Override
    public void setPresenter(PictureContract.Presenter presenter) {
        mPicturePresenter = checkNotNull(presenter);
    }

    public static PictureFragment newInstance() {
        return new PictureFragment();
    }

    public PictureFragment(){}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_picture, container, false);

        ButterKnife.bind(this, root);

        FloatingActionButton fab =(FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerComments.setLayoutManager(mLayoutManager);
        recyclerComments.setItemAnimator(new DefaultItemAnimator());
        recyclerComments.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.message_item, parent, false);

                return new RecyclerView.ViewHolder(itemView) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPicturePresenter.start();

    }

    @Override
    public void showPicture(Images imageData) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        textView.setText(formatter.format(imageData.getDate()));
        Image.loadImage(imagePicture, imageData);
    }


}
