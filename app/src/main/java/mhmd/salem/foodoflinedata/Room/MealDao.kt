package mhmd.salem.foodoflinedata.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mhmd.salem.foodoflinedata.data.Category
import mhmd.salem.foodoflinedata.data.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal :Meal)

    @Query("SELECT * FROM mealInformation WHERE  idMeal LIKE '%'||:idQuery||'%'")
    fun searchInMealsById(idQuery: String)  :Flow<List<Meal>>


    @Query("SELECT * FROM mealInformation")
    fun getAllMeals()   : LiveData<List<Meal>>


}