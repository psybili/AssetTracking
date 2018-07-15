package sample.onursaygili.assettracking.data.local

import android.arch.persistence.room.*
import android.databinding.BaseObservable

@Dao
interface BaseDao<T : BaseObservable> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(vararg t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(l: List<T>)

    @Update
    fun update(vararg t: T)

    @Delete
    fun delete(vararg t: T)

    @Delete
    fun deleteAll(l: List<T>)
}