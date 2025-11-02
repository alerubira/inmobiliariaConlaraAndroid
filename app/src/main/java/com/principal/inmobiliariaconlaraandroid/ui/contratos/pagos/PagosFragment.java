package com.principal.inmobiliariaconlaraandroid.ui.contratos.pagos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.principal.inmobiliariaconlaraandroid.R;
import com.principal.inmobiliariaconlaraandroid.clases.Pago;
import com.principal.inmobiliariaconlaraandroid.databinding.FragmentPagosBinding;
import com.principal.inmobiliariaconlaraandroid.ui.inmuebles.InmuebleAdapter;

import java.util.List;

public class PagosFragment extends Fragment {

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    private PagosViewModel mViewModel;
    private FragmentPagosBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);
        binding=FragmentPagosBinding.inflate(inflater, container, false);
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Lista de Pagos")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        });
        mViewModel.getMPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                PagoAdapter adapter = new PagoAdapter(pagos, getContext());
                GridLayoutManager glm = new GridLayoutManager(getContext(), 1);
                RecyclerView rv = binding.rvPagos;
                rv.setAdapter(adapter);
                rv.setLayoutManager(glm);
            }
        });
        // Recuperar el bundle
        Bundle bundle = getArguments();
        mViewModel.recibirContrato(bundle);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PagosViewModel.class);
        // TODO: Use the ViewModel
    }

}