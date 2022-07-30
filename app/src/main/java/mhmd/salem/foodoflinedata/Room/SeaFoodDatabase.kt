package mhmd.salem.foodoflinedata.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mhmd.salem.foodoflinedata.data.Category
import mhmd.salem.foodoflinedata.data.Meal

@Database(entities = [Category::class ]   , version = 1 )
abstract class SeaFoodDatabase :RoomDatabase(){


    abstract val seaFoodDao : SeaFoodDao

    companion object{
        @Volatile
        var INSTANCE : SeaFoodDatabase ? = null
        @Synchronized
        fun getInstance(context : Context) :SeaFoodDatabase{
            if (INSTANCE == null)
                INSTANCE  = Room.databaseBuilder(
                    context,
                    SeaFoodDatabase::class.java,
                    "seafood.db"
                ).fallbackToDestructiveMigration().build()
            return INSTANCE!!
        }
    }


}