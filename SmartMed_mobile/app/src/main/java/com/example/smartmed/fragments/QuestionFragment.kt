package com.example.smartmed.fragments

import AttachmentAdapter
import AttachmentModel
import QuestionData
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartmed.R
import com.example.smartmed.activity.MainActivity
import com.example.smartmed.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment(R.layout.fragment_question) {
    private lateinit var binding: FragmentQuestionBinding
    private val attachedFiles = mutableListOf<AttachmentModel>()
    private lateinit var attachmentAdapter: AttachmentAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentQuestionBinding.bind(view)

        setupAttachmentRecycler()
        setupButtons()
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.arrowBack.setOnClickListener {
            // Получаем доступ к MainActivity
            val mainActivity = activity as? MainActivity
            mainActivity?.let {
                // Возвращаем видимость элементов MainActivity
                it.binding.viewPager.visibility = View.VISIBLE
                it.binding.sosButton.visibility = View.VISIBLE
                it.binding.appBarLayout.visibility = View.VISIBLE

                // Скрываем контейнер фрагмента
                it.binding.fragmentContainer.visibility = View.GONE

                // Закрываем текущий фрагмент
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun setupAttachmentRecycler() {
        attachmentAdapter = AttachmentAdapter(
            attachments = attachedFiles,
            onDeleteClick = { position ->
                attachedFiles.removeAt(position)
                attachmentAdapter.notifyItemRemoved(position)
            }
        )

        binding.attachmentsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = attachmentAdapter
        }
    }

    private fun setupButtons() {
        // Кнопка прикрепления файлов
        binding.attachButton.setOnClickListener {
            openFilePicker()
        }

        // Кнопка отправки
        binding.sendButton.setOnClickListener {
            if (validateForm()) {
                sendQuestion()
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let { handleFileSelection(it) }
        }
    }

    private fun handleFileSelection(data: Intent) {
        // Обработка одного файла
        data.data?.let { uri ->
            addAttachment(uri)
        }

        // Обработка нескольких файлов
        data.clipData?.let { clipData ->
            for (i in 0 until clipData.itemCount) {
                addAttachment(clipData.getItemAt(i).uri)
            }
        }
    }

    private fun addAttachment(uri: Uri) {
        val fileName = getFileName(uri)
        val attachment = AttachmentModel(uri, fileName)
        attachedFiles.add(attachment)
        attachmentAdapter.notifyItemInserted(attachedFiles.size - 1)
    }

    private fun getFileName(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            val nameIndex = it.getColumnIndex("_display_name")
            it.moveToFirst()
            it.getString(nameIndex)
        } ?: "Файл"
    }

    private fun validateForm(): Boolean {
        var isValid = true
        val minLength = 10 // Минимальная длина текста
        val maxNewLines = 5 // Максимальное количество переносов строк подряд

        // Проверка текста вопроса
        val questionText = binding.questionInput.text?.toString()?.trim() ?: ""
        when {
            questionText.isEmpty() || questionText.isBlank() -> {
                binding.questionLayout.error = getString(R.string.enter_question)
                isValid = false
            }
            questionText.length < minLength -> {
                binding.questionLayout.error = getString(R.string.question_too_short)
                isValid = false
            }
            questionText.contains(Regex("\n{$maxNewLines,}")) -> {
                binding.questionLayout.error = getString(R.string.too_many_newlines)
                isValid = false
            }
            else -> {
                binding.questionLayout.error = null
            }
        }

        return isValid
    }
    private fun sendQuestion() {
        // Сбор данных формы
        val questionData = QuestionData(
            subject = binding.subjectInput.text.toString(),
            question = binding.questionInput.text.toString(),
            attachments = attachedFiles
        )

        // TODO: Отправка данных на сервер
        // Пока просто показываем сообщение об успехе
        showSuccessMessage()
        clearForm()
    }

    private fun showSuccessMessage() {
        Toast.makeText(
            requireContext(),
            getString(R.string.question_sent),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun clearForm() {
        binding.subjectInput.text?.clear()
        binding.questionInput.text?.clear()
        attachedFiles.clear()
        attachmentAdapter.notifyDataSetChanged()
    }

    companion object {
        private const val PICK_FILE_REQUEST_CODE = 123
    }
}