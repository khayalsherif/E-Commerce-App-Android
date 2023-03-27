package az.red.presentation.content.home.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import az.red.domain.model.product.Product

class ProductListItemPagingAdapter(private val addToWishList: (id: String) -> Unit) :
    PagingDataAdapter<Product, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    private var lineView = false
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let{(holder as? ProductViewHolder)?.bind(product = it, addToWishList,lineView)}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ProductViewHolder.getInstance(parent)
    }

    fun switchToLineView(){
        lineView = true
    }
    fun switchToBlockView(){
        lineView = false
    }
}