package com.principal.inmobiliariaconlaraandroid.inicio;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.principal.inmobiliariaconlaraandroid.MainActivity;
import com.principal.inmobiliariaconlaraandroid.R;
import com.principal.inmobiliariaconlaraandroid.databinding.ActivityInicioBinding;

public class InicioActivity extends AppCompatActivity {
  private InicioActivityViewModel mv;
  private ActivityInicioBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInicioBinding.inflate(getLayoutInflater());
        mv= ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(InicioActivityViewModel.class);
        setContentView(binding.getRoot());
        mv.getMNoLogueado().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                new AlertDialog.Builder(InicioActivity.this)
                        .setTitle("Login")
                        .setMessage(s)

                        .setNegativeButton("Cerrar", (dialog, which) -> {
                            // Solo cierra el diálogo
                            dialog.dismiss();
                        })
                        .show();
                binding.edtUsuario.setText("");
                binding.edtPassword.setText("");
            }

        });
        mv.getMLogueado().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Intent intent = new Intent(InicioActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // opcional, evita volver atrás al Inicio
                binding.edtUsuario.setText("");
                binding.edtPassword.setText("");
            }
        });
        binding.btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario=binding.edtUsuario.getText().toString();
                String password=binding.edtPassword.getText().toString();
                mv.loguear(usuario,password);
            }
        });

    }
    }
