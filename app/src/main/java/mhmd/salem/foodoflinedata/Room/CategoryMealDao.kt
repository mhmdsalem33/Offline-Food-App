package mhmd.salem.foodoflinedata.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import mhmd.salem.foodoflinedata.data.CategoryMeal

@Dao
interface CategoryMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(categoryMeal : List<CategoryMeal>)


    @Query("SELECT * FROM categoryMealInformation WHERE strCategory LIKE '%'||:categoryName||'%'")
    fun searchInCategoryName(categoryName :String) :Flow<List<CategoryMeal>>

}