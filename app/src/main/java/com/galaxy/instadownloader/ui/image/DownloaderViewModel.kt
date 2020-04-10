package com.galaxy.instadownloader.ui.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.galaxy.instadownloader.domain.usecase.download.usecase.DownloadUseCase
import com.galaxy.instadownloader.util.Event
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DownloaderViewModel @Inject constructor(private val downloadUseCase: DownloadUseCase) :
    ViewModel() {
    private val mutableLiveData: MutableLiveData<Event<PostDetailState>> = MutableLiveData()
    val liveData: LiveData<Event<PostDetailState>>
        get() = mutableLiveData

    fun downloadFromUrl(url: String) {
        val detailState = downloadUseCase.downloadPost(url)
            .fold({ PostDetailState.PermissionError }, { PostDetailState.DownloadStarted })
        mutableLiveData.value = Event(detailState)

    }



}

sealed class PostDetailState {
    object DownloadStarted : PostDetailState()
    object PermissionError : PostDetailState()
}