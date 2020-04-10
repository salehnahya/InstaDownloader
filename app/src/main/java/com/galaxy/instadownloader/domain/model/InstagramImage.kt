package com.galaxy.instadownloader.domain.model

import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*


@Parcelize
class InstagramImage : InstagramMedia(), Serializable {
     var links: List<String>? = ArrayList<String>()




    fun setLinks(html: String) {
         val regex = """."display_url":"([^"]*)""".toRegex()
        val regexResult = regex.findAll(html)
        val filtered = regexResult.map {
            val value = it.value
            value.substring(16, value.length)
        }
        val links = filtered.distinct().toList()

        this.links = links
    }
}