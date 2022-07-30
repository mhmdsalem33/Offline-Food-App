package mhmd.salem.foodoflinedata.Room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mhmd.salem.foodoflinedata.data.Category

@Dao
interface SeaFoodDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSeaFood(category : Category)


    @Delete
    suspend fun deleteSeaFood(category: Category)

    @Query("SELECT * FROM seaFoodInformation")
    fun getAllSeafoodMeals() : Flow<List<Category>>



}