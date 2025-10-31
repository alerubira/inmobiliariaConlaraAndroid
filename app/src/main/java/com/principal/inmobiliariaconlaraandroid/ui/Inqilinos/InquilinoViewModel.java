package com.principal.inmobiliariaconlaraandroid.ui.Inqilinos;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.principal.inmobiliariaconlaraandroid.clases.Contrato;
import com.principal.inmobiliariaconlaraandroid.clases.Inmueble;
import com.principal.inmobiliariaconlaraandroid.clases.Inquilino;
import com.principal.inmobiliariaconlaraandroid.clases.Propietario;
import com.principal.inmobiliariaconlaraandroid.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {
    private MutableLiveData<String>mMensage=new MutableLiveData<>();
    private MutableLiveData<Inquilino>mInquilino=new MutableLiveData<>();
    private Contrato contrato;
    private Context context;

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }
    public LiveData<String>getMMensage(){
        return mMensage;
    }
    public LiveData<Inquilino>getMInquilino(){
        return mInquilino;
    }
    public void recibirInmueble(Bundle bundle) {
        if (bundle != null) {
            Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");

            obtenerContratoPorInmeble(inmueble.getIdInmueble());

        }else{
            mMensage.setValue("No se recibio ningun Inmueble");
        }
    }
    private void obtenerContratoPorInmeble(int idInmueble){
        ApiClient.InmmobiliariaSetvice api = ApiClient.getApiInmobiliaria();
        String token= ApiClient.leerToken(context);
        Call<Contrato> llamada = api.obtenerContrato("Bearer "+token,idInmueble);
        llamada.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {

                if (response.isSuccessful()) {
                    contrato=response.body();
                    //extraer en inquilino
                    mInquilino.postValue(contrato.getInquilino()) ;
                   // mPropietario.postValue(propietario);
                }else{
                    mMensage.postValue("No Se encontro Contrato");
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                mMensage.postValue("Error del servidor al buscar contrato: "+t.getMessage());
            }
        });
    }
    }
