package `in`.evacuees.barscanner.util.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Api {

    companion object {

        fun getApi(): ApiAddress {
            val retrofit = Retrofit.Builder()
                    .baseUrl("http://sandychahal.com/APP/marketing/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(ApiAddress::class.java)
        }


    }
}
