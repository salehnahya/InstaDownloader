package com.galaxy.instadownloader.ui.initial

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.domain.usecase.GetDirectLinkUseCase
import com.galaxy.instadownloader.util.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class StartViewModel @Inject() constructor(private val getDirectLinkUseCase: GetDirectLinkUseCase) :
    ViewModel() {
    private val disposables = CompositeDisposable()
    private val mutableLiveData: MutableLiveData<Event<GetDirectLinkUseCase.Result>> =
        MutableLiveData()
    val liveData: LiveData<Event<GetDirectLinkUseCase.Result>>
        get() = mutableLiveData


    fun getFeedInfo(url: String) {
        getDirectLinkUseCase.execute(url).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                mutableLiveData.value = Event(it)
            }

    }


    fun clear() {
        disposables.clear()
    }
}


