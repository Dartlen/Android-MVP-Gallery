package by.test.dartlen.gallery.data;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.data.local.greendao.Users;
import by.test.dartlen.gallery.data.remote.retrofit.image.DataImage;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImage;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImagePost;
import by.test.dartlen.gallery.data.remote.retrofit.user.Data;
import by.test.dartlen.gallery.data.remote.retrofit.user.DataResponse;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;
import by.test.dartlen.gallery.util.Image;

/***
 * Created by Dartlen on 26.10.2017.
 */

public class GalleryRepository implements GalleryDataSource{

    private static GalleryRepository INSTANCE = null;

    private static Integer SIZE_PAGE=5;

    private final GalleryDataSource mGalleryRemoteDataSource;

    private final GalleryDataSource mGalleryLocalDataSource;

    private boolean isRemoteLoaded = false;

    private List<Images> mCachedImages = new ArrayList<Images>();

    private Integer pageremotecount = 1;

    boolean mCachIsDirty = false;


    private  GalleryRepository(@NonNull GalleryDataSource galleryRemoteDataSource,
                               @NonNull GalleryDataSource galleryLocalDataSource){
        mGalleryRemoteDataSource = checkNotNull(galleryRemoteDataSource);
        mGalleryLocalDataSource = checkNotNull(galleryLocalDataSource);
    }

    public static GalleryRepository getInstance(GalleryDataSource galleryRemoteDataSource,
                                                GalleryDataSource galleryLocalDataSource){
        if(INSTANCE == null){
            INSTANCE = new GalleryRepository(galleryRemoteDataSource, galleryLocalDataSource);
        }

        return INSTANCE;
    }

    @Override
    public void signup(@NonNull final LoadLoginCallback callback, final @NonNull LoginData loginData) {
        mGalleryRemoteDataSource.signup(new LoadLoginCallback() {
            @Override
            public void onLoggined(DataResponse login) {
                setUser(login.getData());
                callback.onLoggined(login);
            }

            @Override
            public void onDataNotAvailable(String error) {
                callback.onDataNotAvailable(error);
            }
        }, loginData);

    }

    @Override
    public void signin(final @NonNull LoadLoginCallback callback, final @NonNull LoginData loginData) {
        mGalleryRemoteDataSource.signin(new LoadLoginCallback() {
            @Override
            public void onLoggined(DataResponse login) {
                setUser(login.getData());
                callback.onLoggined(login);
            }

            @Override
            public void onDataNotAvailable(String error) {
                callback.onDataNotAvailable(error);
            }
        }, loginData);
    }

    @Override
    public void getImages(final @NonNull LoadImageCallback callback, final @NotNull int page,
                          final @NotNull String token) {

        if (mCachedImages.size() == 0) {
            getLocalImages(new LoadImageCallback() {
                @Override
                public void onDataLoaded(ResponseDataImage dataResponse) {
                    if (dataResponse.getData().size() != 0) {
                        mCachedImages = mGalleryLocalDataSource.toImages(dataResponse.getData());
                        dataResponse.setData(mGalleryLocalDataSource.toDataImages(mCachedImages));
                        getRemoteImages(new LoadImageCallback() {
                            @Override
                            public void onDataLoaded(ResponseDataImage dataResponse) {
                                refreshLocalDataSource(dataResponse);


                                List<Images> x = mGalleryLocalDataSource.toImages(dataResponse.getData());
                                appendnewitems(x);

                                dataResponse.setData(mGalleryLocalDataSource.toDataImages(mCachedImages));


                                callback.onDataLoaded(dataResponse);
                            }

                            @Override
                            public void onError(String error) {
                                callback.onError(error);
                            }
                        }, page, token);


                        //callback.onDataLoaded(dataResponse);
                    }
                }

                @Override
                public void onError(String error) {

                    getRemoteImages(new LoadImageCallback() {
                        @Override
                        public void onDataLoaded(ResponseDataImage dataResponse) {
                            refreshLocalDataSource(dataResponse);

                            List<Images> x = mGalleryLocalDataSource.toImages(dataResponse.getData());
                            appendnewitems(x);

                            dataResponse.setData(mGalleryLocalDataSource.toDataImages(mCachedImages));


                            callback.onDataLoaded(dataResponse);
                        }

                        @Override
                        public void onError(String error) {
                            callback.onError(error);
                        }
                    }, page, token);
                    //callback.onError(error);
                }
            }, page, token);

        }else{
            getRemoteImages(new LoadImageCallback() {
                @Override
                public void onDataLoaded(ResponseDataImage dataResponse) {
                    refreshLocalDataSource(dataResponse);


                    List<Images> x = mGalleryLocalDataSource.toImages(dataResponse.getData());
                    appendnewitems(x);

                    dataResponse.setData(mGalleryLocalDataSource.toDataImages(mCachedImages));


                    callback.onDataLoaded(dataResponse);
                }

                @Override
                public void onError(String error) {
                    callback.onError(error);
                }
            }, page, token);
        }





        /*getLocalImages(new LoadImageCallback() {
            @Override
            public void onDataLoaded(ResponseDataImage dataResponse) {
                if (dataResponse.getData().size() != 0) {
                    mCachedImages = mGalleryLocalDataSource.toImages(dataResponse.getData());
                    dataResponse.setData(mGalleryLocalDataSource.toDataImages(mCachedImages));
                    callback.onDataLoaded(dataResponse);
                }
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, page, token);


        getRemoteImages(new LoadImageCallback() {
            @Override
            public void onDataLoaded(ResponseDataImage dataResponse) {
                refreshLocalDataSource(dataResponse);


                List<Images> x = mGalleryLocalDataSource.toImages(dataResponse.getData());
                appendnewitems(x);

                dataResponse.setData(mGalleryLocalDataSource.toDataImages(mCachedImages));

                callback.onDataLoaded(dataResponse);

            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        },  page, token);*/


        /*if (mCachedImages.size() == 0) {
            getLocalImages(new LoadImageCallback() {
                @Override
                public void onDataLoaded(ResponseDataImage dataResponse) {
                    if (dataResponse.getData().size() != 0) {
                        mCachedImages = mGalleryLocalDataSource.toImages(dataResponse.getData());
                        dataResponse.setData(mGalleryLocalDataSource.toDataImages(mCachedImages));
                        getRemoteImages(new LoadImageCallback() {
                            @Override
                            public void onDataLoaded(ResponseDataImage dataResponse) {
                                refreshLocalDataSource(dataResponse);


                                List<Images> x = mGalleryLocalDataSource.toImages(dataResponse.getData());
                                appendnewitems(x);

                                dataResponse.setData(mGalleryLocalDataSource.toDataImages(mCachedImages));


                                callback.onDataLoaded(dataResponse);
                            }

                            @Override
                            public void onError(String error) {
                                callback.onError(error);
                            }
                        }, page, token);


                        //callback.onDataLoaded(dataResponse);
                    }
                }

                @Override
                public void onError(String error) {

                    getRemoteImages(new LoadImageCallback() {
                        @Override
                        public void onDataLoaded(ResponseDataImage dataResponse) {
                            refreshLocalDataSource(dataResponse);

                            List<Images> x = mGalleryLocalDataSource.toImages(dataResponse.getData());
                            appendnewitems(x);

                            dataResponse.setData(mGalleryLocalDataSource.toDataImages(mCachedImages));


                            callback.onDataLoaded(dataResponse);
                        }

                        @Override
                        public void onError(String error) {
                            callback.onError(error);
                        }
                    }, page, token);
                    //callback.onError(error);
                }
            }, page, token);

        }else{
            getRemoteImages(new LoadImageCallback() {
                @Override
                public void onDataLoaded(ResponseDataImage dataResponse) {
                    refreshLocalDataSource(dataResponse);


                    List<Images> x = mGalleryLocalDataSource.toImages(dataResponse.getData());
                    appendnewitems(x);

                    dataResponse.setData(mGalleryLocalDataSource.toDataImages(mCachedImages));


                    callback.onDataLoaded(dataResponse);
                }

                @Override
                public void onError(String error) {
                    callback.onError(error);
                }
            }, page, token);
        }*/

    }

    public void appendnewitems(List<Images> images){
        boolean flag = false;
        for(Images im: images) {
            for (Images x : mCachedImages) {
                if (x.getUrl().equals(im.getUrl())) {
                    flag=true;
                }
            }
            if(!flag){
                mCachedImages.add(im);
            }
        }
    }

    private void getRemoteImages(final @NonNull LoadImageCallback callback, final @NotNull int page,
                                final @NotNull String token){

        mGalleryRemoteDataSource.getImages(new LoadImageCallback() {
            @Override
            public void onDataLoaded(ResponseDataImage dataResponse) {

                callback.onDataLoaded(dataResponse);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, page, token);
    }

    private void refreshCache(ResponseDataImage images) {
      /*  if (mCachedImages == null) {
            mCachedImages = new ResponseDataImage();
        }
        mCachedImages.setData(images.getData());

        mCacheIsDirty = false;*/
    }

    private void refreshLocalDataSource(ResponseDataImage images) {

        mGalleryLocalDataSource.setImages(images.getData(), mGalleryLocalDataSource.getUser().getToken());

    }

    /*public void getRemoteImages(final @NonNull LoadImageCallback callback, final @NotNull int page,
                                final @NotNull String token){
        mGalleryRemoteDataSource.getImages(new LoadImageCallback() {
            @Override
            public void onDataLoaded(ResponseDataImage dataResponse) {
                callback.onDataLoaded(dataResponse);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        },page, token );
    }*/

    @Override
    public void setImages(List<DataImage> data, String token) {
        mGalleryLocalDataSource.setImages(data, token);
    }

    public void getLocalImages(final @NotNull LoadImageCallback callback,final @NotNull Integer page,
                               final @NotNull String token){
        mGalleryLocalDataSource.getImages(new LoadImageCallback() {
            @Override
            public void onDataLoaded(ResponseDataImage dataResponse) {
                callback.onDataLoaded(dataResponse);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        },page, token );
    }

    @Override
    public void postImage(final ImagePostCallback callback, String token, ImageData data) {

        mGalleryRemoteDataSource.postImage(new ImagePostCallback() {
            @Override
            public void onDataLoaded(ResponseDataImagePost dataResponse) {
                callback.onDataLoaded(dataResponse);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }, token, data);

    }

    @Override
    public void setUser(Data user) {
        mGalleryLocalDataSource.setUser(user);
    }

    @Override
    public Users getUser() {
        return mGalleryLocalDataSource.getUser();
    }


    @Override
    public List<DataImage> toDataImages(List<Images> list) {
        return null;
    }

    @Override
    public List<Images> toImages(List<DataImage> list) {
        return null;
    }
}
