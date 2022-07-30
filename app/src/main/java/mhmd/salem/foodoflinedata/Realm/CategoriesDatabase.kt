package mhmd.salem.foodoflinedata.Realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class CategoriesDatabase :RealmObject(){
    @PrimaryKey
    var primaryKey  : Long = 0
    var idCategory  : String ? = null
    var strCategory : String ? = null
    var strCategoryThumb      : String ? = null
    var strCategoryDescription: String ? = null
}