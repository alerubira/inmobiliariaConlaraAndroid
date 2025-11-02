package com.principal.inmobiliariaconlaraandroid.ui.contratos.contrato;

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
import com.principal.inmobiliariaconlaraandroid.clases.Pago;
import com.principal.inmobiliariaconlaraandroid.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {
    private MutableLiveData<String>mMensage=new MutableLiveData<>();
    private MutableLiveData<Contrato>mContrato=new MutableLiveData<>();
    private MutableLiveData<Boolean>mHayContrato=new MutableLiveData<>();
    private Context context;
    public ContratoViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }
    public LiveData<String>getMMensage(){
        return mMensage;
    }

    public LiveData<Contrato>getMContrato(){
        return mContrato;
    }
    public LiveData<Boolean>getMHayContrato(){return mHayContrato;}
    public void recibirInmueble(Bundle bundle){
        if (bundle != null) {
            Inmueble inmueble = (Inmueble) bundle.getSerializable("inmueble");
            obtenerContrato(inmueble.getIdInmueble());

        }else{
            mMensage.setValue("No se recibio ningun Inmueble");
        }
    }
    public void obtenerContrato(int idInmueble){
        ApiClient.InmmobiliariaSetvice api = ApiClient.getApiInmobiliaria();
        String token= ApiClient.leerToken(context);
        Call<Contrato> llamada = api.obtenerContrato("Bearer "+token,idInmueble);
        llamada.enqueue(new Callback<Contrato>() {
            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()) {
                    mContrato.postValue(response.body());

                }else{
                    mMensage.postValue("No Se encontro el contrato");
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable t) {
                mMensage.postValue("Error del servidor al buscar el contrato: "+t.getMessage());
            }
        });
    }
    public void verificarContrato(){
        Contrato contrato = mContrato.getValue();
        if (contrato == null) {
            mMensage.setValue("No Existe contrato");
        }else{
            mHayContrato.setValue(true);

        }
    }
    public void cambiarMHayContrato(){
        mHayContrato.setValue(false);
    }


}