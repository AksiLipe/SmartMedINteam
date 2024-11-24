package com.example.smartmed.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smartmed.R
import com.example.smartmed.adapter.ViewPagerAdapter
import com.example.smartmed.databinding.ActivityMainBinding
import com.example.smartmed.fragments.QuestionFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupTabs()
        logout()
        sos()
        setupQuestionButton()
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter
    }

    private fun setupTabs() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Запись к врачу"
                1 -> "Показатели"
                2 -> "Обо мне"
                else -> throw IllegalArgumentException("Invalid position $position")
            }
        }.attach()
    }

    private fun logout() {
        binding.leftButton.setOnClickListener {
            getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("is_logged_in", false)
                .apply()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun sos() {
        binding.sosButton.setOnClickListener {
            showCallDialog(PHONE_NUMBER)
        }
    }

    private fun showCallDialog(phoneNumber: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Экстренный вызов")
            .setMessage("Вы уверены, что хотите позвонить?")
            .setPositiveButton("Да") { _, _ ->
                makePhoneCall(phoneNumber)
            }
            .setNegativeButton("Нет", null)
            .show()
    }

    private fun makePhoneCall(phoneNumber: String) {
        val permission = "android.permission.CALL_PHONE"

        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:$phoneNumber")
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(
                    this,
                    "Ошибка при совершении звонка: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                CALL_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CALL_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    makePhoneCall(PHONE_NUMBER)
                } else {
                    Toast.makeText(
                        this,
                        "Необходимо разрешение на совершение звонков",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupQuestionButton() {
        binding.rightButton.setOnClickListener {
            binding.viewPager.visibility = View.GONE
            binding.sosButton.visibility = View.GONE
            binding.appBarLayout.visibility = View.GONE

            binding.fragmentContainer.visibility = View.VISIBLE

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, QuestionFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            binding.viewPager.visibility = View.VISIBLE
            binding.sosButton.visibility = View.VISIBLE
            binding.appBarLayout.visibility = View.VISIBLE

            binding.fragmentContainer.visibility = View.GONE

            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val CALL_PERMISSION_REQUEST_CODE = 123
        private const val PHONE_NUMBER = "+79274172989"
    }
}