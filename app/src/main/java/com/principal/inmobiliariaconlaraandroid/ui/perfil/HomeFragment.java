package com.principal.inmobiliariaconlaraandroid.ui.perfil;

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

import com.principal.inmobiliariaconlaraandroid.R;
import com.principal.inmobiliariaconlaraandroid.clases.Propietario;
import com.principal.inmobiliariaconlaraandroid.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        homeViewModel.getMInforme().observe(getViewLifecycleOwner(), new Observer<String>() {
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
        });
        homeViewModel.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.edtApellido.setText(propietario.getApellido());
                binding.edtDni.setText(propietario.getDni());
                binding.edtIdPropietario.setText(propietario.getIdPropietario()+"");
                binding.edtNombre.setText(propietario.getNombre());
                binding.edtEmail.setText(propietario.getEmail());
                binding.edtTelefono.setText(propietario.getTelefono());
            }
        });
        homeViewModel.getMTextBtn().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditar.setText(s);
            }
        });
        homeViewModel.getmEstadosEdt().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.edtDni.setEnabled(true);
                    binding.edtTelefono.setEnabled(true);
                    binding.edtEmail.setEnabled(true);
                    binding.edtNombre.setEnabled(true);
                    binding.edtApellido.setEnabled(true);
                }else{
                    binding.edtDni.setEnabled(false);
                    binding.edtTelefono.setEnabled(false);
                    binding.edtEmail.setEnabled(false);
                    binding.edtNombre.setEnabled(false);
                    binding.edtApellido.setEnabled(false);
                }

            }
        });
        binding.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn=binding.btnEditar.getText().toString();
                //int idPropietario=binding.edtIdPropietario.getId();
                String dni=binding.edtDni.getText().toString();
                String apellido=binding.edtApellido.getText().toString();
                String nombre=binding.edtNombre.getText().toString();
                String email=binding.edtEmail.getText().toString();
                String telefono=binding.edtTelefono.getText().toString();
                homeViewModel.editar(btn,dni,apellido,nombre,email,telefono);
            }
        });
        binding.fabCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.cambiarClaveFragment,bundle);

            }
        });
        homeViewModel.obtenerPropietario();
        //final TextView textView = binding.textHome;
      //  homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}