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
        TransactionViewModel.Factory(TransactionRepository(database.transactionDao(), database.budgetDao(), database.categoryDao()))
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

        binding.toggleChart.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnPie -> {
                        binding.pieChart.visibility = android.view.View.VISIBLE
                        binding.barChart.visibility = android.view.View.GONE
                    }
                    R.id.btnBar -> {
                        binding.pieChart.visibility = android.view.View.GONE
                        binding.barChart.visibility = android.view.View.VISIBLE
                    }
                }
            }
        }

        binding.btnExport.setOnClickListener {
            val transactions = viewModel.transactionsInMonth.value ?: emptyList()
            if (transactions.isNotEmpty()) {
                com.example.thu_chi.util.CsvExporter.exportTransactions(requireContext(), transactions)
            } else {
                android.widget.Toast.makeText(requireContext(), "Không có dữ liệu để xuất", android.widget.Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupChart() {
        binding.pieChart.apply {
            setDrawEntryLabels(false)
            setHoleColor(Color.TRANSPARENT)
            setCenterTextSize(14f)
            setCenterTextColor(Color.GRAY)
            animateY(1400, com.github.mikephil.charting.animation.Easing.EaseInOutQuad)
            legend.isEnabled = false
            description.isEnabled = false
            holeRadius = 60f
            setHoleColor(android.graphics.Color.TRANSPARENT)
            legend.isEnabled = false
        }

        binding.barChart.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            setDrawBarShadow(false)
            legend.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
            axisRight.isEnabled = false
        }
    }

    private fun observeViewModel() {
        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))

        viewModel.totalIncome.observe(viewLifecycleOwner) { income ->
            binding.tvTotalIncome.text = formatter.format(income ?: 0L)
        }

        viewModel.totalExpense.observe(viewLifecycleOwner) { expense ->
            val total = expense ?: 0L
            binding.tvTotalExpense.text = formatter.format(total)
            binding.pieChart.centerText = "Tổng chi\n" + formatter.format(total)
        }

        viewModel.transactionsInMonth.observe(viewLifecycleOwner) { transactions ->
            updateBaoCao(transactions, viewModel.budgets.value ?: emptyList())
        }

        viewModel.budgets.observe(viewLifecycleOwner) { budgets ->
            updateBaoCao(viewModel.transactionsInMonth.value ?: emptyList(), budgets)
        }
    }

    private fun updateBaoCao(transactions: List<com.example.thu_chi.data.Transaction>, budgets: List<com.example.thu_chi.data.Budget>) {
        val expenseTransactions = transactions.filter { !it.isIncome }
        val totalExpense = expenseTransactions.sumOf { it.amount }
        val categorySum = expenseTransactions.groupBy { it.category }
            .mapValues { entry -> entry.value.sumOf { it.amount } }

        updatePieChart(categorySum)
        updateBarChart(categorySum)
        updateSummaryList(categorySum, totalExpense, budgets)
    }

    private fun updateSummaryList(categorySum: Map<String, Long>, totalExpense: Long, budgets: List<com.example.thu_chi.data.Budget>) {
        val summaries = categorySum.map { (name, amount) ->
            val percentage = if (totalExpense > 0) (amount.toFloat() / totalExpense) * 100 else 0f
            val budget = budgets.find { it.category == name }
            CategorySummary(name, amount, percentage, budget?.limitAmount ?: 0L)
        }.sortedByDescending { it.amount }

        binding.rvCategorySummary.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategorySummary.adapter = CategorySummaryAdapter(summaries)
    }

    private fun updatePieChart(categorySum: Map<String, Long>) {
        val entries = categorySum.map { (name, amount) -> PieEntry(amount.toFloat(), name) }
        val dataSet = PieDataSet(entries, "").apply {
            colors = com.github.mikephil.charting.utils.ColorTemplate.VORDHOFF_COLORS.toList()
            setDrawValues(false)
        }
        binding.pieChart.data = PieData(dataSet)
        binding.pieChart.invalidate()
    }

    private fun updateBarChart(categorySum: Map<String, Long>) {
        val entries = categorySum.entries.mapIndexed { index, entry ->
            com.github.mikephil.charting.data.BarEntry(index.toFloat(), entry.value.toFloat())
        }
        val dataSet = com.github.mikephil.charting.data.BarDataSet(entries, "Chi tiêu").apply {
            color = android.graphics.Color.parseColor("#FF8C00")
            setDrawValues(true)
        }
        
        binding.barChart.xAxis.valueFormatter = com.github.mikephil.charting.formatter.IndexAxisValueFormatter(categorySum.keys)
        binding.barChart.data = com.github.mikephil.charting.data.BarData(dataSet)
        binding.barChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
