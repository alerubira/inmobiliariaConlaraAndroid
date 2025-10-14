package com.principal.inmobiliariaconlaraandroid.inicio;

import android.app.Application;
import android.content.Context;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.principal.inmobiliariaconlaraandroid.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mLogueado=new MutableLiveData<>();
    private MutableLiveData<String> mNoLogueado=new MutableLiveData<>();
    private Context context;
    public InicioActivityViewModel(@NonNull Application application) {
        super(application);
        context=getApplication();
    }
        public LiveData<String> getMLogueado(){
            return mLogueado;
        }
        public LiveData<String> getMNoLogueado(){
            return mNoLogueado;
        }
        public void loguear(String mail,String clave){
            ApiClient.InmmobiliariaSetvice api = ApiClient.getApiInmobiliaria();

            Call<String> llamada = api.login(mail, clave);

            llamada.enqueue(new Callback<String>() {

                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String token= response.body();
                        ApiClient.guardarToken(context, token);
                       mLogueado.postValue("logueado");
                    } else{

                        mNoLogueado.postValue("Usuario y/o contraseña Incorrecta; reintente");

                      }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                      mNoLogueado.postValue("Error intrerno del servidor: "+t.getMessage());
                }
            });
            }
    }

