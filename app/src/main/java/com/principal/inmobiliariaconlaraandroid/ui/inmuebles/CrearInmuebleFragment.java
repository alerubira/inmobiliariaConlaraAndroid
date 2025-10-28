package com.principal.inmobiliariaconlaraandroid.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.principal.inmobiliariaconlaraandroid.R;
import com.principal.inmobiliariaconlaraandroid.databinding.FragmentCrearInmuebleBinding;
import com.principal.inmobiliariaconlaraandroid.databinding.FragmentDetalleInmuebleBinding;

public class CrearInmuebleFragment extends Fragment {

    private CrearInmuebleViewModel mViewModel;
    private FragmentCrearInmuebleBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Intent intent;

    public static CrearInmuebleFragment newInstance() {
        return new CrearInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CrearInmuebleViewModel.class);
        binding=FragmentCrearInmuebleBinding.inflate(getLayoutInflater());
        View root=binding.getRoot();
        abrirGaleria();
        mViewModel.getMMensage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
               abrirDialogo(s);
            }
        });
        mViewModel.getMMensageExito().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
               abrirDialogo(s);
            limpiarCampos();
            }
        });
        binding.btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arl.launch(intent);

            }
        });

        mViewModel.getMuri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.imgView.setImageURI(uri);
            }
        });
        binding.btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//String direccion, String valor, String tipo, String uso, String ambientes, String superficie, boolean disponible
                mViewModel.cargarInmueble(binding.etDireccion.getText().toString(),
                        binding.etValor.getText().toString(),
                        binding.spTipo.getSelectedItem().toString(),
                        binding.spUso.getSelectedItem().toString(),
                        binding.etAmbientes.getText().toString(),
                        binding.etSuperficie.getText().toString(),
                        binding.cbDisp.isChecked());
            }
        });
        cargarSpinerUso();
        cargarSpinerTipo();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearInmuebleViewModel.class);
        // TODO: Use the ViewModel
    }
    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//Es para abrir la galeria
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //Log.d("AgregarInmuebleFragment", "Result: " + result);
                mViewModel.recibirFoto(result);

            }
        });
    }
    private void abrirDialogo(String s){
        new AlertDialog.Builder(getContext())
                .setTitle("Crear Inmueble")
                .setMessage(s)

                .setNegativeButton("Cerrar", (dialog, which) -> {
                    // Solo cierra el diálogo
                    dialog.dismiss();
                })
                .show();
    }
    private void limpiarCampos(){
        binding.etAmbientes.setText("");
        binding.etDireccion.setText("");
        binding.etSuperficie.setText("");
        binding.etValor.setText("");

       // binding.etUso.setText("");
        //binding.etTipo.setText("");
        binding.imgView.setImageResource(0);
        binding.cbDisp.setChecked(false);
    }
    private void cargarSpinerUso(){
        String[] opciones = {"Seleccione un tipo de uso","Particular", "Comercial", "Mixto"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),                               // contexto
                android.R.layout.simple_spinner_item, // diseño del ítem
                opciones                            // los datos
        );
        binding.spUso.setAdapter(adapter);// se lo asignás al Spinner
        // Mostrar el primer ítem por defecto
        binding.spUso.setSelection(0);
        // Listener para manejar selección
        binding.spUso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Primer ítem: hint, no hacer nada
                    return;
                }

                // Opciones válidas
                String seleccion = parent.getItemAtPosition(position).toString();
                //Toast.makeText(requireContext(), "Seleccionaste: " + seleccion, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

    }
    private void cargarSpinerTipo(){
        String[] opciones = {"Seleccione un tipo de Inmueble","Casa", "Departamento", "Galpon","Local"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),                               // contexto
                android.R.layout.simple_spinner_item, // diseño del ítem
                opciones                            // los datos
        );
        binding.spTipo.setAdapter(adapter);// se lo asignás al Spinner
        // Mostrar el primer ítem por defecto
        binding.spTipo.setSelection(0);
        // Listener para manejar selección
        binding.spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Primer ítem: hint, no hacer nada
                    return;
                }

                // Opciones válidas
                String seleccion = parent.getItemAtPosition(position).toString();
                //Toast.makeText(requireContext(), "Seleccionaste: " + seleccion, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });

    }

}