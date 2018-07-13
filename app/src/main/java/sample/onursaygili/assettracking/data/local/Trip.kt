package sample.onursaygili.assettracking.data.local

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.databinding.BaseObservable
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

    override fun toString(): String {
        val sb = StringBuilder()
        if (id != null) sb.append("id: $id")
        sb.append("\nstart: $startDate")
        if (endDate != null) sb.append("\nend: $endDate")
        return sb.toString()
    }
}