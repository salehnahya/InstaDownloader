package com.galaxy.instadownloader.domain.repository


import io.reactivex.Observable

interface InstaRepository {

    fun getInstaPostInfo(url:String): Observable<String>

}
