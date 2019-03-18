package com.bailm.vivychallenge.di.module

import android.util.Log
import com.bailm.vivychallenge.BuildConfig
import com.bailm.vivychallenge.data.DoctorsApiService
import com.bailm.vivychallenge.data.DoctorsRepository
import com.bailm.vivychallenge.data.IDoctorsRepository
import com.bailm.vivychallenge.data.api.model.AuthorizationService
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesDoctorsApiService(okHttpClient: OkHttpClient): DoctorsApiService {
        val retrofiltBuilder = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
        retrofiltBuilder.build()
        return retrofiltBuilder.build().create(DoctorsApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesAuthorizationApiService(okHttpClient: OkHttpClient): AuthorizationService {
        val retrofiltBuilder = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.AUTH_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
        retrofiltBuilder.build()
        return retrofiltBuilder.build().create(AuthorizationService::class.java)
    }

    @Singleton
    @Provides
    fun providesHttpClient(): OkHttpClient {
        var clientBuilder = OkHttpClient.Builder()
            .addNetworkInterceptor(provideLogInterceptor())
        return clientBuilder.build()

    }

    private fun provideLogInterceptor(): HttpLoggingInterceptor {
        var interceptor =  HttpLoggingInterceptor { message ->
                        Logger.d("NETWORK_LOG",message)
                }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor

    }



    @Singleton
    @Provides
    fun providesRepository(
        doctorsApiService: DoctorsApiService,
        authService: AuthorizationService
    ): IDoctorsRepository {
        return DoctorsRepository(doctorsApiService, authService)
    }


}