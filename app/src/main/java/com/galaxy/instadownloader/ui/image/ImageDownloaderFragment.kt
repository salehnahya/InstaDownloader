package com.galaxy.instadownloader.ui.image

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.PagerSnapHelper
import com.galaxy.instadownloader.R
import com.galaxy.instadownloader.databinding.FragmentImageDownloaderBinding
import com.galaxy.instadownloader.ui.BaseFragment
import com.galaxy.instadownloader.ui.image.adapter.ScreenSlidePagerAdapter
import com.galaxy.instadownloader.ui.listener.CommunicationListener
import javax.inject.Inject


class ImageDownloaderFragment : BaseFragment(), CommunicationListener {

    @Inject
    lateinit var viewModel: DownloaderViewModel
    private lateinit var binding: FragmentImageDownloaderBinding
    private lateinit var args: ImageDownloaderFragmentArgs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var inflate = inflater.inflate(R.layout.fragment_image_downloader, container, false)
        binding = FragmentImageDownloaderBinding.bind(inflate)
        return inflate
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args = ImageDownloaderFragmentArgs.fromBundle(requireArguments())
        var viewPagerAdapter = ScreenSlidePagerAdapter(args.args, this)
        binding.pager.adapter = viewPagerAdapter
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(binding.pager)
        binding.indicator.attachToRecyclerView(binding.pager, pagerSnapHelper)


        viewModel.liveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.getContentIfNotHandled()?.let { it ->
                when (it) {
                    PostDetailState.DownloadStarted -> notifyDownloadStarted()
                    PostDetailState.PermissionError -> notifyDownloadError()
                }
            }
        })
    }

    override fun onDownloadButtonClicked(url: String) {
        doWithPermission { viewModel.downloadFromUrl(url) }
    }

}
