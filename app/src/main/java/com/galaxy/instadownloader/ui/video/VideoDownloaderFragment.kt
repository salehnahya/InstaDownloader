package com.galaxy.instadownloader.ui.video

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.galaxy.instadownloader.R
import com.galaxy.instadownloader.databinding.VideoDownloaderFragmentBinding
import com.galaxy.instadownloader.ui.BaseFragment
import com.galaxy.instadownloader.ui.image.DownloaderViewModel
import com.galaxy.instadownloader.ui.image.PostDetailState
import javax.inject.Inject

class VideoDownloaderFragment : BaseFragment() {

    companion object {
        fun newInstance() = VideoDownloaderFragment()
    }

    @Inject
    lateinit var viewModel: DownloaderViewModel
    private lateinit var binding: VideoDownloaderFragmentBinding
    private lateinit var video: VideoDownloaderFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.video_downloader_fragment, container, false)
        binding = VideoDownloaderFragmentBinding.bind(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        video = VideoDownloaderFragmentArgs.fromBundle(requireArguments())
        var args = video.video
        binding.tvTitle.setText(args.title)
        Glide.with(requireContext()).load(args.posterUrl).into(binding.imgThumbnail)
        binding.btnDownload.setOnClickListener {
            doWithPermission { viewModel.downloadFromUrl(args.secureUrl) }
        }

        viewModel.liveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.getContentIfNotHandled()?.let { it ->
                when (it) {
                    PostDetailState.DownloadStarted -> notifyDownloadStarted()
                    PostDetailState.PermissionError -> notifyDownloadError()
                }
            }
        })
    }


}
