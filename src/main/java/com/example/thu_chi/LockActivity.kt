package com.example.thu_chi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.thu_chi.databinding.ActivityLockBinding
import com.example.thu_chi.util.SecurityUtils

class LockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLockBinding
    private var inputPin = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLockBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupKeypad()
        checkBiometric()
    }

    private fun setupKeypad() {
        // Find all buttons in GridLayout and setup listener
        for (i in 0 until binding.keypad.childCount) {
            val child = binding.keypad.getChildAt(i)
            if (child is Button) {
                child.setOnClickListener { onNumberClick(child.text.toString()) }
            }
        }

        binding.btnDelete.setOnClickListener {
            if (inputPin.isNotEmpty()) {
                inputPin = inputPin.substring(0, inputPin.length - 1)
                updateDots()
            }
        }
        
        binding.btnBiometric.setOnClickListener { checkBiometric() }
    }

    private fun onNumberClick(number: String) {
        if (inputPin.length < 4) {
            inputPin += number
            updateDots()
            
            if (inputPin.length == 4) {
                verifyPin()
            }
        }
    }

    private fun updateDots() {
        val dots = listOf(binding.dot1, binding.dot2, binding.dot3, binding.dot4)
        for (i in dots.indices) {
            if (i < inputPin.length) {
                dots[i].setBackgroundResource(R.drawable.dot_filled)
            } else {
                dots[i].setBackgroundResource(R.drawable.dot_empty)
            }
        }
    }

    private fun verifyPin() {
        if (inputPin == SecurityUtils.getPin(this)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Mã PIN không đúng!", Toast.LENGTH_SHORT).show()
            inputPin = ""
            updateDots()
        }
    }

    private fun checkBiometric() {
        val biometricManager = BiometricManager.from(this)
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS) {
            showBiometricPrompt()
        }
    }

    private fun showBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Xác thực vân tay")
            .setSubtitle("Sử dụng vân tay để mở khóa")
            .setNegativeButtonText("Sử dụng mã PIN")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
