package com.example.smartmed.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.smartmed.R
import com.example.smartmed.databinding.FragmentIndicatorBinding
import kotlin.random.Random

class IndicatorFragment : Fragment(R.layout.fragment_indicator) {
    private var _binding: FragmentIndicatorBinding? = null
    private val binding get() = _binding!!

    // Константы для нормальных значений
    companion object {
        // Пульс
        private const val PULSE_MIN = 60
        private const val PULSE_MAX = 100

        // Давление
        private const val PRESSURE_SYSTOLIC_MIN = 110
        private const val PRESSURE_SYSTOLIC_MAX = 130
        private const val PRESSURE_DIASTOLIC_MIN = 70
        private const val PRESSURE_DIASTOLIC_MAX = 90

        // Дыхание
        private const val BREATHING_MIN = 12
        private const val BREATHING_MAX = 20

        // Сатурация
        private const val SATURATION_MIN = 95
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIndicatorBinding.bind(view)

        updateIndicators()

        binding.updateButton.setOnClickListener {
            updateIndicators()
        }
    }

    private fun updateIndicators() {
        val pulse = Random.nextInt(50, 110)
        val pressureSystolic = Random.nextInt(100, 140)
        val pressureDiastolic = Random.nextInt(60, 100)
        val breathing = Random.nextInt(10, 25)
        val saturation = Random.nextInt(90, 100)

        with(binding) {
            pulseValue.text = "$pulse уд/мин"
            pulseLayout.setBackgroundColor(getColorForValue(
                pulse.toFloat(),
                PULSE_MIN.toFloat(),
                PULSE_MAX.toFloat()
            ))

            pressureValue.text = "$pressureSystolic/$pressureDiastolic"
            pressureLayout.setBackgroundColor(getColorForPressure(
                pressureSystolic,
                pressureDiastolic
            ))

            breathingValue.text = "$breathing/мин"
            breathingLayout.setBackgroundColor(getColorForValue(
                breathing.toFloat(),
                BREATHING_MIN.toFloat(),
                BREATHING_MAX.toFloat()
            ))

            saturationValue.text = "$saturation%"
            saturationLayout.setBackgroundColor(getColorForValue(
                saturation.toFloat(),
                SATURATION_MIN.toFloat(),
                100f
            ))
        }
    }

    private fun getColorForValue(value: Float, min: Float, max: Float): Int {
        return when {
            value < min || value > max -> ContextCompat.getColor(requireContext(), R.color.danger_background)
            else -> ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    private fun getColorForPressure(systolic: Int, diastolic: Int): Int {
        return when {
            systolic < PRESSURE_SYSTOLIC_MIN || systolic > PRESSURE_SYSTOLIC_MAX ||
                    diastolic < PRESSURE_DIASTOLIC_MIN || diastolic > PRESSURE_DIASTOLIC_MAX ->
                ContextCompat.getColor(requireContext(), R.color.danger_background)
            else -> ContextCompat.getColor(requireContext(), R.color.white)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}