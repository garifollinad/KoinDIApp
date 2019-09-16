package com.example.jsonholderapp

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.jsonholderapp.di.appModules
import com.example.jsonholderapp.worker.PostWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModules)
            initWorker()
        }
    }

    fun initWorker(){

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequest.Builder(PostWorker::class.java)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueue(workRequest)

    }
}