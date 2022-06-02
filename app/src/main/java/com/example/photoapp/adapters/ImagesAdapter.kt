package com.example.photoapp.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photoapp.model.AppImage
import com.example.photoapp.databinding.ItemAdapterImageBinding




interface ImageAdapterSelectInterface {
    fun onImagAdapterClick(appImage: AppImage)
}

class ImagesAdapter(val onImageAdapterClick: ImageAdapterSelectInterface)  :
    RecyclerView.Adapter<ImagesAdapter.ImageAppViewHolder>() {
    private var appImages: List<AppImage> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAppViewHolder {
        val itemView: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAdapterImageBinding.inflate(itemView)
        return ImageAppViewHolder(binding)
    }

    fun update(appImagesNew:List<AppImage>){
        this.appImages = appImagesNew
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ImageAppViewHolder, position: Int) {
        val appImage = appImages[position]
        holder.bind(appImage)
    }

    override fun getItemCount(): Int {
        return appImages.size
    }

    inner class ImageAppViewHolder(val binding: ItemAdapterImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AppImage) {
            binding.appImage = item
            binding.callback = onImageAdapterClick
        }
    }


}
