package com.tanzible.restapilogindeno;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiClient {

    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30000, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS).addInterceptor(logging).addInterceptor(logging);

            retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl(AppConstant.BASEURL.URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static MyApiEndpointInterface getUserService() {
        return getClient().create(MyApiEndpointInterface.class);
    }



    public static <S> S createService(
            Class<S> UserServices) {
        return retrofit.create(UserServices);
    }


}
