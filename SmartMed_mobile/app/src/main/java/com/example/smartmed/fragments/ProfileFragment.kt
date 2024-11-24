import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.smartmed.R
import com.example.smartmed.databinding.FragmentProfileBinding
import com.example.smartmed.enums.Gender
import java.time.format.DateTimeFormatter

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        displayProfile(ProfileRepository.getProfile())
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayProfile(profile: ProfileModel) {
        with(binding) {
            Glide.with(this@ProfileFragment)
                .load(profile.imageUrl)
                .placeholder(R.drawable.ic_error_doctor_appointment)
                .error(R.drawable.ic_error_doctor_appointment)
                .circleCrop()
                .into(profileImage)

            loginText.text = profile.login
            ageText.text = "${profile.age} лет"
            genderText.text = when (profile.gender) {
                Gender.MALE -> "Мужской"
                Gender.FEMALE -> "Женский"
            }

            // Беременность
            pregnancyCard.isVisible = profile.gender == Gender.FEMALE
            if (profile.gender == Gender.FEMALE) {
                if (profile.pregnancy.isPregnant) {
                    pregnancyWeekText.text = "Срок: ${profile.pregnancy.weekCount} недель"
                    dueDateText.text = "Предполагаемая дата родов: ${
                        profile.pregnancy.dueDate?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                    }"
                    dueDateText.isVisible = true
                } else {
                    pregnancyWeekText.text = "Нет"
                    dueDateText.isVisible = false
                }
            }

            // Заболевания
            if (profile.diseases.isNotEmpty()) {
                diseasesCard.isVisible = true
                diseasesText.text = profile.diseases.joinToString("\n") {
                    "• ${it.name}"
                }
            } else {
                diseasesCard.isVisible = false
            }

            // Аллергии
            if (profile.allergies.isNotEmpty()) {
                allergiesCard.isVisible = true
                allergiesText.text = profile.allergies.joinToString("\n") { allergy ->
                    "• ${allergy.name} (${allergy.getSeverityText()})"
                }
            } else {
                allergiesCard.isVisible = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}