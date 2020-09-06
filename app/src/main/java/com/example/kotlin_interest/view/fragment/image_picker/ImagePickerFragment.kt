package com.example.kotlin_interest.view.fragment.image_picker

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import android.content.ContentResolver
import com.example.kotlin_interest.R
import com.example.kotlin_interest.databinding.FragmentImagePickerBinding
import com.example.kotlin_interest.model.User
import com.example.kotlin_interest.view.activity.LoginActivity
import com.example.kotlin_interest.view.activity.MainActivity
import com.example.kotlin_interest.view.fragment.description.DescriptionFragment
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_image_picker.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject


class ImagePickerFragment : DaggerFragment() {

    @Inject
    lateinit var modelFactory: ViewModelProvider.Factory
    private lateinit var imagePickerViewModel: ImagePickerViewModel

    private lateinit var binding: FragmentImagePickerBinding

    var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentImagePickerBinding.inflate(inflater, container, false)
        imagePickerViewModel =
            ViewModelProvider(this, modelFactory)[ImagePickerViewModel::class.java]

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        binding.apply {
            nextButton.setOnClickListener {
                imageUri?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        imagePickerViewModel.saveImage(requireActivity().contentResolver.openInputStream(it)!!)
                    }
                }
                when (activity) {
                    is LoginActivity -> startActivity(Intent(activity, MainActivity::class.java))
                    is MainActivity -> {

                    }
                }
            }
            imageButton.setOnClickListener { setImage() }
        }
    }

    private fun setImage() {
        val permission =
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        } else {
            startImagePickerActivity()
        }
    }

    private fun startImagePickerActivity() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            startImagePickerActivity()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            imageUri = intent?.data
            imageUri?.let {
                Glide.with(this)
                    .load(it)
                    .circleCrop()
                    .into(binding.imageButton)
            }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        val PERMISSIONS_STORAGE: Array<String> = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        const val REQUEST_IMAGE = 1
        const val REQUEST_EXTERNAL_STORAGE = 1

        fun newInstance() = ImagePickerFragment()
    }

}
