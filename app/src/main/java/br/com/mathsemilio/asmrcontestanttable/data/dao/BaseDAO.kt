package br.com.mathsemilio.asmrcontestanttable.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update

@Dao
interface BaseDAO<T> {

    @Insert
    suspend fun insertData(data: T)

    @Update
    suspend fun updateData(data: T)
}