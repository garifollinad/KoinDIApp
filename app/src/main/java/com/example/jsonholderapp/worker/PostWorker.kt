package com.example.jsonholderapp.worker

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.example.jsonholderapp.repository.PostDaoRepository
import com.example.jsonholderapp.repository.PostDaoRepositoryImpl
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject

class PostWorker(
    context: Context,
    workerParameters: WorkerParameters
) :  RxWorker(context, workerParameters), KoinComponent {

    private val postDaoRepository: PostDaoRepository by inject()

    override fun createWork(): Single<Result> {
        return postDaoRepository.getDaoPosts()
            .map { result ->
                postDaoRepository.insertPosts(result)
                Result.success()
            }
            .doFinally {
                Log.d("yel", "addresses worker endWork()")
            }
    }

}