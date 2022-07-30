package mhmd.salem.foodoflinedata.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mhmd.salem.foodoflinedata.data.Chicken

@Dao
interface ChickenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(chicken: List<Chicken>)



    @Query("SELECT * FROM chickenInformation")
    fun  getChickenSavedInformation () : Flow<List<Chicken>>



}