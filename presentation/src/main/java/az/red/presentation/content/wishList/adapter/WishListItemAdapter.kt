package az.red.presentation.content.wishList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import az.red.domain.model.product.Product
import az.red.presentation.R
import az.red.presentation.base.BaseDiffUtil
import az.red.presentation.databinding.ItemWishlistCardBinding
import coil.load

class WishListItemAdapter(private val removeFromWishList: (String) -> Unit,
                          private val addToCart: (String) -> Unit) :
    RecyclerView.Adapter<WishListItemViwHolder>() {

    private var data = mutableListOf<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListItemViwHolder {
        return WishListItemViwHolder.getInstance(parent)
    }

    override fun onBindViewHolder(holder: WishListItemViwHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem,removeFromWishList,addToCart)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun setData(newData: List<Product>) {
        val diffUtil = BaseDiffUtil(data, newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        data = newData.toMutableList()
        diffUtilResult.dispatchUpdatesTo(this)
    }
}

class WishListItemViwHolder(private val binding: ItemWishlistCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product, removeFromWishList: (String) -> Unit, addToCart: (String) -> Unit) {
        binding.tvName.text = product.name
        binding.tvCurrentPrice.text = "US $${product.currentPrice}"

        if (product.imageUrls.firstOrNull().isNullOrEmpty()) {
            binding.ivImage.setImageResource(R.drawable.ic_launcher_foreground)
        } else {
            binding.ivImage.load(product.imageUrls.first())
        }

        binding.ivHeart.setOnClickListener {
            binding.ivHeart.setImageResource(R.drawable.ic_heart)
            removeFromWishList(product._id)
        }
        binding.btnAddToCart.setOnClickListener {
            addToCart(product._id)
        }
    }

    companion object {
        fun getInstance(parent: ViewGroup): WishListItemViwHolder {
            val binding = ItemWishlistCardBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )

            return WishListItemViwHolder(binding)
        }
    }
}