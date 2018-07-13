package sample.onursaygili.assettracking.data.local

import android.arch.persistence.room.TypeConverter

import java.util.Date


class Converters {
    @TypeConverter fun fromTimestamp(value: Long?) = if (value == null) null else Date(value)

    @TypeConverter fun dateToTimestamp(date: Date?) = date?.time
}