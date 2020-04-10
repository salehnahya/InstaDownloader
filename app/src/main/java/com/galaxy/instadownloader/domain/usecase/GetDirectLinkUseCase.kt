package com.domain.usecase


import com.galaxy.instadownloader.domain.model.InstagramMedia
import com.galaxy.instadownloader.domain.repository.InstaRepository
import com.galaxy.instadownloader.util.InstagramMediaFactory
import io.reactivex.Observable
import org.jsoup.Jsoup
import javax.inject.Inject

class GetDirectLinkUseCase @Inject constructor(private val instaRepository: InstaRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(var insta: InstagramMedia?=null) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }


    fun execute(url: String): Observable<Result> {
        return instaRepository.getInstaPostInfo(url)
            .map {
                var parse = Jsoup.parse(it)
                var insta = InstagramMediaFactory.createInstagramMedia(parse)
                if(insta!=null)
                Result.Success(insta) as Result
                else {
                    var throwable = Throwable("Error This is private Post")
                    Result.Failure(throwable)
                }
            }
            .onErrorReturn {
                Result.Failure(it) }
            .startWith(Result.Loading)
    }
}
