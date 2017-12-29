package by.test.dartlen.gallery.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.remote.Image;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 31.10.2017.
 */

public class MapFragment extends Fragment implements MapContract.View {

    MapView mMapView;
    private MapContract.Presenter mMapPresenter;

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        mMapPresenter = checkNotNull(presenter);
    }

    @Override
    public void showPoints(final List<Image> imagesData) {
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for(Image x: imagesData){
                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(x.getLat(), x.getLng()))
                            .title(formatter.format(x.getDate())));
                }
            }
        });
    }

    public MapFragment(){}

    public static MapFragment newInstance(){
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMapPresenter.start();
            }
        });

        return rootView;
    }
}