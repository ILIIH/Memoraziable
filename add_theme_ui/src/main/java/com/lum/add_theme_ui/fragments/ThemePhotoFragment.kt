package com.lum.add_theme_ui.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.lum.core.R
import com.lum.add_theme_ui.databinding.FragmentThemePhotoBinding
import com.lum.add_theme_ui.viewModels.ThemeAddViewModel
import com.lum.core.data.PhotoManager
import com.lum.core.ui.MediaFragment
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ThemePhotoFragment : MediaFragment() {

    private val viewModule: ThemeAddViewModel by sharedViewModel()
    private val photoManage: PhotoManager by inject()

    private lateinit var view : FragmentThemePhotoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = FragmentThemePhotoBinding.inflate(inflater,container, false )
        view.continueBtn.setOnClickListener { viewModule.addTheme() }

        viewModule._validation.filterNotNull().onEach {
            showError(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModule._photo.onEach {
            view.themePhoto.setImageDrawable(null)
            if(it?.isRecycled != true){
                view.themePhoto.setImageBitmap(it)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        view.themePhoto.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.choose_an_option)).apply {
                putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
            }
            launcher.launch(chooserIntent)
        }
        return view.root
    }

    override fun saveGalleryImageData(uri: Uri) {
        val bitmap =photoManage.imageDecode(uri)
        viewModule.setPhoto(photoManage.getResizedBitmap(bitmap,1000))
        view.overlayPhoto.alpha = 0.8f
        viewModule.setPhotoURI(uri.toString())
    }
    override fun saveCameraImageData(bitmap: Bitmap){
        viewModule.setPhoto(photoManage.getResizedBitmap(bitmap, 1000))
        view.overlayPhoto.alpha = 0.8f
        val photoUri =  photoManage.saveImageToGallery(bitmap)
        if(photoUri != null) {
            viewModule.setPhotoURI(photoUri.toString())
        }
    }



}
