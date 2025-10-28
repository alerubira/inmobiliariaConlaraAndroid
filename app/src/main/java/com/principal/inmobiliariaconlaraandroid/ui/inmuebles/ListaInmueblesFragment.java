package com.principal.inmobiliariaconlaraandroid.ui.inmuebles;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.principal.inmobiliariaconlaraandroid.R;
import com.principal.inmobiliariaconlaraandroid.clases.Inmueble;
import com.principal.inmobiliariaconlaraandroid.databinding.FragmentListaInmueblesBinding;
import com.principal.inmobiliariaconlaraandroid.ui.perfil.HomeFragment;

import java.util.List;

public class ListaInmueblesFragment extends Fragment {

    private FragmentListaInmueblesBinding binding;

    private ListaInmueblesViewModel mViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         mViewModel =
                new ViewModelProvider(this).get(ListaInmueblesViewModel.class);

        binding = FragmentListaInmueblesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel.getmInmueble().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InmuebleAdapter adapter = new InmuebleAdapter(inmuebles, getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
                RecyclerView rv = binding.rvListaInmuebles;

                rv.setAdapter(adapter);
                rv.setLayoutManager(glm);
            }
        });
        binding.fabCrearInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                NavHostFragment.findNavController(ListaInmueblesFragment.this)
                        .navigate(R.id.crearInmuebleFragment,bundle);
            }
        });
        mViewModel.getmText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Lista Inmuebles")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        });

        mViewModel.leerInmuebles();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}