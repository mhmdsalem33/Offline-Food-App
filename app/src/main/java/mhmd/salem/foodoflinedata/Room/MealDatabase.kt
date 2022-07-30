package mhmd.salem.foodoflinedata.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import mhmd.salem.foodoflinedata.data.Meal

@Database(entities = [Meal::class ]   , version = 1 )
@TypeConverters(TypeConverter::class)
abstract class MealDatabase :RoomDatabase() {

    abstract val mealDao : MealDao

    companion object{
        @Volatile
        var INSTANCE : MealDatabase ? = null
        @Synchronized
        fun getInstance(context :Context) :MealDatabase{
            if (INSTANCE == null)
                INSTANCE  = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration().build()
            return INSTANCE as MealDatabase
        }
    }


}