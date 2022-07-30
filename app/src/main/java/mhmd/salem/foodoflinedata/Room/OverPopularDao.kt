package mhmd.salem.foodoflinedata.Room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mhmd.salem.foodoflinedata.data.OverPopular



@Dao
interface OverPopularDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(over :OverPopular)


    @Query("DELETE FROM overInformation")
    suspend fun deleteAllFromOver()

    @Delete
    suspend fun delete(over: OverPopular)

    @Query("SELECT * FROM overInformation")
    fun getAllDataOverPopular() :Flow<List<OverPopular>>

}

