import android.os.Build
import androidx.annotation.RequiresApi
import com.example.smartmed.enums.Gender
import com.example.smartmed.fragments.Disease
import java.time.LocalDate

object ProfileRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getProfile(): ProfileModel {
        return ProfileModel(
            imageUrl = "https://example.com/profile.jpg",
            login = "Иванова Анна Петровна",
            age = "28",
            gender = Gender.FEMALE,
            pregnancy = Pregnancy(
                isPregnant = true,
                weekCount = 24,
                dueDate = LocalDate.of(2024, 9, 15)
            ),
            diseases = listOf(
                Disease("Артериальная гипертензия"),
            ),
            allergies = listOf(
                Allergy("Аспирин", Severity.SEVERE),
                Allergy("Пенициллин", Severity.MODERATE),
            )
        )
    }

    // Можно добавить тестовый профиль мужчины
    fun getMaleProfile(): ProfileModel {
        return ProfileModel(
            imageUrl = "https://example.com/male-profile.jpg",
            login = "Петров Иван Сергеевич",
            age = "32",
            gender = Gender.MALE,
            pregnancy = Pregnancy(isPregnant = false),
            diseases = listOf(
                Disease("Гипертония"),
                Disease("Астма")
            ),
            allergies = listOf(
                Allergy("Арахис", Severity.SEVERE),
                Allergy("Пыльца", Severity.MILD)
            )
        )
    }
}

// Перечисление для степени тяжести аллергии
enum class Severity {
    MILD,      // Легкая
    MODERATE,  // Средняя
    SEVERE     // Тяжелая
}

// Модель аллергии с указанием степени тяжести
data class Allergy(
    val name: String,
    val severity: Severity
) {
    fun getSeverityText(): String {
        return when (severity) {
            Severity.MILD -> "Легкая"
            Severity.MODERATE -> "Средняя"
            Severity.SEVERE -> "Тяжелая"
        }
    }
}

// Основная модель профиля
data class ProfileModel(
    val imageUrl: String,
    val login: String,
    val age: String,
    val gender: Gender,
    val pregnancy: Pregnancy,
    val diseases: List<Disease>,
    val allergies: List<Allergy>
)

// Модель для беременности
data class Pregnancy(
    val isPregnant: Boolean,
    val weekCount: Int? = null,
    val dueDate: LocalDate? = null
)



