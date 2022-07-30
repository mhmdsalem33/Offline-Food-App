package mhmd.salem.foodoflinedata.Room

import android.content.Context
import androidx.room.*
import androidx.room.TypeConverter
import mhmd.salem.foodoflinedata.data.CategoryMeal

@TypeConverters(CategoryMealTypeConverter::class)
@Database(entities = [CategoryMeal::class] , version = 1)
abstract class CategoryMealDatabase  :RoomDatabase(){
    abstract  val categoryMealDao : CategoryMealDao

    companion object{
        @Volatile
        var INSTANCE : CategoryMealDatabase ? = null
        @Synchronized
        fun getInstance(context: Context): CategoryMealDatabase {
            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(
                    context,
                    CategoryMealDatabase::class.java,
                    "meals.db"
                ).fallbackToDestructiveMigration().build()
            return INSTANCE as CategoryMealDatabase
        }
    }

}