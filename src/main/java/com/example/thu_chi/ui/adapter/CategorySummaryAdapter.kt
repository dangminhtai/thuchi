package com.example.thu_chi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thu_chi.databinding.ItemCategorySummaryBinding
import java.text.NumberFormat
import java.util.Locale

data class CategorySummary(val name: String, val amount: Long, val percentage: Float)

class CategorySummaryAdapter(private val summaries: List<CategorySummary>) : RecyclerView.Adapter<CategorySummaryAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCategorySummaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(summary: CategorySummary) {
            val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
            binding.tvCategoryName.text = summary.name
            binding.tvAmount.text = formatter.format(summary.amount)
            binding.tvPercentage.text = String.format("%.1f %%", summary.percentage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategorySummaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(summaries[position])
    }

    override fun getItemCount() = summaries.size
}
