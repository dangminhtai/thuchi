package com.example.thu_chi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thu_chi.data.Category
import com.example.thu_chi.databinding.ItemCategoryManageBinding

class CategoryManageAdapter(
    private var categories: List<Category>,
    private val onDeleteClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryManageAdapter.ViewHolder>() {

    fun updateList(newList: List<Category>) {
        categories = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemCategoryManageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.tvName.text = category.name
            binding.tvIcon.text = category.icon
            binding.btnDelete.setOnClickListener { onDeleteClick(category) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryManageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size
}
