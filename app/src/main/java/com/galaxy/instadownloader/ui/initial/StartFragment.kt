package com.galaxy.instadownloader.ui.initial

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.domain.usecase.GetDirectLinkUseCase
import com.galaxy.instadownloader.R
import com.galaxy.instadownloader.databinding.FragmentStartBinding
import com.galaxy.instadownloader.domain.model.InstagramImage
import com.galaxy.instadownloader.domain.model.InstagramVideo
import com.galaxy.instadownloader.util.ValidationUtils
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class StartFragment : DaggerFragment() {


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

    fun startLoading() {
        progressDialog = ProgressDialog(requireContext())
        progressDialog!!.setMessage("Loading...!")
        progressDialog!!.show()
    }

}
