package com.fearaujo.mememaker.dashboard

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import com.fearaujo.mememaker.BANNER_ID
import com.fearaujo.mememaker.R
import com.fearaujo.mememaker.arch.BaseFragment
import com.fearaujo.mememaker.hideKeyboard
import com.fearaujo.mememaker.web.WebSelectorActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.markodevcic.peko.Peko
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.container_ads.*
import kotlinx.android.synthetic.main.container_editor.*
import kotlinx.android.synthetic.main.container_palette_colors.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainFragment : BaseFragment<MainFragmentContract.View, MainFragmentContract.Presenter>(), MainFragmentContract.View {

    override fun initPresenter(): MainFragmentContract.Presenter {
        return MainFragmentPresenter(this)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        memesButton.setOnClickListener {
            startActivityForResult(Intent(activity, WebSelectorActivity::class.java), REQUEST_PHOTO_WEB)
        }

        albumButton.setOnClickListener {
            openGallery(getString(R.string.msg_select_picture))
        }

        cameraButton.setOnClickListener {
            requestCameraPermission()
        }

        btRed.setOnClickListener {
            context?.let { context ->
                editorContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
            }
        }

        btBlue.setOnClickListener {
            context?.let { context ->
                editorContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
            }
        }

        btGreen.setOnClickListener {
            context?.let { context ->
                editorContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.green))
            }
        }

        btYellow.setOnClickListener {
            context?.let { context ->
                editorContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
            }
        }

        btOrange.setOnClickListener {
            context?.let { context ->
                editorContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.orange))
            }
        }

        btBlack.setOnClickListener {
            context?.let { context ->
                editorContainer.setBackgroundColor(ContextCompat.getColor(context, android.R.color.black))
            }
        }

        btWhite.setOnClickListener {
            context?.let { context ->
                editorContainer.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
            }
        }
    }

    override fun createAdView() {
        val adViewConfig = AdView(context)
        adViewConfig.adSize = AdSize.SMART_BANNER
        adViewConfig.adUnitId = BANNER_ID

        adView.addView(adViewConfig)
        val adRequest = AdRequest.Builder().build()
        adViewConfig.loadAd(adRequest)
    }


    override fun loadImageOnEditor(url: String?) {
        url?.let { value ->
            val picasso = Picasso.Builder(ivMeme.context).build()
            picasso.load(value).into(ivMeme)
        }

    }

    override fun loadImageOnEditor(image: Bitmap) {
        ivMeme.setImageBitmap(image)
    }

    override fun openGallery(title: String) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT//
        startActivityForResult(Intent.createChooser(intent, title), REQUEST_PHOTO_GALLERY)
    }

    override fun openCamera() {
        val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_PHOTO_CAMERA)
    }

    fun takeScreenshot() {
        checkFields()
        tvWatermark.visibility = View.VISIBLE
        activity?.hideKeyboard()

        try {
            // image naming and path  to include sd card  appending name you choose for file
            val mPath = Environment.getExternalStorageDirectory().toString() + "/temp_picture.jpg"

            // create bitmap screen capture
            val v1 = editorContainer
            val bitmap = v1.drawToBitmap()

            val imageFile = File(mPath)
            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()

            shareImage(bitmap)
        } catch (e: Throwable) {
            // Several error may come out with file handling or DOM
            e.printStackTrace()
        }

    }

    private fun checkFields() {
        val topText = tvTopText.text.toString().trim()
        val bottomText = tvBottomText.text.toString().trim()

        if (topText.isEmpty()) {
            tvTopText.visibility = View.GONE
        }

        if (bottomText.isEmpty()) {
            tvBottomText.visibility = View.GONE
        }
    }

    private fun fieldsVisible() {
        tvTopText.visibility = View.VISIBLE
        tvBottomText.visibility = View.VISIBLE
    }


    private fun shareImage(mBitmap: Bitmap) {
        val share = Intent(Intent.ACTION_SEND)
        share.type = "image/jpeg"
        val bytes = ByteArrayOutputStream()
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val f = File(Environment.getExternalStorageDirectory().toString() + File.separator + "temporary_file.jpg")
        try {
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (context != null) {
            val uri = FileProvider.getUriForFile(
                    context!!, context!!.applicationContext.packageName + ".provider", f)
            share.setDataAndType(uri, "image/jpeg")
            share.putExtra(Intent.EXTRA_STREAM, uri)
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(share, "Share Image"))
        }
        fieldsVisible()
        tvWatermark.visibility = View.INVISIBLE
    }

    private fun requestCameraPermission() {
        val activityRoot = activity as Activity
        launch(UI) {
            val permissionResultDeferred = Peko.requestPermissionsAsync(
                    activityRoot, Manifest.permission.CAMERA
            )
            val (grantedPermissions) = permissionResultDeferred.await()

            if (Manifest.permission.CAMERA in grantedPermissions) {
                openCamera()
            } else {
                Toast.makeText(activity, "Permission required.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_PHOTO_WEB -> loadImageOnEditor(data.getStringExtra(REQUEST_PHOTO_URL))
                REQUEST_PHOTO_GALLERY -> loadImageOnEditor(data.data.toString())
                REQUEST_PHOTO_CAMERA -> loadImageOnEditor(data.extras.get("data") as Bitmap)
            }

        }
    }


}
