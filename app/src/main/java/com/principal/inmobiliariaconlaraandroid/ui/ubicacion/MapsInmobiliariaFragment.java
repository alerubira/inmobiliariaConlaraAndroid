package com.principal.inmobiliariaconlaraandroid.ui.ubicacion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.principal.inmobiliariaconlaraandroid.R;

public class MapsInmobiliariaFragment extends Fragment {
private MapsInmubiliariaFragmentViewModel mv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(MapsInmubiliariaFragmentViewModel.class);
        mv.getMMapa().observe(getViewLifecycleOwner(), new Observer<MapsInmubiliariaFragmentViewModel.MapaActual>() {
            @Override
            public void onChanged(MapsInmubiliariaFragmentViewModel.MapaActual mapaActual) {
                SupportMapFragment mapFragment =
                        (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(mapaActual);
            }
        });
        mv.cargarMapa();
        return inflater.inflate(R.layout.fragment_maps_inmobiliaria, container, false);
    }


}