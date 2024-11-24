import android.net.Uri
import android.os.Parcelable

// AttachmentModel.kt
data class AttachmentModel(
    val uri: Uri,
    val fileName: String
)

// QuestionData.kt
data class QuestionData(
    val subject: String,
    val question: String,
    val attachments: List<AttachmentModel>
)