package com.example.jsonholderapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.jsonholderapp.models.Post
import com.example.jsonholderapp.repository.PostDaoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostViewModel(private val repository: PostDaoRepository): ViewModel() {

    private val disposable = CompositeDisposable()

    val liveData by lazy {
        MutableLiveData<PostData>()
    }

    fun getPost() {
        disposable.add(
            repository.getDaoPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { liveData.value = PostData.ShowLoading }
                .doFinally { liveData.value = PostData.HideLoading }
                .subscribe({ list ->
                    liveData.value = PostData.PostResult(list as ArrayList<Post>)
                }, {
                    liveData.value = PostData.Error(it.message)
                })
        )
    }

    fun getLocalPost() {
        disposable.add(
            repository.getLocalDaoPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { liveData.value = PostData.ShowLoading }
                .doFinally { liveData.value = PostData.HideLoading }
                .subscribe({ list ->
                    liveData.value = PostData.PostLocalResult(list as ArrayList<Post>)
                }, {
                    liveData.value = PostData.Error(it.message)
                })

        )
    }

    fun getPagedLocalPost(): LiveData<PagedList<Post>> {
        var personsLiveData: LiveData<PagedList<Post>>
        val factory: DataSource.Factory<Int, Post> = repository.getPagedDaoPosts()
        Log.d("repository_paged",factory.toString() )
        val pagedListBuilder: LivePagedListBuilder<Int, Post> = LivePagedListBuilder<Int, Post>(factory,
            50)
        personsLiveData = pagedListBuilder.build()
        return personsLiveData
    }


    sealed class PostData {
        data class PostResult(val posts: ArrayList<Post>) : PostData()
        data class PostLocalResult(val postsLocal: ArrayList<Post>) : PostData()
        data class Error(val message: String?): PostData()
        object ShowLoading: PostData()
        object HideLoading: PostData()

    }

}
