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
import com.example.thu_chi.ui.adapter.CategorySummary
import com.example.thu_chi.ui.adapter.CategorySummaryAdapter
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import androidx.recyclerview.widget.LinearLayoutManager
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
            setUsePercentValues(true)
            description.isEnabled = false
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            setHoleColor(Color.WHITE)
            setTransparentCircleColor(Color.WHITE)
            setTransparentCircleAlpha(110)
            holeRadius = 58f
            transparentCircleRadius = 61f
            setDrawCenterText(true)
            rotationAngle = 0f
            isRotationEnabled = true
            isHighlightPerTapEnabled = true
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(12f)
            legend.isEnabled = false
        }
    }

    private fun observeViewModel() {
        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))

        viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
            binding.tvTotalIncome.text = formatter.format(income ?: 0L)
        }

        viewModel.totalExpense.observe(viewLifecycleOwner) { expense ->
            binding.tvTotalExpense.text = formatter.format(expense ?: 0L)
        }

        viewModel.transactionsInMonth.observe(viewLifecycleOwner) { transactions ->
            val expenseTransactions = transactions.filter { !it.isIncome }
            val totalExpense = expenseTransactions.sumOf { it.amount }
            val categorySum = expenseTransactions.groupBy { it.category }
                .mapValues { entry -> entry.value.sumOf { it.amount } }

            updateChart(categorySum)
            updateSummaryList(categorySum, totalExpense)
        }
    }

    private fun updateSummaryList(categorySum: Map<String, Long>, totalExpense: Long) {
        val summaries = categorySum.map { (name, amount) ->
            val percentage = if (totalExpense > 0) (amount.toFloat() / totalExpense) * 100 else 0f
            CategorySummary(name, amount, percentage)
        }.sortedByDescending { it.amount }

        binding.rvCategorySummary.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategorySummary.adapter = CategorySummaryAdapter(summaries)
    }

    private fun updateChart(categorySum: Map<String, Long>) {
        val entries = categorySum.map { PieEntry(it.value.toFloat(), it.key) }
        val dataSet = PieDataSet(entries, "Chi tiêu theo danh mục")
        
        val colors = mutableListOf<Int>()
        for (c in com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS) colors.add(c)
        for (c in com.github.mikephil.charting.utils.ColorTemplate.VORDIPLOM_COLORS) colors.add(c)
        dataSet.colors = colors

        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        
        val data = PieData(dataSet)
        data.setValueFormatter(com.github.mikephil.charting.formatter.PercentFormatter(binding.pieChart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)
        
        binding.pieChart.data = data
        binding.pieChart.highlightValues(null)
        binding.pieChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
