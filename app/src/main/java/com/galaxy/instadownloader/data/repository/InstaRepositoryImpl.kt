package com.data.repository


import com.galaxy.instadownloader.domain.repository.InstaRepository
import com.majidalfuttaim.terminal.data.datasource.InstaDataSource
import com.majidalfuttaim.terminal.data.response.BaseResponse
import io.reactivex.Observable
import javax.inject.Inject


class InstaRepositoryImpl @Inject constructor(
    private val terminalApi: InstaDataSource
) : InstaRepository {


    override fun getInstaPostInfo(url: String): Observable<String> {
        return terminalApi.getInstaPost(url).toObservable()
    }


}

