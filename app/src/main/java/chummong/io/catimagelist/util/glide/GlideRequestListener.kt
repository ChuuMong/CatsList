package chummong.io.catimagelist.util.glide

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * Created by LeeJongHun on 2016-03-08.
 */

class GlideRequestListener<String, GlideDrawable>(val progress: ProgressBar? = null) : RequestListener<String, GlideDrawable> {

    override fun onResourceReady(resource: GlideDrawable,
                                 model: String,
                                 target: Target<GlideDrawable>,
                                 isFromMemoryCache: Boolean,
                                 isFirstResource: Boolean): Boolean {

        Log.d("GlideRequestListener", "Get Url : ${model}, ")
        progress?.visibility = View.GONE
        return false
    }

    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
        Log.d("GlideRequestListener", "Error in Glide listener")
        e.printStackTrace()
        return false
    }
}