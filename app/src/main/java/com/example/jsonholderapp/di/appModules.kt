package com.example.jsonholderapp.di

import android.util.Log
import androidx.room.Room
import com.example.jsonholderapp.BuildConfig
import com.example.jsonholderapp.api.PostApi
import com.example.jsonholderapp.database.Database
import com.example.jsonholderapp.repository.PostDaoRepository
import com.example.jsonholderapp.repository.PostDaoRepositoryImpl
import com.example.jsonholderapp.ui.PostViewModel
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single { createHttpClient()}
    single { createApiService(get())}
    single { PostDaoRepositoryImpl(get(), get()) as PostDaoRepository }
    single { Room.databaseBuilder(androidContext(), Database::class.java, "database2").build()}
    single { get<Database>().postDao() }

}

val viewModelModule = module {
    viewModel { PostViewModel(get()) }
}

fun createHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("OkHttp", message) })
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        okHttpClient
            .addInterceptor(interceptor)
    }
    return okHttpClient.build()
}



fun createApiService(okHttpClient: OkHttpClient): PostApi {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.POST_API)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(PostApi::class.java)
}


val appModules = listOf(networkModule, viewModelModule)