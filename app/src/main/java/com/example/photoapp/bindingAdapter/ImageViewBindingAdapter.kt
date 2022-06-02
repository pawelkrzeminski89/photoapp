package app.pkrzeminski.weather.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.photoapp.R

class ImageViewBindingAdapter {

    companion object {

        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, url: String?) {

            url?.let {
                val imageUtl = GlideUrl(
                    url, LazyHeaders.Builder()
                        .build()
                )
                Glide
                    .with(view.context)
                    .load(imageUtl)
                    .placeholder(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(view)
            }
        }
    }
}