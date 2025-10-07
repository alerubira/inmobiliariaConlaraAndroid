package com.principal.inmobiliariaconlaraandroid.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class InicioActivityViewModel extends AndroidViewModel {
    private MutableLiveData<String> mLogueado=new MutableLiveData<String>();
    private MutableLiveData<String> mNoLogueado=new MutableLiveData<String>();
    public InicioActivityViewModel(@NonNull Application application) {
        super(application);
    }
        public LiveData<String> getMLogueado(){
            return mLogueado;
        }
        public LiveData<String> getMNoLogueado(){
            return mNoLogueado;
        }
        public void loguear(String usuario,String password){
            if(usuario.equals("ale") && password.equals("123")){
                mLogueado.setValue("Logueado");
            }else{
                mNoLogueado.setValue("Algo esta mal con el login");
            }
        }
    }

