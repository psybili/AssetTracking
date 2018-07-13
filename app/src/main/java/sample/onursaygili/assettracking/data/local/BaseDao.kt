package sample.onursaygili.assettracking.data.local

import android.arch.persistence.room.*
import android.databinding.BaseObservable

@Dao
interface BaseDao<T : BaseObservable> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg t: T)

    @Update
    fun update(vararg t: T)

    @Delete
    fun delete(vararg t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(l: List<T>)

    @Delete
    fun delete(l: List<T>)
}