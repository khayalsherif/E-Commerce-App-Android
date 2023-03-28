package az.red.presentation.content.reviews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import az.red.domain.model.review.Review
import az.red.presentation.base.BaseDiffUtil
import az.red.presentation.databinding.ItemReviewBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    private var emptyList = emptyList<Review>()
    private val viewPool = RecyclerView.RecycledViewPool()

    class ReviewViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ReviewViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemReviewBinding.inflate(layoutInflater, parent, false)
                return ReviewViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder.from(parent)

    }

    override fun getItemCount(): Int = emptyList.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.binding.textContent.text = emptyList[position].content!!
        holder.binding.texTitle.text =
            "${emptyList[position].customer.firstName} ${emptyList[position].customer.lastName}"
        holder.binding.textTime.text = emptyList[position].customer.date.substring(0, 10)


        val childLayoutManager = LinearLayoutManager(
            holder.binding.root.context,
            RecyclerView.HORIZONTAL,
            false
        )

        holder.binding.rcView.apply {
            layoutManager = childLayoutManager
            adapter = ImageAdapter(emptyList[position].product.imageUrls)
            setRecycledViewPool(viewPool)
        }
    }

    fun setData(newList: List<Review>) {
        val diffUtil = BaseDiffUtil(emptyList, newList)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        emptyList = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}