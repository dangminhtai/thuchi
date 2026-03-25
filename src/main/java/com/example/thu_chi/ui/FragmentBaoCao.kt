package com.example.thu_chi.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.thu_chi.data.AppDatabase
import com.example.thu_chi.databinding.FragmentBaoCaoBinding
import com.example.thu_chi.repository.TransactionRepository
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.text.NumberFormat
import java.util.Locale

class FragmentBaoCao : Fragment() {

    private var _binding: FragmentBaoCaoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        TransactionViewModel.Factory(TransactionRepository(database.transactionDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaoCaoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupChart()
        observeViewModel()
    }

    private fun setupChart() {
        binding.pieChart.apply {
            description.isEnabled = false
            isDrawHoleEnabled = true
            setHoleColor(Color.TRANSPARENT)
            setCenterTextSize(16f)
            legend.isEnabled = false
        }
    }

    private fun observeViewModel() {
        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))

        viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
            binding.tv_total_income.text = formatter.format(income ?: 0L)
        }

        viewModel.totalExpense.observe(viewLifecycleOwner) { expense ->
            binding.tv_total_expense.text = formatter.format(expense ?: 0L)
        }

        viewModel.transactionsInMonth.observe(viewLifecycleOwner) { transactions ->
            val expenseTransactions = transactions.filter { !it.isIncome }
            val categorySum = expenseTransactions.groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount } }

            updateChart(categorySum)
        }
    }

    private fun updateChart(categorySum: Map<String, Long>) {
        val entries = categorySum.map { PieEntry(it.value.toFloat(), it.key) }
        val dataSet = PieDataSet(entries, "Chi tiêu theo danh mục")
        
        dataSet.colors = listOf(
            Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, 
            Color.MAGENTA, Color.LTGRAY, Color.DKGRAY
        )
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f

        binding.pieChart.data = PieData(dataSet)
        binding.pieChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
