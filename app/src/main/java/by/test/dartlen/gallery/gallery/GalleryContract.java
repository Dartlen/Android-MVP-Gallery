package by.test.dartlen.gallery.gallery;

import java.util.List;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;
import by.test.dartlen.gallery.data.remote.Image;


/***
 * Created by Dartlen on 28.10.2017.
 */

public interface GalleryContract {

    interface Presenter extends BasePresenter {
        void loadImages();
        void onBackPressed();
        void onClickedFab();
        void onItemLongClicked(int data);
        void onClickedQuestionOk();
        void onClickedQuestionCancel();
    }

    interface View extends BaseView<GalleryContract.Presenter> {
        void showLogin(String login);
        void showImages(List<Image> dataImages);
        void showDialogQuestion();
        void hideQuestionDialog();
    }
}
