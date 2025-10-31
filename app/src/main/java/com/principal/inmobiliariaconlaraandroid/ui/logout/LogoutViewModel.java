package com.principal.inmobiliariaconlaraandroid.ui.logout;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.principal.inmobiliariaconlaraandroid.request.ApiClient;

public class LogoutViewModel extends AndroidViewModel {
    private MutableLiveData<String> mDeslogueado=new MutableLiveData<>();

    public LogoutViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMDeslogueado(){
        return mDeslogueado;
    }
    public void desloquearse(){
        ApiClient.borrarToken(getApplication());

        mDeslogueado.setValue("deslogueado");
    }

}