package com.galaxy.instadownloader.ui.image.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.galaxy.instadownloader.R
import com.galaxy.instadownloader.databinding.ViewImagesBinding
import com.galaxy.instadownloader.domain.model.InstagramImage
import com.galaxy.instadownloader.ui.listener.CommunicationListener


class ScreenSlidePagerAdapter(
    val image: InstagramImage,
  val  listener: CommunicationListener
) :
    RecyclerView.Adapter<ImageViewHolder>() {
    private var list: List<String> = image.links!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.view_images, parent, false)
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = lp
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        var bind = holder.bind
        bind.tvTitle.text = image.title
        var link = list[position].replace("\\u0026", "&")
        Glide.with(holder.itemView.context).load(link).into(bind.imgThumbnail)
        bind.btnDownload.setOnClickListener { listener.onDownloadButtonClicked(link) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class ImageViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
    var bind: ViewImagesBinding = ViewImagesBinding.bind(view)
}