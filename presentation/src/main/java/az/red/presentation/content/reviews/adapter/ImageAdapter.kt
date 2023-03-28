package az.red.presentation.content.reviews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import az.red.presentation.databinding.ItemImageBinding
import coil.load
import coil.transform.RoundedCornersTransformation

class ImageAdapter(private var emptyList: List<String>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ImageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemImageBinding.inflate(layoutInflater, parent, false)
                return ImageViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder.from(parent)
    }

    override fun getItemCount(): Int = emptyList.size


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.binding.imageView.load(emptyList[position]) {
            RoundedCornersTransformation(
                topLeft = 8F,
                topRight = 8F,
                bottomLeft = 8F,
                bottomRight = 8F
            )

        }
    }
}