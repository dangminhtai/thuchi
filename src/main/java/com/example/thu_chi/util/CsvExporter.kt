package com.example.thu_chi.util

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.example.thu_chi.data.Transaction
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

object CsvExporter {
    fun exportTransactions(context: Context, transactions: List<Transaction>) {
        val fileName = "bao_cao_thu_chi.csv"
        val file = File(context.cacheDir, fileName)
        
        file.writeText("Ngày,Loại,Danh mục,Số tiền,Ghi chú\n")
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        
        transactions.forEach {
            val type = if (it.isIncome) "Thu" else "Chi"
            file.appendText("${sdf.format(it.date)},$type,${it.category},${it.amount},${it.note}\n")
        }
        
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Xuất báo cáo"))
    }
}
