package com.principal.inmobiliariaconlaraandroid.ui.perfil;

import static androidx.lifecycle.AndroidViewModel_androidKt.getApplication;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.principal.inmobiliariaconlaraandroid.clases.Propietario;
import com.principal.inmobiliariaconlaraandroid.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {

  //  private final MutableLiveData<String> mText;
     private MutableLiveData<Propietario> mPropietario=new MutableLiveData<>();
     private MutableLiveData<String> mNoEncontrado=new MutableLiveData<>();
    private Context context;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }


    /*  public LiveData<String> getText() {
          return mText;
      }*/
  public LiveData<Propietario> getMPropietario(){
      return mPropietario;
  }
    public LiveData<String> getMNoEncontrado(){
        return mNoEncontrado;
    }
    public void obtenerPropietario(){
        ApiClient.InmmobiliariaSetvice api = ApiClient.getApiInmobiliaria();
        String token= ApiClient.leerToken(context);
        Call<Propietario> propietario = api.obtenerPropietario("Bearer"+token);
        propietario.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
               // mNoEncontrado.postValue(response.toString());
                if (response.isSuccessful()) {
                    Propietario p=response.body();
                    mPropietario.postValue(p);
                }else{
                   mNoEncontrado.postValue("No Se encontro Propietario");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
               mNoEncontrado.postValue("Error del servidor al buscar propietario"+t.getMessage());
            }
        });
    }
}