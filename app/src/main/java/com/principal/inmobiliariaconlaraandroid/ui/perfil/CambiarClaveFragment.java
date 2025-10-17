package com.principal.inmobiliariaconlaraandroid.ui.perfil;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
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
import com.principal.inmobiliariaconlaraandroid.databinding.FragmentCambiarClaveBinding;

public class CambiarClaveFragment extends Fragment {

    private CambiarClaveViewModel mViewModel;
    private FragmentCambiarClaveBinding binding;

    public static CambiarClaveFragment newInstance() {
        return new CambiarClaveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CambiarClaveViewModel.class);
        binding=FragmentCambiarClaveBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        binding.btnCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String clave=binding.edtClave.getText().toString();
                String claveNueva=binding.edtClaveNueva.getText().toString();
                String claveNuevaRepetida=binding.edtClaveNuevaRepetida.getText().toString();
                mViewModel.verificarIgualdad(clave,claveNueva,claveNuevaRepetida);
            }
        });
        mViewModel.getMMensageError().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Propietario")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        }) ;
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Propietario")
                        .setMessage(s)

                        .setNegativeButton("No Cambiar", (dialog, which) -> {
                            binding.edtClave.setText("");
                            binding.edtClaveNueva.setText("");
                            binding.edtClaveNuevaRepetida.setText("");
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .setPositiveButton("Seguro desea Cambiar la Clave",((dialog, which) -> {
                           mViewModel.cambiarClave();
                            binding.edtClave.setText("");
                            binding.edtClaveNueva.setText("");
                            binding.edtClaveNuevaRepetida.setText("");
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        }))
                        .show();
            }

        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CambiarClaveViewModel.class);
        // TODO: Use the ViewModel
    }

}