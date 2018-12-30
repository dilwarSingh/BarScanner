package `in`.evacuees.barscanner.util.Api

import `in`.evacuees.barscanner.Response.LoginResponse
import `in`.evacuees.barscanner.Response.ProductsAndStores
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiAddress {


    @FormUrlEncoded
    @POST("login.php")
    fun login(@Field("email") email: String, @Field("password") password: String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("data.php")
    fun getData(@Field("uid") userId: String): Call<ProductsAndStores>

    @FormUrlEncoded
    @POST("order.php")
    fun placeOrder(@Field("uid") userId: String, @Field("product") pId: String, @Field("retailer") sId: String): Call<ResponseBody>

}