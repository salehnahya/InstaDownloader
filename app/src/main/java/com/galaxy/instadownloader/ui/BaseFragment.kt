package com.galaxy.instadownloader.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.galaxy.instadownloader.R
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerDialogFragment

open class BaseFragment : DaggerDialogFragment() {
     open  val PERMISSION_REQUEST = 123

    fun doWithPermission(actionRequiringPermission: () -> Unit) {

        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST)
            } else {
                actionRequiringPermission()
            }
        }
    }
    fun notifyDownloadStarted() {
        Toast.makeText(context, getString(R.string.notify_download_started), Toast.LENGTH_SHORT).show()
    }

    fun notifyDownloadError() {
        val snackbar = Snackbar.make(requireView(), R.string.notify_download_needs_permission, Snackbar.LENGTH_INDEFINITE)
        snackbar.setAction(R.string.settings) {

            activity?.let {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", it.packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }
        snackbar.show()
    }
    override fun onStart() {
        super.onStart()
        dialog?.let { it.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Toast.makeText(
                    context,
                    "Permission granted please try to download now",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                notifyDownloadError()
            }
        }
    }

}