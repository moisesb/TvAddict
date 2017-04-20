package com.moisesborges.tvaddict;

import android.content.Context;
import android.support.annotation.NonNull;

import com.moisesborges.tvaddict.net.TvMazeApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Moisés on 11/04/2017.
 */
@Module
@Singleton
public class NetModule {

    private final Context mContext;

    public NetModule(@NonNull Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    public OkHttpClient providesOkHttpClient() {
        Interceptor interceptor = buildInterceptor();
        Cache cache = buildCache();

        return new OkHttpClient().newBuilder()
                .cache(cache)
                .addInterceptor(interceptor)
                .build();
    }

    private Cache buildCache() {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(mContext.getCacheDir() , cacheSize);
    }

    @NonNull
    private Interceptor buildInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.tag("OkHttp").d(message));
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return loggingInterceptor;
    }

    @Singleton
    @Provides
    public Retrofit providesRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.tvmaze.com")
                .build();
    }

    @Singleton
    @Provides
    public TvMazeApi providesTvMazeApi(Retrofit retrofit) {
        return retrofit.create(TvMazeApi.class);
    }
}
