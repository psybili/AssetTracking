package sample.onursaygili.assettracking.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Trip(
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null
) : BaseObservable() {
    @Ignore constructor() : this(null)

    var startDate = Date()
    var endDate: Date? = null
    var tripStatus: Int? = 1
    var imageUrl: String? = null

    override fun toString(): String {
        val sb = StringBuilder()
        if (id != null) sb.append("id: $id")
        sb.append("\nstart: $startDate")
        if (endDate != null) sb.append("\nend: $endDate")
        if (tripStatus != null) sb.append("\nstatus: $tripStatus")
        return sb.toString()
    }

    fun toStringCompact(): String {
        val sb = StringBuilder()
        sb.append("${SimpleDateFormat("EEE, MMM d, ''yy\nh:mm:ss a").format(startDate)}")
        if (endDate != null) sb.append(" - ${SimpleDateFormat("h:mm:ss a").format(endDate)}")
        return sb.toString()
    }
}