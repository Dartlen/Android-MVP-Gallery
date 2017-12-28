package by.test.dartlen.gallery.map;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;

/***
 * Created by Dartlen on 31.10.2017.
 */

public class MapContract  {

    interface Presenter extends BasePresenter{

    }
    interface View extends BaseView<MapContract.Presenter>{
        //void showPoints(List<DataImage> imagesData);
    }
}
