package com.principal.inmobiliariaconlaraandroid.ui.logout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogoutViewModel extends ViewModel {
    private MutableLiveData<String> mDeslogueado=new MutableLiveData<>();
    public LiveData<String> getMDeslogueado(){
        return mDeslogueado;
    }
    public void desloquearse(){
        mDeslogueado.setValue("deslogueado");
    }
    // TODO: Implement the ViewModel
}