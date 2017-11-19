package by.test.dartlen.gallery.map;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.Mapper;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.data.remote.retrofit.image.DataImage;
import by.test.dartlen.gallery.gallery.MainPageActivity;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 31.10.2017.
 */

public class MapFragment extends Fragment implements MapContract.View {

    MapView mMapView;
    private GoogleMap googleMap;
    private MapContract.Presenter mMapPresenter;

    private Mapper mMapper;
    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        mMapPresenter = checkNotNull(presenter);
    }

    @Override
    public void showPoints(final List<DataImage> imagesData) {
        final List<Images> iData = mMapper.toImagesFromDataImages(imagesData);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                googleMap = mMap;
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for(Images x: iData){
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
        mMapper = new Mapper(getContext());
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                mMapPresenter.start();


                //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                //googleMap.getUiSettings().setZoomControlsEnabled(true);
                /*boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                        .getString(R.raw.style_json.json)));*/
                /*boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                        .getString(R.raw.style_json)));*/

                /*LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}