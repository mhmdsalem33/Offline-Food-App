package mhmd.salem.foodoflinedata.retrofit

import mhmd.salem.foodoflinedata.data.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("api/json/v1/1/filter.php")
    fun getSeafood(@Query("c") category :String) : Call<CategoryList>

    @GET("api/json/v1/1/lookup.php")
    fun getMeal(@Query("i") id : String) :Call<MealList>

    @GET("api/json/v1/1/filter.php")
    fun getOverPopular(@Query("c") category: String) :  Call<OverPopularList>

    @GET("api/json/v1/1/filter.php")
    fun getEgyptianMeals(@Query("a") meal :String) :Call<EgyptianList>

    @GET("/api/json/v1/1/filter.php")
    fun getBreakFastMeals(@Query("c") category: String) :Call<CategoryList>

    @GET("api/json/v1/1/filter.php")
    fun getChickenBreast(@Query("i") category : String) :Call<ChickenList>

    @GET("api/json/v1/1/search.php")
    fun searchMeals(@Query("s") searchQuery : String) : Call<MealList>

    @GET("api/json/v1/1/categories.php")
    fun getCategories()  : Call<CategoriesList>

    @GET("api/json/v1/1/search.php")
    fun getCategoryMeal(@Query("s") categoryName :String) :Call<CategoryMealList>


}