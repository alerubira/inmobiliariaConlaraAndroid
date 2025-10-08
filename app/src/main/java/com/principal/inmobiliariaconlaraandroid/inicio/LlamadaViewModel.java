package com.principal.inmobiliariaconlaraandroid.inicio;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class LlamadaViewModel extends AndroidViewModel {
    private SensorManager manager;
    private Sensor acelerometro;
    private SensorEventListener listener;
    private long ultimoTiempo = 0;
    private static final int SHAKE_THRESHOLD = 2000; // sensibilidad del shake
    private MutableLiveData<String> mSensorActivado = new MutableLiveData<>();
    private MutableLiveData<String> mNumeroTelefono = new MutableLiveData<>();

    public LlamadaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getMSensorActivado() {
        return mSensorActivado;
    }

    public LiveData<String> getMNumeroTelefono() {
        return mNumeroTelefono;
    }

    // Activar el acelerómetro
    public void activarAcelerometro() {
        manager = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);

        if (manager != null) {
            List<Sensor> sensores = manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
            if (!sensores.isEmpty()) {
                acelerometro = sensores.get(0);
                listener = new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values[2];

                        long tiempoActual = System.currentTimeMillis();
                        long diferenciaTiempo = tiempoActual - ultimoTiempo;
                        if (diferenciaTiempo > 100) {
                            float velocidad = Math.abs(x + y + z) / diferenciaTiempo * 10000;

                            if (velocidad > SHAKE_THRESHOLD) {

                                mSensorActivado.postValue("Sacudido en: " + tiempoActual);
                            }

                            ultimoTiempo = tiempoActual;
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    }
                };

                // registramos el listener
                manager.registerListener(listener, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);

            }
        }
    }

    // Desactivar el acelerómetro
    public void desactivarAcelerometro() {
        if (manager != null && listener != null) {
            manager.unregisterListener(listener);
        }
    }
    public void llamar(){
        mNumeroTelefono.setValue("2657677111");
    }


}
