import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,              // Use 'id' as the primary key
    val email: String,
    val std_name: String,
    val dept: String,
    val year: String,
    val u_rollno: String,
    val std_contact: String,
    val f_contact: String,
    val m_contact: String,
    val room_altd: String,
    val seater_altd: Int
)

data class User1(
    @SerializedName("users")
    val users: List<User>
)

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("std_name")
    val std_name: String,
    @SerializedName("dept")
    val dept: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("u_rollno")
    val u_rollno: String,
    @SerializedName("std_contact")
    val std_contact: String,
    @SerializedName("f_contact")
    val f_contact: String,
    @SerializedName("m_contact")
    val m_contact: String,
    @SerializedName("room_altd")
    val room_altd: String,
    @SerializedName("seater_altd")
    val seater_altd: Int
)
