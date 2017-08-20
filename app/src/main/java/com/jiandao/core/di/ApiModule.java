package com.jiandao.core.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiandao.core.di.qualifier.ApplicationScope;
import com.jiandao.data.api.ApiDefaultConfig;
import com.jiandao.data.api.service.TopService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by grubber on 2017/1/6.
 */
@Module
public class ApiModule {
    @Provides
    @ApplicationScope
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
//        builder.registerTypeAdapter(T.class, new TDeserializer());
        return builder.create();
    }

    @Provides
    @ApplicationScope
    Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Converter.Factory converterFactory) {
        return new Retrofit.Builder()
                .baseUrl(ApiDefaultConfig.BASE_ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    TopService provideTopService(Retrofit retrofit) {
        return retrofit.create(TopService.class);
    }
}
