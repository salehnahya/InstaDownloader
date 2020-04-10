package com.majidalfuttaim.terminal.data.datasource

import io.reactivex.Single

interface InstaDataSource   {
    fun getInstaPost(url: String): Single<String>


}