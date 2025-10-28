package com.principal.inmobiliariaconlaraandroid.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.principal.inmobiliariaconlaraandroid.R;
import com.principal.inmobiliariaconlaraandroid.clases.Inmueble;
import com.principal.inmobiliariaconlaraandroid.databinding.FragmentDetalleInmuebleBinding;
import com.principal.inmobiliariaconlaraandroid.inicio.InicioActivity;
import com.principal.inmobiliariaconlaraandroid.request.ApiClient;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mViewModel;
    private FragmentDetalleInmuebleBinding binding;

    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding=FragmentDetalleInmuebleBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Detalle Inmueble")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        });
        mViewModel.mInmueble.observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                binding.tvAmbientesI.setText(inmueble.getAmbientes()+"");
                binding.tvDireccionI.setText(inmueble.getDireccion());
                binding.tvUsoI.setText(inmueble.getUso());
                binding.tvIdInmueble.setText(inmueble.getIdInmueble()+"");
                binding.tvLatitudI.setText(inmueble.getLatitud()+"");
                binding.tvLatitudI.setText(inmueble.getLatitud()+"");
                binding.tvValorI.setText(inmueble.getValor()+"");
                Glide.with(getContext())
                        .load(ApiClient.URLBASE+inmueble.getImagen())
                        .placeholder(R.drawable.casa)
                        .error("null")
                        .into(binding.imgInmueble);
                binding.checkDisponible.setChecked(inmueble.isDisponible());
            }
        });
        mViewModel.obtenerInmueble(getArguments());
        binding.checkDisponible.setOnClickListener(v ->
                mViewModel.actualizarInmueble(binding.checkDisponible.isChecked()));

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }

}