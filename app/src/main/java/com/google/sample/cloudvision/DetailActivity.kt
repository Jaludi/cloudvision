package com.google.sample.cloudvision

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.Target
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
import java.io.IOException
import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity(), RecycleViewAdapter.ItemClickListener {

    private var mImageDetails: TextView? = null
    private var mMainImage: ImageView? = null

    private var searchString: String? = null

    private var urlList: ArrayList<String>? = null

    protected lateinit  var adapter: RecycleViewAdapter
    protected lateinit  var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mImageDetails = findViewById(R.id.image_details) as TextView
        mMainImage = findViewById(R.id.main_image) as ImageView

        urlList = ArrayList<String>()
        recyclerView = findViewById(R.id.recyclerTWO) as RecyclerView
        val numberOfColumns = 2
        recyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)
        adapter = RecycleViewAdapter(urlList as ArrayList<String>, this)
        adapter.setClickListener(this)
        recyclerView.adapter = adapter
        searchString = ""
        Glide

                .with(this)
                .load(intent.getStringExtra("Url"))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        Log.e("TAG", "Error loading image", e)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {

                        mImageDetails!!.text = "image loaded"
                        val bitmap = (resource as BitmapDrawable).bitmap
                        try {
                            callCloudVision(bitmap)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }

                        return false
                    }
                })
                .into(mMainImage!!)


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

                        urlList!!.add(item.link.toString())

                    }

                    adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BaseImage>, t: Throwable) {
                t.printStackTrace()
            }
        })
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

    override fun onItemClick(view: View, position: Int) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position)
        val intent = Intent(this@DetailActivity, DetailActivity::class.java)
        intent.putExtra("Url", urlList!![position])
        startActivity(intent)
    }

    companion object {
        private val TAG = DetailActivity::class.java.simpleName
        private val CLOUD_VISION_API_KEY = "AIzaSyBRMLNpHLUJY4U940tvouxvf2moqcks0jY"
        private val ANDROID_CERT_HEADER = "X-Android-Cert"
        private val ANDROID_PACKAGE_HEADER = "X-Android-Package"

        private val K = "AIzaSyAV0biFXUPJzPKB9aIzoAE0PZtC1RXySFI"
        private val ID = "012156516503846940546:d2q-7llk4t8"
        private val RETROFIT_URL = "https://www.googleapis.com/"
        private val SEARCHTYPE = "image"
    }
}
