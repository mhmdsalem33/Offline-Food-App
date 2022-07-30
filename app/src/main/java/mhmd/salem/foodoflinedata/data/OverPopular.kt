package mhmd.salem.foodoflinedata.data

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "overInformation")
data class OverPopular(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
)

