package mhmd.salem.foodoflinedata.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mhmd.salem.foodoflinedata.data.Egyptian

@Dao
interface EgyptianDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(egyptian: List<Egyptian>)


    @Query("SELECT * FROM egyptianInformation" )
    fun getAllFromEgyptian() :Flow<List<Egyptian>>


}