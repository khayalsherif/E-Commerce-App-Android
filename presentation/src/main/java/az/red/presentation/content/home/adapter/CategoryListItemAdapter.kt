package az.red.presentation.content.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import az.red.domain.model.category.Category
import az.red.presentation.R
import az.red.presentation.base.BaseDiffUtil
import az.red.presentation.databinding.CategoryCardBinding
import coil.load

class CategoryListItemAdapter(private val selectCategory: (categoryName: String) -> Unit) :
    RecyclerView.Adapter<CategoryViewHolder>() {
    private var data = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem) {
            selectCategory(it)
            notifyDataSetChanged()
        }
    }

    fun setData(newData: List<Category>) {
        val diffUtil = BaseDiffUtil(data, newData)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtil)
        data = newData.toMutableList()
        diffUtilResult.dispatchUpdatesTo(this)
    }
}

class CategoryViewHolder(private val binding: CategoryCardBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(currentItem: Category, selectCategory: (categoryName: String) -> Unit) {
        println("re_bind" + currentItem.name + " " + currentItem.isSelected.toString())
        binding.tvCategoryName.text = currentItem.name
        if (currentItem.imgUrl.isNullOrEmpty()) {
            binding.ivCategoryImage.setImageResource(R.drawable.ic_launcher_foreground)
        } else {
            binding.ivCategoryImage.load(currentItem.imgUrl!!)
        }
        binding.root.setOnClickListener { selectCategory(currentItem.name) }
        if (currentItem.isSelected) {
            binding.tvCategoryName.setTextColor(Color.RED)
        } else {
            binding.tvCategoryName.setTextColor(R.attr.text_color)
        }
    }

}