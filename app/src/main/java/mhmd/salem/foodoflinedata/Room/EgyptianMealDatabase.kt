package mhmd.salem.foodoflinedata.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mhmd.salem.foodoflinedata.data.Egyptian


@Database(entities = [Egyptian::class] ,version = 1)
abstract class EgyptianMealDatabase :RoomDatabase() {

   abstract val egyptianDao : EgyptianDao

   companion object{
       @Volatile
       var INSTANCE : EgyptianMealDatabase ? = null
       @Synchronized
       fun getInstance(context: Context):EgyptianMealDatabase{
           if (INSTANCE == null)
               INSTANCE = Room.databaseBuilder(
                   context,
                   EgyptianMealDatabase::class.java,
                   "egyptian.db"
               ).fallbackToDestructiveMigration().build()
           return INSTANCE as EgyptianMealDatabase
       }

   }
}