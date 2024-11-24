package com.example.smartmed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartmed.R
import com.example.smartmed.databinding.ItemAppointmentBinding
import com.example.smartmed.model.AppointmentModel

class AppointmentAdapter(
    private val onAppointmentClick: (AppointmentModel) -> Unit
) : ListAdapter<AppointmentModel, AppointmentAdapter.AppointmentViewHolder>(AppointmentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        return AppointmentViewHolder(
            ItemAppointmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppointmentViewHolder(
        private val binding: ItemAppointmentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.selectTimeButton.setOnClickListener {
                onAppointmentClick(getItem(adapterPosition))
            }
        }

        fun bind(appointment: AppointmentModel) {
            with(binding) {
                Glide.with(doctorImage)
                    .load(appointment.imageUrl)
                    .circleCrop()
                    .error(R.drawable.ic_error_doctor_appointment)
                    .into(doctorImage)

                doctorName.text = appointment.fio
                doctorSpecialty.text = appointment.post
                date.text = appointment.date
                dayOfTheWeek.text = appointment.dayOfWeek
                startDate.text = appointment.startTime
                endDate.text = appointment.endTime
            }
        }
    }

    private class AppointmentDiffCallback : DiffUtil.ItemCallback<AppointmentModel>() {
        override fun areItemsTheSame(oldItem: AppointmentModel, newItem: AppointmentModel): Boolean {
            return oldItem.fio == newItem.fio &&
                    oldItem.date == newItem.date &&
                    oldItem.startTime == newItem.startTime
        }

        override fun areContentsTheSame(oldItem: AppointmentModel, newItem: AppointmentModel): Boolean {
            return oldItem == newItem
        }
    }
}