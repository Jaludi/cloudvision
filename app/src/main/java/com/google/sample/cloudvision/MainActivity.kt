/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sample.cloudvision

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.FileProvider
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.vision.v1.Vision
import com.google.api.services.vision.v1.VisionRequest
import com.google.api.services.vision.v1.VisionRequestInitializer
import com.google.api.services.vision.v1.model.AnnotateImageRequest
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse
import com.google.api.services.vision.v1.model.EntityAnnotation
import com.google.api.services.vision.v1.model.Feature
import com.google.api.services.vision.v1.model.Image
import com.google.sample.cloudvision.searchpojos.BaseImage
import com.google.sample.cloudvision.searchpojos.Item
import com.google.sample.cloudvision.searchpojos.RetrofitService

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mImageDetails: TextView? = null
    private var mMainImage: ImageView? = null
    private var searchString: String? = null
    private var urlList: ArrayList<String>? = null
    private var listBTN: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        urlList = ArrayList<String>()
        searchString = ""
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder
                    .setMessage(R.string.dialog_select_prompt)
                    .setPositiveButton(R.string.dialog_select_gallery) { dialog, which -> startGalleryChooser() }
                    .setNegativeButton(R.string.dialog_select_camera) { dialog, which -> startCamera() }
            builder.create().show()
        }

        mImageDetails = findViewById(R.id.image_details) as TextView
        mMainImage = findViewById(R.id.main_image) as ImageView
        listBTN = findViewById(R.id.testBTN) as Button
        listBTN!!.setOnClickListener(this)
    }

    fun startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_IMAGE_REQUEST)
        }
    }

    fun startCamera() {
        if (PermissionUtils.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photoUri = FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", cameraFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST)
        }
    }

    val cameraFile: File
        get() {
            val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File(dir, FILE_NAME)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            uploadImage(data.data)
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val photoUri = FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", cameraFile)
            uploadImage(photoUri)
        }
    }

    private fun doRetrofitNetworkCall() {
        val retrofit = Retrofit.Builder()
                .baseUrl(RETROFIT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(RetrofitService::class.java)
        val call = service.getStuff(K, ID, SEARCHTYPE, searchString!!)
        call.enqueue(object : Callback<BaseImage> {
            override fun onResponse(call: Call<BaseImage>, response: Response<BaseImage>) {
                if (response.isSuccessful) {
                    val myBaseImage = response.body()
                    for (item in myBaseImage.items!!) {
                        Log.d(TAG, "onResponse: Link is " + item.link)

                        urlList!!.add(item.link!!)

                    }

                    Log.d(TAG, "onResponse: arrayCount = " + urlList!!.size)
                    Log.d(TAG, "onResponse: list item 3 " + urlList!![3])
                    listBTN!!.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<BaseImage>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSIONS_REQUEST -> if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                startCamera()
            }
            GALLERY_PERMISSIONS_REQUEST -> if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                startGalleryChooser()
            }
        }
    }

    fun uploadImage(uri: Uri?) {
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                val bitmap = scaleBitmapDown(
                        MediaStore.Images.Media.getBitmap(contentResolver, uri),
                        1200)

                callCloudVision(bitmap)
                mMainImage!!.setImageBitmap(bitmap)

            } catch (e: IOException) {
                Log.d(TAG, "Image picking failed because " + e.message)
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show()
            }

        } else {
            Log.d(TAG, "Image picker gave us a null image.")
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show()
        }
    }

    @Throws(IOException::class)
    private fun callCloudVision(bitmap: Bitmap) {
        // Switch text to loading
        mImageDetails!!.setText(R.string.loading_message)

        // Do the real work in an async task, because we need to use the network anyway
        object : AsyncTask<Any, Void, String>() {
            override fun doInBackground(vararg params: Any): String {
                try {
                    val httpTransport = AndroidHttp.newCompatibleTransport()
                    val jsonFactory = GsonFactory.getDefaultInstance()

                    val requestInitializer = object : VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                        @Throws(IOException::class)
                        override fun initializeVisionRequest(visionRequest: VisionRequest<*>?) {
                            super.initializeVisionRequest(visionRequest)

                            val packageName = packageName
                            visionRequest!!.requestHeaders.set(ANDROID_PACKAGE_HEADER, packageName)

                            val sig = PackageManagerUtils.getSignature(packageManager, packageName)

                            visionRequest.requestHeaders.set(ANDROID_CERT_HEADER, sig)
                        }
                    }

                    val builder = Vision.Builder(httpTransport, jsonFactory, null)
                    builder.setVisionRequestInitializer(requestInitializer)

                    val vision = builder.build()

                    val batchAnnotateImagesRequest = BatchAnnotateImagesRequest()
                    batchAnnotateImagesRequest.requests = object : ArrayList<AnnotateImageRequest>() {
                        init {
                            val annotateImageRequest = AnnotateImageRequest()

                            // Add the image
                            val base64EncodedImage = Image()
                            // Convert the bitmap to a JPEG
                            // Just in case it's a format that Android understands but Cloud Vision
                            val byteArrayOutputStream = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream)
                            val imageBytes = byteArrayOutputStream.toByteArray()

                            // Base64 encode the JPEG
                            base64EncodedImage.encodeContent(imageBytes)
                            annotateImageRequest.image = base64EncodedImage

                            // add the features we want
                            annotateImageRequest.features = object : ArrayList<Feature>() {
                                init {
                                    val labelDetection = Feature()
                                    labelDetection.type = "LABEL_DETECTION"
                                    labelDetection.maxResults = 10
                                    add(labelDetection)
                                }
                            }

                            // Add the list of one thing to the request
                            add(annotateImageRequest)
                        }
                    }

                    val annotateRequest = vision.images().annotate(batchAnnotateImagesRequest)
                    // Due to a bug: requests to Vision API containing large images fail when GZipped.
                    annotateRequest.disableGZipContent = true
                    Log.d(TAG, "created Cloud Vision request object, sending request")

                    val response = annotateRequest.execute()
                    return convertResponseToString(response)

                } catch (e: GoogleJsonResponseException) {
                    Log.d(TAG, "failed to make API request because " + e.content)
                } catch (e: IOException) {
                    Log.d(TAG, "failed to make API request because of other IOException " + e.message)
                }

                return "Cloud Vision API request failed. Check logs for details."
            }

            override fun onPostExecute(result: String) {
                mImageDetails!!.text = result
                Log.d(TAG, "onPostExecute: " + searchString!!)
                doRetrofitNetworkCall()

            }
        }.execute()
    }


    fun scaleBitmapDown(bitmap: Bitmap, maxDimension: Int): Bitmap {

        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        var resizedWidth = maxDimension
        var resizedHeight = maxDimension

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension
            resizedWidth = (resizedHeight * originalWidth.toFloat() / originalHeight.toFloat()).toInt()
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension
            resizedHeight = (resizedWidth * originalHeight.toFloat() / originalWidth.toFloat()).toInt()
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension
            resizedWidth = maxDimension
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false)
    }

    private fun convertResponseToString(response: BatchAnnotateImagesResponse): String {
        var message = "Found:\n\n"

        val labels = response.responses[0].labelAnnotations
        if (labels != null) {
            var counter = 0
            for (label in labels) {
                message += label.description
                message += "\n"
                if (counter < 3) {
                    if (counter > 0)
                        searchString += "+" + label.description
                    else {
                        searchString += label.description
                    }
                }
                counter++
            }
        } else {
            message += "nothing"
        }

        return message
    }

    private fun callActivitySimilar() {
        val intent = Intent(this@MainActivity, SimilarActivity::class.java)
        intent.putStringArrayListExtra("UrlList", urlList)
        startActivity(intent)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.testBTN -> callActivitySimilar()
            else -> return
        }
    }

    companion object {
        private val CLOUD_VISION_API_KEY = "AIzaSyBRMLNpHLUJY4U940tvouxvf2moqcks0jY"
        val FILE_NAME = "temp.jpg"
        private val ANDROID_CERT_HEADER = "X-Android-Cert"
        private val ANDROID_PACKAGE_HEADER = "X-Android-Package"
        private val K = "AIzaSyAV0biFXUPJzPKB9aIzoAE0PZtC1RXySFI"
        private val ID = "012156516503846940546:d2q-7llk4t8"
        private val RETROFIT_URL = "https://www.googleapis.com/"
        private val SEARCHTYPE = "image"

        private val TAG = MainActivity::class.java.simpleName
        private val GALLERY_PERMISSIONS_REQUEST = 0
        private val GALLERY_IMAGE_REQUEST = 1
        val CAMERA_PERMISSIONS_REQUEST = 2
        val CAMERA_IMAGE_REQUEST = 3
    }
}