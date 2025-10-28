package com.principal.inmobiliariaconlaraandroid.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.principal.inmobiliariaconlaraandroid.clases.Inmueble;
import com.principal.inmobiliariaconlaraandroid.request.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<String>mMensage=new MutableLiveData<>();
    private MutableLiveData<String>mMensageExito=new MutableLiveData<>();
    private MutableLiveData<Uri> mUri = new MutableLiveData<>();
    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);

    }
    public LiveData<String>getMMensage(){
        return mMensage;
    }
    public LiveData<String>getMMensageExito(){
        return mMensageExito;
    }
    public LiveData<Uri> getMuri(){
        return mUri;
    }
    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
           //Log.d("salada", uri.toString());
            mUri.setValue(uri);
        }
    }
    public void cargarInmueble(String direccion, String valor, String tipo, String uso, String ambientes, String superficie, boolean disponible){
        int superficiePars, ambientesPars;
        double precio;

        try{
            precio = Double.parseDouble(valor);
            superficiePars = Integer.parseInt(superficie);
            ambientesPars = Integer.parseInt(ambientes);
            if(direccion.isEmpty() || tipo.isEmpty() || uso.isEmpty() || ambientes.isEmpty() || superficie.isEmpty() || valor.isEmpty()){
                //Toast.makeText(getApplication(), "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
                mMensage.setValue("Todo los campos sonobligatorios");
                return;
            }

            if(mUri.getValue() == null) {
                //Toast.makeText(getApplication(), "Debe seleccionar una foto", Toast.LENGTH_SHORT).show();
                mMensage.setValue("La foto es obligatoria");
                return;
            }

            Inmueble inmueble = new Inmueble();
            inmueble.setDireccion(direccion);
            inmueble.setValor(precio);
            inmueble.setTipo(tipo);
            inmueble.setUso(uso);
            inmueble.setAmbientes(ambientesPars);
            inmueble.setSuperficie(superficiePars);
            inmueble.setDisponible(disponible);

            //Convertir en base a la uri
            byte[] imagen = transformarImagen();
            String inmuebleJson = new Gson().toJson(inmueble);
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);

            //Armar multipart
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

            ApiClient.InmmobiliariaSetvice inmoService = ApiClient.getApiInmobiliaria();
            String token = ApiClient.leerToken(getApplication());
            Call<Inmueble> call = inmoService.cargarInmueble("Bearer "+ token, imagenPart, inmuebleBody);
            call.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if(response.isSuccessful()){
                       // Toast.makeText(getApplication(), "Inmueble cargado exitosamente", Toast.LENGTH_SHORT).show();
                        mMensageExito.postValue("Inmueble cargado exitosamente");
                    }else{
                       // Toast.makeText(getApplication(), "Error al cargar inmueble", Toast.LENGTH_SHORT).show();
                        mMensage.postValue("Erroe al cargar el Inmueble: "+response.message());
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable t) {
                    //Toast.makeText(getApplication(), "Error al cargar inmueble", Toast.LENGTH_SHORT).show();
                    mMensage.postValue("error al cargar el imueble: "+t.getMessage());
                }
            });

        }catch(NumberFormatException nfe){
           // Toast.makeText(getApplication(), "Debe ingresar numeros en los campos de valor, superficie y ambientes", Toast.LENGTH_SHORT).show();
            mMensage.postValue("Los campos de valor , ambientes y superficie deven sen numeros validos");
            return;
        }

    }

    private byte[] transformarImagen() {
        try {
            Uri uri = mUri.getValue(); //lo puedo usar porque estoy en viewmodel
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);//Crea un canal para conectarse a un archivo
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (
                FileNotFoundException er) {
           // Toast.makeText(getApplication(), "No ha seleccinado una foto", Toast.LENGTH_SHORT).show();
            mMensage.postValue("No a seleccionado ninguna foto");
            return new byte[]{};
        }
    }

}