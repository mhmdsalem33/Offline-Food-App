package mhmd.salem.foodoflinedata.Realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class BritishDatabase :RealmObject() {
    @PrimaryKey
    var id           :Long = 0
    var idMeal       :String ? = null
    var strMeal      :String ? = null
    var strMealThumb :String ? = null
}