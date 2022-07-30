package mhmd.salem.foodoflinedata.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import mhmd.salem.foodoflinedata.data.Chicken


@Database(entities = [Chicken::class] , version = 1)
abstract class ChickenDatabase : RoomDatabase(){


    abstract val chickenDao : ChickenDao

    companion object{
        @Volatile
        var INSTANCE : ChickenDatabase ? = null
        @Synchronized
        fun getInstance(context: Context): ChickenDatabase{
            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(
                    context,
                    ChickenDatabase::class.java,
                    "chicken.db"
                ).fallbackToDestructiveMigration().build()
            return INSTANCE as ChickenDatabase
        }

    }


}