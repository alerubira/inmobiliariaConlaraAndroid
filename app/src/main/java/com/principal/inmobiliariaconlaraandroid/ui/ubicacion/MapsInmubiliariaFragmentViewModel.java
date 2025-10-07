package com.principal.inmobiliariaconlaraandroid.ui.ubicacion;

import android.app.Application;
import android.content.Context;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsInmubiliariaFragmentViewModel extends AndroidViewModel {
    private MutableLiveData<MapaActual> mMapa;
    private Context context;
    public MapsInmubiliariaFragmentViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }
    public LiveData<MapaActual> getMMapa() {
        if (mMapa == null) {
            mMapa = new MutableLiveData<>();
        }
        return mMapa;
    }
    public void cargarMapa() {
        MapaActual mapaActual = new MapaActual();
        mMapa.setValue(mapaActual);
    }
    public class MapaActual implements OnMapReadyCallback {

        LatLng ubi;

        public MapaActual() {
            this.ubi = new LatLng(-32.730268, -65.280712);
        }


        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            MarkerOptions marcador = new MarkerOptions();
            marcador.position(ubi);
            marcador.title("Inmobiliaria");

            googleMap.addMarker(marcador);
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            CameraPosition cam = new CameraPosition.Builder()
                    .target(ubi)
                    .zoom(30)
                    .bearing(45)
                    .tilt(15)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cam);
            googleMap.animateCamera(cameraUpdate);
        }
    }
}
