package com.principal.inmobiliariaconlaraandroid.ui.Inqilinos;

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

import com.principal.inmobiliariaconlaraandroid.R;
import com.principal.inmobiliariaconlaraandroid.clases.Inquilino;
import com.principal.inmobiliariaconlaraandroid.databinding.FragmentInquilinoBinding;

public class InquilinoFragment extends Fragment {

    private InquilinoViewModel mViewModel;
    private FragmentInquilinoBinding binding;

    public static InquilinoFragment newInstance() {
        return new InquilinoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(InquilinoViewModel.class);
        binding=FragmentInquilinoBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Inquilino")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        });
        mViewModel.getMInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                  binding.txvIdInquilino.setText("Codigo Interno: "+inquilino.getIdInquilino());
                  binding.txvNombreInquilino.setText("Nombre: "+inquilino.getNombre());
                  binding.txvApellidoInquilino.setText("Apellido: "+inquilino.getApellido());
                  binding.txvDniInquilino.setText("DNI: "+inquilino.getDni());
                  binding.txvTelefonoInquilino.setText("Telefono: "+inquilino.getTelefono());
                  binding.txvEmailInquilino.setText("Email: "+inquilino.getEmail());
            }
        });
        // Recuperar el bundle
        Bundle bundle = getArguments();
        mViewModel.recibirInmueble(bundle);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InquilinoViewModel.class);
        // TODO: Use the ViewModel
    }

}