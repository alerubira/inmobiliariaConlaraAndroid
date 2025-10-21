package com.principal.inmobiliariaconlaraandroid.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.principal.inmobiliariaconlaraandroid.clases.Inmueble;
import com.principal.inmobiliariaconlaraandroid.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    MutableLiveData<Inmueble> mInmueble=new MutableLiveData<>();
    MutableLiveData<String> mMensage=new MutableLiveData<>();
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);

    }
    LiveData<Inmueble> getMInmueble(){
        return mInmueble;
    }
    LiveData<String> getMMensage(){
        return mMensage;
    }
public void obtenerInmueble(Bundle inmuebleBundle){
        Inmueble inmueble=(Inmueble) inmuebleBundle.getSerializable("inmueble");
        if(inmueble != null){
            this.mInmueble.setValue(inmueble);
        }
    }
public void actualizarInmueble(Boolean disponible){
        Inmueble inmueble=new Inmueble();
        inmueble.setDisponible(disponible);
        inmueble.setIdInmueble(this.getMInmueble().getValue().getIdInmueble());
    String token = ApiClient.leerToken(getApplication());
    ApiClient.InmmobiliariaSetvice api = ApiClient.getApiInmobiliaria();
   Call<Inmueble> llamada=api.actualizarInmueble("Bearer "+token,inmueble);
   llamada.enqueue(new Callback<Inmueble>() {
       @Override
       public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
           if(response.isSuccessful()){
               if(disponible){
                   mMensage.postValue("El Inmueble ya esta Habilitado");
               }else{
                   mMensage.postValue("El Inmueble ya No esta Habilitado");
               }
           }else{
               mMensage.postValue(("Hubo un inconveniente al actualizar el ainmueble:"+response.message()));
           }
       }

       @Override
       public void onFailure(Call<Inmueble> call, Throwable t) {
              mMensage.postValue("Error del servidor:"+t.getMessage());
       }
   });
}
}