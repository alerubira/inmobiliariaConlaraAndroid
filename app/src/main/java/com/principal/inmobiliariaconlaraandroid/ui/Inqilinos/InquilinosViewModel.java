package com.principal.inmobiliariaconlaraandroid.ui.Inqilinos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.principal.inmobiliariaconlaraandroid.clases.Inmueble;
import com.principal.inmobiliariaconlaraandroid.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinosViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Inmueble>> mInmueblesContratos = new MutableLiveData<>();
    private MutableLiveData<String> mMensage=new MutableLiveData<>();

    public InquilinosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Inmueble>> getmInmueblesContratos() {
        return mInmueblesContratos;
    }
    public LiveData<String>getMMensage(){return mMensage;}
    public void obtenerInmueblesContartos(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmmobiliariaSetvice api = ApiClient.getApiInmobiliaria();
        Call<List<Inmueble>> llamada = api.obtenerInmueblesConContratoVigente("Bearer "+token);
        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()){
                    mInmueblesContratos.postValue(response.body());
                } else {
                    //cambiar por mutable y poner en el observer un dialogo
                    // Toast.makeText(getApplication(), "No hay inmuebles disponibles: "+response.message(), Toast.LENGTH_SHORT).show();
                    mMensage.postValue("No hay inmuebles disponibles");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                //Toast.makeText(getApplication(), "Error en servidor: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                mMensage.postValue("Error interno del servidor: "+t.getMessage());
            }
        });
    }
}