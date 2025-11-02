package com.principal.inmobiliariaconlaraandroid.ui.contratos.pagos;

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

public class PagosViewModel extends AndroidViewModel {
    private MutableLiveData<String> mMensage=new MutableLiveData<>();
    private MutableLiveData<List<Pago>> mPagos=new MutableLiveData<>();
    private Context context;
    public PagosViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }
    public LiveData<String>getMMensage(){
        return mMensage;
    }
    public LiveData<List<Pago>>getMPagos(){
        return mPagos;
    }
    public void recibirContrato(Bundle bundle){
        if (bundle != null) {
            Contrato contrato = (Contrato) bundle.getSerializable("contrato");
            obtenerPagosPorContrato(contrato.getIdContrato());

        }else{
            mMensage.setValue("No se recibio ningun Inmueble");
        }
    }
    private void obtenerPagosPorContrato(int idContrato){
        ApiClient.InmmobiliariaSetvice api = ApiClient.getApiInmobiliaria();
        String token= ApiClient.leerToken(context);
        Call<List<Pago>> llamada = api.obtenerPagos("Bearer "+token,idContrato);
        llamada.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    mPagos.postValue(response.body());

                }else{
                    mMensage.postValue("No Se encontraron pagos");
                }

            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable t) {
                mMensage.postValue("Error del servidor al buscar pagos: "+t.getMessage());
            }
        });
    }



}