package com.example.thu_chi.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.thu_chi.data.Transaction
import com.example.thu_chi.databinding.ItemTransactionBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("vi", "VN"))

            binding.tvAmount.text = if (transaction.isIncome) "+" else "-" + formatter.format(transaction.amount)
            binding.tvAmount.setTextColor(if (transaction.isIncome) Color.GREEN else Color.RED)
            binding.tvNote.text = transaction.note
            binding.tvCategory.text = transaction.category
            binding.tvDate.text = sdf.format(transaction.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) = oldItem == newItem
    }
}
