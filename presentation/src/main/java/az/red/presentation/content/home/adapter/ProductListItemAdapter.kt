package az.red.presentation.content.home.adapter

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Paint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import az.red.domain.model.product.Product
import az.red.presentation.R
import az.red.presentation.base.BaseDiffUtil
import az.red.presentation.databinding.ProductCardBinding
import coil.load
import kotlin.math.roundToInt


class ProductListItemAdapter(private val addToWishList: (id: String) -> Unit) :
    RecyclerView.Adapter<ProductViewHolder>() {
    private var data = mutableListOf<Product>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.getInstance(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem, addToWishList, false)
    }

    fun setData(newData: List<Product>) {
        val diffUtil = BaseDiffUtil(data, newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        data = newData.toMutableList()
        diffUtilResult.dispatchUpdatesTo(this)
    }
}

class ProductViewHolder(private val binding: ProductCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind(product: Product, addToWishList: (id: String) -> Unit, lineView: Boolean) {
        binding.tvName.text = product.name
        if (product.description.isNullOrBlank()) binding.tvDescription.visibility = View.GONE
        binding.tvDescription.text = product.description.toString()
        binding.tvCurrentPrice.text = "US $${product.currentPrice}"

        if (product.imageUrls.firstOrNull().isNullOrEmpty()) {
            binding.ivImage.setImageResource(R.drawable.ic_launcher_foreground)
        } else {
            binding.ivImage.load(product.imageUrls.first())
        }

        if (product.currentPrice != (product.previousPrice ?: product.currentPrice)) {
            binding.tvPreviousPrice.visibility = View.VISIBLE
            binding.tvPreviousPrice.text = "US $${product.previousPrice}"
            binding.tvPreviousPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }

        if (product.currentPrice != (product.previousPrice ?: product.currentPrice)) {
            binding.cvSale.visibility = View.VISIBLE
            binding.tvSale.text =
                "${(product.currentPrice - product.previousPrice!!).roundToInt()}%"
        }

        binding.ivHeart.setOnClickListener {
            addToWishList(product.id)
        }

        if (lineView) {
            binding.ivHeart.visibility = View.GONE

            binding.cvSale.layoutParams.height = 16.toPx.toInt()
            binding.cvSale.layoutParams.width = 37.toPx.toInt()
            binding.tvSale.textSize = 8f

            binding.cvImage.layoutParams.height = 80.toPx.toInt()
            binding.cvImage.layoutParams.width = 80.toPx.toInt()

            (binding.root as LinearLayout).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
            }
        } else {

            binding.ivHeart.visibility = View.VISIBLE

            binding.cvSale.layoutParams.height = 20.toPx.toInt()
            binding.cvSale.layoutParams.width = 52.toPx.toInt()
            binding.tvSale.textSize = 12f

            binding.cvImage.layoutParams.height = 160.toPx.toInt()
            binding.cvImage.layoutParams.width = 160.toPx.toInt()

            (binding.root as LinearLayout).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams.width = 168.toPx.toInt()
            }
        }
    }

    companion object {
        fun getInstance(parent: ViewGroup): ProductViewHolder {
            val binding = ProductCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ProductViewHolder(binding)
        }
    }
}

val Int.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )