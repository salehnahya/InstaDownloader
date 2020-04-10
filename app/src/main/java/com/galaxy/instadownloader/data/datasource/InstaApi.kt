package com.data

import com.majidalfuttaim.terminal.data.datasource.InstaDataSource
import com.majidalfuttaim.terminal.data.response.*
import io.reactivex.Single
import javax.inject.Inject

class InstaApi @Inject constructor(private val instaEndpoint: InstaEndpoint) : InstaDataSource {


    override fun getInstaPost(url: String): Single<String> {
        return instaEndpoint.getInstaPost(url)
    }


}
