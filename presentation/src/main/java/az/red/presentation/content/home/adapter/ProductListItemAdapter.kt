package az.red.presentation.content.home.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import az.red.domain.model.product.Product
import az.red.presentation.R
import az.red.presentation.databinding.ProductCardBinding
import coil.load

class ProductListItemAdapter : RecyclerView.Adapter<ProductViewHolder>() {
    private var data = mutableListOf<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
    }

    fun setData(newData: List<Product>) {
        data = newData.toMutableList()
        notifyDataSetChanged()
    }
}

class ProductViewHolder(private val binding: ProductCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(product: Product) {
        binding.tvName.text = product.name
        binding.tvDescription.text = product.description.toString()
        binding.tvCurrentPrice.text = "US $${product.currentPrice}"
        binding.tvPreviousPrice.text = "US $${product.previousPrice}"
        binding.tvPreviousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        if (product.imageUrls.firstOrNull().isNullOrEmpty()) {
            binding.ivImage.setImageResource(R.drawable.ic_launcher_foreground)
        } else {
            binding.ivImage.load(product.imageUrls.first())
        }
    }
}