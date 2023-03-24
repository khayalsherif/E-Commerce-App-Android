package az.red.presentation.content.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import az.red.domain.model.category.Category
import az.red.presentation.R
import az.red.presentation.databinding.CategoryCardBinding
import coil.load

class CategoryListItemAdapter : RecyclerView.Adapter<CategoryViewHolder>() {
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
        holder.bind(currentItem)
    }

    fun setData(newData: List<Category>) {
        data = newData as MutableList<Category>
        notifyDataSetChanged()
    }

}

class CategoryViewHolder(val binding: CategoryCardBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(currentItem: Category) {
        binding.tvCategoryName.text = currentItem.name
        if (currentItem.imgUrl.isNullOrEmpty()) {
            binding.ivCategoryImage.setImageResource(R.drawable.ic_launcher_foreground)
        } else {
            binding.ivCategoryImage.load(currentItem.imgUrl!!)
        }
    }

}