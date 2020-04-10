package com.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface InstaEndpoint {


    @GET
    fun getInstaPost(@Url url:String): Single<String>


}
