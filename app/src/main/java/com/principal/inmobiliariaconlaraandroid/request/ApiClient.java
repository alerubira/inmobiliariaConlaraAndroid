package com.principal.inmobiliariaconlaraandroid.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.principal.inmobiliariaconlaraandroid.clases.Contrato;
import com.principal.inmobiliariaconlaraandroid.clases.Inmueble;
import com.principal.inmobiliariaconlaraandroid.clases.Pago;
import com.principal.inmobiliariaconlaraandroid.clases.Propietario;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClient {
    // IP de tu PC + puerto donde corre la API HTTP
    //public static final String URLBASE = "http://192.168.1.108:5164/";


    public static final String URLBASE = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";//virtual


    public static InmmobiliariaSetvice getApiInmobiliaria() {


        Gson gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(URLBASE)

                .addConverterFactory(ScalarsConverterFactory.create())

                .addConverterFactory(GsonConverterFactory.create(gson))

                .build();

        return retrofit.create(InmmobiliariaSetvice.class);
    }
    public static void guardarToken(Context context, String token){
        SharedPreferences sp=context.getSharedPreferences("token.xml",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("token",token);
        editor.apply();
    }
    public static String leerToken(Context context){
        SharedPreferences sp=context.getSharedPreferences("token.xml",Context.MODE_PRIVATE);
        return sp.getString("token",null);
    }
    public static void borrarToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("token"); // elimina solo la clave "token"
        editor.apply();
    }

    public interface InmmobiliariaSetvice{
            @FormUrlEncoded
            @POST("api/Propietarios/login")
            Call<String> login(@Field("Usuario") String u, @Field("Clave") String c);

            @GET("api/Propietarios")
            Call<Propietario>obtenerPropietario(@Header("Authorization")String token);

            @PUT("api/propietarios/actualizar")
            Call<Propietario>actualizarPropietario(@Header("Authorization")String token,@Body Propietario p);
            
            @GET("api/Inmuebles")
            Call<List<Inmueble>> obtenerInmuebles(@Header("Authorization")String token);

            @FormUrlEncoded
            @PUT("api/Propietarios/changePassword")
            Call<Void>cambiarClave(@Header("Authorization")String token,
                                   @Field("currentPassword")String claveVieja,
                                   @Field("newPassword")String claveNueva);
            @PUT("api/Inmuebles/actualizar")
            Call<Inmueble> actualizarInmueble(@Header("Authorization")String token,@Body Inmueble inmueble);

            @Multipart
            @POST("api/Inmuebles/cargar")
            Call<Inmueble>cargarInmueble(@Header("Authorization")String token,
                                         @Part MultipartBody.Part imagen,
                                         @Part ("inmueble")RequestBody inmueble
            );
            @GET("api/Inmuebles/GetContratoVigente")
            Call<List<Inmueble>> obtenerInmueblesConContratoVigente(
                    @Header("Authorization") String token
            );

        @GET("api/contratos/inmueble/{id}")
        Call<Contrato> obtenerContrato(
                @Header("Authorization") String token,
                @Path("id") int idInmueble
        );
        @GET("api/pagos/contrato/{id}")
        Call <List<Pago>> obtenerPagos(
                @Header("Authorization") String token,
                @Path("id") int idContrato
        );

    }
}
