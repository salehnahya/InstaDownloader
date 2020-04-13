package com.galaxy.instadownloader.ui.initial

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.domain.usecase.GetDirectLinkUseCase
import com.galaxy.instadownloader.R
import com.galaxy.instadownloader.databinding.FragmentStartBinding
import com.galaxy.instadownloader.domain.model.InstagramImage
import com.galaxy.instadownloader.domain.model.InstagramVideo
import com.galaxy.instadownloader.util.ValidationUtils
import com.galaxy.instadownloader.util.readFromClipboard
import com.google.android.gms.ads.*
import com.google.android.gms.ads.formats.MediaView
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import dagger.android.support.DaggerFragment
import java.util.*
import javax.inject.Inject
const val ADMOB_AD_UNIT_ID = "ca-app-pub-4587894583273616/4511071712"
var currentNativeAd: UnifiedNativeAd? = null
class StartFragment : DaggerFragment() {
    private val TAG = StartFragment::class.java.simpleName


    @Inject
    lateinit var viewModel: StartViewModel

    private var progressDialog: ProgressDialog? = null
    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_start, container, false)
        binding = FragmentStartBinding.bind(view)
        return view
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnDownload.setOnClickListener {
            var url = binding.etUrl.text.toString()
            if (ValidationUtils.isValidInstagramUrl(url)) {
                viewModel.getFeedInfo(url)
            } else {
                binding.etUrl.error = "Please Enter Valid Insta Url"
            }
        }
        binding.btnPaste.setOnClickListener {
            var readFromClipboard = readFromClipboard().orEmpty()
            if (ValidationUtils.isValidInstagramUrl(readFromClipboard)) {
                viewModel.getFeedInfo(readFromClipboard)

            } else {
               showMessage("Please Enter Valid Insta Url")
            }
        }

        viewModel.liveData.observe(viewLifecycleOwner, Observer {

            var result = it.peekContent()
            when (result) {

                is GetDirectLinkUseCase.Result.Loading -> {
                    startLoading()
                }
                is GetDirectLinkUseCase.Result.Success -> {
                    stopLoading()
                    var data = result.insta?.data
                        if (data is InstagramImage) {
                            findNavController().navigate(
                                StartFragmentDirections.actionStartFragmentToImageDownloaderFragment(
                                    data
                                )
                            )
                        }
                        if (data is InstagramVideo) {
                            findNavController().navigate(
                                StartFragmentDirections.actionStartFragmentToVideoDownloaderFragment(
                                    data
                                )
                            )
                        }

                }
                is GetDirectLinkUseCase.Result.Failure -> {
                    stopLoading()
                    var throwable = result.throwable
                    showMessage(throwable.message)
                }

            }
        })

        loadAdd()
    }

    private fun loadAdd() {
refreshAd()
        val adRequest = AdRequest.Builder().build();
        binding.adView.loadAd(adRequest)
        binding.adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()

                Log.e(TAG, "onAdLoaded")
            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                Log.e(TAG, "onAdFailedToLoad $p0")
            }
        }
    }

    private fun showMessage(message: String?) {
        Toast.makeText(requireContext(), message.orEmpty(), Toast.LENGTH_LONG).show()
    }

    fun stopLoading() {
        if (progressDialog != null && progressDialog?.isShowing!!) {
            progressDialog!!.cancel()
            progressDialog!!.dismiss()
            progressDialog?.setCancelable(false)
            progressDialog = null
        }
    }

    private fun startLoading() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog!!.setMessage("Loading...!")
        progressDialog!!.show()
    }

    /**
     * Populates a [UnifiedNativeAdView] object with data from a given
     * [UnifiedNativeAd].
     *
     * @param nativeAd the object containing the ad's assets
     * @param adView the view to be populated
     */
    private fun populateUnifiedNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
        // You must call destroy on old ads when you are done with them,
        // otherwise you will have a memory leak.
        currentNativeAd?.destroy()
        currentNativeAd = nativeAd
        // Set the media view.
        adView.mediaView = adView.findViewById(R.id.ad_media)

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        adView.mediaView.setMediaContent(nativeAd.mediaContent)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView.visibility = View.INVISIBLE
        } else {
            adView.bodyView.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }

        if (nativeAd.callToAction == null) {
            adView.callToActionView.visibility = View.INVISIBLE
        } else {
            adView.callToActionView.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }

        if (nativeAd.icon == null) {
            adView.iconView.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon.drawable
            )
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.INVISIBLE
        } else {
            adView.priceView.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.INVISIBLE
        } else {
            adView.storeView.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
            adView.starRatingView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.VISIBLE

            adView.setNativeAd(nativeAd)
            val vc = nativeAd.videoController
            // Updates the UI to say whether or not this ad has a video asset.
            if (vc.hasVideoContent()) {
                vc.videoLifecycleCallbacks = object : VideoController.VideoLifecycleCallbacks() {
                    override fun onVideoEnd() {
                        super.onVideoEnd()
                    }
                }
            }
        }
    }

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     *
     */
    private fun refreshAd() {

        val builder = AdLoader.Builder(requireContext(), ADMOB_AD_UNIT_ID)

        builder.forUnifiedNativeAd { unifiedNativeAd ->
            // OnUnifiedNativeAdLoadedListener implementation.
            val adView = layoutInflater
                .inflate(R.layout.ad_unified, null) as UnifiedNativeAdView
            populateUnifiedNativeAdView(unifiedNativeAd, adView)
            binding.adFrame.removeAllViews()
            binding.adFrame.addView(adView)
        }

        val videoOptions = VideoOptions.Builder()
            .setStartMuted(true)
            .build()

        val adOptions = NativeAdOptions.Builder()
            .setVideoOptions(videoOptions)
            .build()

        builder.withNativeAdOptions(adOptions)

        val adLoader = builder.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(errorCode: Int) {
                Log.e(TAG,"Failed to load native ad: " + errorCode)
            }
        }).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    override fun onDestroy() {
        currentNativeAd?.destroy()
        super.onDestroy()
    }

}
