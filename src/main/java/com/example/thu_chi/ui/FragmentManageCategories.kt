package com.example.thu_chi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thu_chi.data.AppDatabase
import com.example.thu_chi.data.Category
import com.example.thu_chi.databinding.FragmentManageCategoriesBinding
import com.example.thu_chi.repository.TransactionRepository
import com.example.thu_chi.ui.adapter.CategoryManageAdapter

class FragmentManageCategories : Fragment() {

    private var _binding: FragmentManageCategoriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransactionViewModel by viewModels {
        val database = AppDatabase.getDatabase(requireContext())
        TransactionViewModel.Factory(TransactionRepository(database.transactionDao(), database.budgetDao(), database.categoryDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManageCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoryManageAdapter(emptyList()) { category ->
            showDeleteConfirm(category)
        }
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = adapter

        viewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            adapter.updateList(categories)
        }

        binding.btnAddCategory.setOnClickListener { showAddCategoryDialog() }
    }

    private fun showAddCategoryDialog() {
        val view = layoutInflater.inflate(com.example.thu_chi.R.layout.dialog_add_category, null)
        val etName = view.findViewById<EditText>(com.example.thu_chi.R.id.etName)
        val etIcon = view.findViewById<EditText>(com.example.thu_chi.R.id.etIcon)

        AlertDialog.Builder(requireContext())
            .setTitle("Thêm danh mục mới")
            .setView(view)
            .setPositiveButton("Thêm") { _, _ ->
                val name = etName.text.toString()
                val icon = etIcon.text.toString()
                if (name.isNotEmpty()) {
                    viewModel.insertCategory(Category(name = name, icon = icon.ifEmpty { "📁" }))
                }
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showDeleteConfirm(category: Category) {
        AlertDialog.Builder(requireContext())
            .setTitle("Xóa danh mục")
            .setMessage("Bạn có chắc chắn muốn xóa danh mục '${category.name}'?")
            .setPositiveButton("Xóa") { _, _ ->
                viewModel.deleteCategory(category)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
