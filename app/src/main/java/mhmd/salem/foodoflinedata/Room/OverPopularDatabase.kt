package mhmd.salem.foodoflinedata.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mhmd.salem.foodoflinedata.data.OverPopular


@Database(entities = [OverPopular::class] , version = 1)

abstract class OverPopularDatabase :RoomDatabase(){

    abstract val overPopularDao : OverPopularDao

    companion object{
        @Volatile
        var INSTANCE : OverPopularDatabase ? = null
        @Synchronized
        fun getInstance(context: Context):OverPopularDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    OverPopularDatabase::class.java,
                    "over.db"
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
    }
}

