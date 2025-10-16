package com.principal.inmobiliariaconlaraandroid.ui.perfil;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.principal.inmobiliariaconlaraandroid.clases.Propietario;
import com.principal.inmobiliariaconlaraandroid.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {

    private  MutableLiveData<String> mTextbtn=new MutableLiveData<>();
     private MutableLiveData<Propietario> mPropietario=new MutableLiveData<>();
     private MutableLiveData<String> mInforme =new MutableLiveData<>();
     private MutableLiveData<Boolean>mEstadosEdt=new MutableLiveData<Boolean>();
    private Context context;
    private Propietario propietario;
    private String mensage;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }



  public LiveData<Propietario> getMPropietario(){
      return mPropietario;
  }
    public LiveData<String> getMInforme(){
        return mInforme;
    }
    public LiveData<String>getMTextBtn(){
        return mTextbtn;
    }
    public LiveData<Boolean>getmEstadosEdt(){
        return mEstadosEdt;
    }
    public void obtenerPropietario(){
        mTextbtn.postValue("Editar");
        ApiClient.InmmobiliariaSetvice api = ApiClient.getApiInmobiliaria();
        String token= ApiClient.leerToken(context);
        Call<Propietario> llamada = api.obtenerPropietario("Bearer "+token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
               // mNoEncontrado.postValue(response.toString());
                if (response.isSuccessful()) {
                     propietario=response.body();
                    mPropietario.postValue(propietario);
                }else{
                   mInforme.postValue("No Se encontro Propietario");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
               mInforme.postValue("Error del servidor al buscar propietario: "+t.getMessage());
            }
        });
    }
    public void editar(String btn,String dni,String apellido,String nombre,String email,String telefono){
      if(btn.equalsIgnoreCase("editar")){
          mTextbtn.setValue("Guardar");
          mEstadosEdt.setValue(true);
      }else{
          if(verificarCampos(dni,apellido,nombre, email,telefono)){
              guardarCambios( dni, apellido, nombre, email, telefono);
              mTextbtn.postValue("Editar");
              mEstadosEdt.postValue(false);

          }else{
              mInforme.setValue("Algo esta mal con algun campo modificado "+mensage);
          }

      }
    }
    public boolean verificarCampos(String dni,String apellido,String nombre,String email,String telefono) {
        boolean bandera = true;
        mensage = "";
        // Limpiar espacios y caracteres invisibles
        if (dni != null) dni = dni.trim();
        if (telefono != null) telefono = telefono.trim();
        if (apellido != null) apellido = apellido.trim();
        if (nombre != null) nombre = nombre.trim();
        if (email != null) email = email.trim();
        if (dni == null || apellido == null || nombre == null || email == null || telefono == null ||
                dni.isEmpty() || apellido.isEmpty() || nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            mensage = "null o vacio algun campo";
            bandera = false;
        } else {
            // Validar que DNI y teléfono solo contengan dígitos
            if (!dni.matches("\\d+") || !telefono.matches("\\d+")) {
                mensage = "El teléfono o el DNI no son números";
                bandera = false;
            } else {
                // Verificar longitud del DNI (7 u 8 dígitos)
                if (dni.length() < 7 || dni.length() > 8) {
                    mensage = "El DNI tiene menos de 7 o más de 8 caracteres";
                    bandera = false;
                }

                // Verificar longitud del teléfono (más de 7 dígitos)
                if (telefono.length() < 7) {
                    mensage = "El teléfono tiene menos de 7 caracteres";
                    bandera = false;
                }

            }

        }
        return bandera;
    }
        public void guardarCambios (String dni, String apellido, String nombre, String email, String telefono){

            ApiClient.InmmobiliariaSetvice api = ApiClient.getApiInmobiliaria();
            String token = ApiClient.leerToken(context);
            Propietario p = new Propietario(propietario.getIdPropietario(), dni, nombre, apellido, propietario.getEmail(), null, telefono);
            Call<Propietario> llamada = api.actualizarPropietario("Bearer " + token, p);
            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        propietario = response.body();
                        mPropietario.postValue(propietario);
                        mInforme.postValue("Propietario modificado con exito");
                    } else {
                        mInforme.postValue("Error almodificar el Propietario" + response.message());
                    }

                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                        mInforme.postValue("Error del servidor : "+t.getMessage());
                }
            });
        }
    }