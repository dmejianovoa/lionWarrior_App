package com.damianmejia.lionwarrior.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.damianmejia.lionwarrior.BuildConfig;

// Patrón Singleton: una única instancia de Retrofit para toda la app
public class RetrofitClient {

    // URL base de la API desplegada en Render
    private static final String BASE_URL = "https://lion-warrior-api.onrender.com/";

    private static  ApiService apiService;

    // Constructor privado: nadie puede crear instancias con "new"
    private RetrofitClient() { }

        public static ApiService getApiService() {
            if(apiService == null) {
                // Solo en desarrollo se muestran las peticiones en el Logcat (en producción expondría contraseñas)
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(BuildConfig.DEBUG
                    ? HttpLoggingInterceptor.Level.BODY
                        : HttpLoggingInterceptor.Level.NONE);

            //Timeouts amplios: Render(plan gratuito) tarda en "despertar"
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(60,TimeUnit.SECONDS)
                        .readTimeout(60,TimeUnit.SECONDS)
                        .addInterceptor(logging)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

            apiService = retrofit.create(ApiService.class);
        }
            return apiService;
    }
}
