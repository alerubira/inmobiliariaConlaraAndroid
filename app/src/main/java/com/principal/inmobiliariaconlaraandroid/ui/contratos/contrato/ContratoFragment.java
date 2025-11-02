package com.principal.inmobiliariaconlaraandroid.ui.contratos.contrato;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.principal.inmobiliariaconlaraandroid.R;
import com.principal.inmobiliariaconlaraandroid.clases.Contrato;
import com.principal.inmobiliariaconlaraandroid.databinding.FragmentContratoBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ContratoFragment extends Fragment {

    public static ContratoFragment newInstance() {
        return new ContratoFragment();
    }

    private ContratoViewModel mViewModel;
    private FragmentContratoBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ContratoViewModel.class);
        binding= FragmentContratoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Contrato")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
            }
        });
        mViewModel.getMContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                //cargar la vista
                binding.txvIdContrato.setText(contrato.getIdContrato()+" ");
                Date fecha = contrato.getFechaInicio(); // Supongamos que devuelve un java.util.Date
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                binding.txvFechaInicio.setText(sdf.format(fecha)+"");
                Date fecha1 = contrato.getFechaInicio(); // Supongamos que devuelve un java.util.Date
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                binding.txvFechaFinal.setText(sdf1.format(fecha1)+"");
                binding.txvMonto.setText(contrato.getMontoAlquiler()+"");
                binding.txvInquilino.setText(contrato.getInquilino().getNombre()+"  "+contrato.getInquilino().getApellido());
                binding.txvDireccion.setText(contrato.getInmueble().getDireccion());


            }
        });
        mViewModel.getMHayContrato().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if (b) {
                    //armar e bundle estraer el contrato del mutable con el get,envia a pagosfragment
                    Contrato contrato = mViewModel.getMContrato().getValue();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("contrato", contrato);
                    // Navegar al fragment de pagos
                    Navigation.findNavController(requireView())
                            .navigate(R.id.pagosFragment, bundle);
                    mViewModel.cambiarMHayContrato();
                }
            }
        });
        binding.btnVerPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.verificarContrato();
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
        mViewModel = new ViewModelProvider(this).get(ContratoViewModel.class);
        // TODO: Use the ViewModel
    }


}