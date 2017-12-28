package by.test.dartlen.gallery.gallery;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;


/***
 * Created by Dartlen on 28.10.2017.
 */

public interface GalleryContract {

    interface Presenter extends BasePresenter {
        void loadFirstPage(int page);
        void onBackPressed();
        void onClickedFab();
    }

    interface View extends BaseView<GalleryContract.Presenter> {
        void showLogin(String login);
    }
}
