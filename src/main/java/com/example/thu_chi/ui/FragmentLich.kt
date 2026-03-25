package com.example.thu_chi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thu_chi.data.AppDatabase
import com.example.thu_chi.databinding.FragmentLichBinding
import com.example.thu_chi.repository.TransactionRepository
import com.example.thu_chi.ui.adapter.TransactionAdapter
import java.util.Calendar

class FragmentLich : Fragment() {

    private var _binding: FragmentLichBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        TransactionViewModel.Factory(TransactionRepository(database.transactionDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLichBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var adapter: TransactionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TransactionAdapter()
        binding.rv_daily_transactions.layoutManager = LinearLayoutManager(requireContext())
        binding.rv_daily_transactions.adapter = adapter
        
        binding.calendar_view.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            // Filter logic can be added here or in ViewModel
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.transactionsInMonth.observe(viewLifecycleOwner) { transactions ->
            adapter.submitList(transactions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
