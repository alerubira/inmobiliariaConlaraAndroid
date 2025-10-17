package com.principal.inmobiliariaconlaraandroid.ui.perfil;

import android.app.Application;
import android.content.Context;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;



import androidx.lifecycle.ViewModel;

import com.principal.inmobiliariaconlaraandroid.request.ApiClient;

public class CambiarClaveViewModel extends AndroidViewModel {
    private MutableLiveData<String> mMensage=new MutableLiveData<>();
    private MutableLiveData<String> mMensageError=new MutableLiveData<>();
    private Context context;
    private String clave;
    private String claveNueva;
    public CambiarClaveViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }
    public LiveData<String>getMMensage(){
        return mMensage;
    }
    public LiveData<String>getMMensageError(){
        return mMensageError;
    }
    public void verificarIgualdad(String claveA,String claveNuevaA,String claveNuevaRepetida){
        if(claveNuevaA==null||claveNuevaA.isEmpty()){
            mMensageError.setValue("La clave nueva debe contener parametros");
        }else{
            if (claveNuevaA.equals(claveNuevaRepetida)) {
                mMensage.setValue("Seguro desea cambiar la clave??");
                clave=claveA;
                claveNueva=claveNuevaA;
            }else{
                mMensageError.setValue("La clave nueva y la confirmacion  deben ser iguales");
            }
        }

    }
    public void cambiarClave(){
        ApiClient.InmmobiliariaSetvice api = ApiClient.getApiInmobiliaria();
        String token= ApiClient.leerToken(context);
        Call<Void> llamada=api.cambiarClave("Bearer "+token,clave,claveNueva);
        llamada.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    mMensageError.postValue("Clave modificada con exito");
                }else{
                    mMensageError.postValue("La clave no se modifico");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                 mMensageError.postValue("Error del servidor al cambiar la clave");
            }
        });
    }
}