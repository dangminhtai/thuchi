package com.example.thu_chi.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thu_chi.data.AppDatabase
import com.example.thu_chi.data.Transaction
import com.example.thu_chi.databinding.FragmentNhapThuChiBinding
import com.example.thu_chi.repository.TransactionRepository
import com.example.thu_chi.ui.adapter.Category
import com.example.thu_chi.ui.adapter.CategoryAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FragmentNhapThuChi : Fragment() {

    private var _binding: FragmentNhapThuChiBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        TransactionViewModel.Factory(TransactionRepository(database.transactionDao()))
    }

    private var selectedDate = Calendar.getInstance()
    private var selectedCategory = "Khác"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNhapThuChiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateDateDisplay()
        binding.et_date.setOnClickListener { showDatePicker() }

        setupCategoryList()

        binding.btn_save.setOnClickListener { saveTransaction() }
    }

    private fun setupCategoryList() {
        val categories = listOf(
            Category("Ăn uống", "🍽️"),
            Category("Phí giao lưu", "🍻"),
            Category("Y tế", "🏥"),
            Category("Mỹ phẩm", "💄"),
            Category("Tiền điện", "⚡"),
            Category("Tiền nhà", "🏠"),
            Category("Chi tiêu hàng ngày", "🛒"),
            Category("Giáo dục", "🎓"),
            Category("Quần áo", "👕")
        )

        binding.rv_categories.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rv_categories.adapter = CategoryAdapter(categories) { category ->
            selectedCategory = category.name
        }
    }

    private fun showDatePicker() {
        DatePickerDialog(requireContext(), { _, year, month, day ->
            selectedDate.set(year, month, day)
            updateDateDisplay()
        }, selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateDateDisplay() {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("vi", "VN"))
        binding.et_date.setText(sdf.format(selectedDate.time))
    }

    private fun saveTransaction() {
        val amountStr = binding.et_amount.text.toString()
        val note = binding.et_note.text.toString()
        val isIncome = binding.toggle_group.checkedButtonId == R.id.btn_income

        if (amountStr.isEmpty()) {
            Toast.makeText(requireContext(), "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toLong()
        val transaction = Transaction(
            amount = amount,
            note = note,
            date = selectedDate.timeInMillis,
            isIncome = isIncome,
            category = selectedCategory
        )

        viewModel.insert(transaction)
        Toast.makeText(requireContext(), "Đã lưu thành công", Toast.LENGTH_SHORT).show()
        
        // Reset fields
        binding.et_amount.text?.clear()
        binding.et_note.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
