package com.principal.inmobiliariaconlaraandroid.ui.inmuebles;

import android.app.Application;
import android.widget.Toast;

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

public class ListaInmueblesViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText = new MutableLiveData<>();
    private final MutableLiveData<List<Inmueble>> mInmueble = new MutableLiveData<>();

    public ListaInmueblesViewModel(@NonNull Application application) {
        super(application);
        leerInmuebles();
    }

    public LiveData<String> getmText() {
        return mText;
    }

    public LiveData<List<Inmueble>> getmInmueble() {
        return mInmueble;
    }



    public void leerInmuebles(){
        String token = ApiClient.leerToken(getApplication());
        ApiClient.InmmobiliariaSetvice api = ApiClient.getApiInmobiliaria();
        Call<List<Inmueble>> llamada = api.obtenerInmuebles("Bearer "+token);
        llamada.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()){
                    mInmueble.postValue(response.body());
                } else {
                    //cambiar por mutable y poner en el observer un dialogo
                   // Toast.makeText(getApplication(), "No hay inmuebles disponibles: "+response.message(), Toast.LENGTH_SHORT).show();
                    mText.postValue("No hay inmuebles disponibles");
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable t) {
                //Toast.makeText(getApplication(), "Error en servidor: "+t.getMessage(), Toast.LENGTH_SHORT).show();
                mText.postValue("Error interno del servidor: "+t.getMessage());
            }
        });
    }


}