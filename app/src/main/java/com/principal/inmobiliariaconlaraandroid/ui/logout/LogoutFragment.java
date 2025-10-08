package com.principal.inmobiliariaconlaraandroid.ui.logout;

import static androidx.lifecycle.AndroidViewModel_androidKt.getApplication;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.principal.inmobiliariaconlaraandroid.R;

public class LogoutFragment extends Fragment {

    private LogoutViewModel mViewModel;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
   mViewModel=new ViewModelProvider(this).get(LogoutViewModel.class);

    mViewModel.getMDeslogueado().observe(this, new Observer<String>() {
        @Override
        public void onChanged(String s) {
            // Cierra completamente la aplicación
            requireActivity().finishAffinity();
        }
    });
        return inflater.inflate(R.layout.fragment_logout, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LogoutViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume() {
        super.onResume();
       new AlertDialog.Builder(requireContext())
                .setTitle("Cerrar sesión")
                .setMessage("¿Deseas salir de la aplicación?")
                .setPositiveButton("Sí, salir", (dialog, which) -> {
                    mViewModel.desloquearse();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}