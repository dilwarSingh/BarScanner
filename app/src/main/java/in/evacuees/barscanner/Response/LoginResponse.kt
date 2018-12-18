package `in`.evacuees.barscanner.Response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

        @SerializedName("alert")
        val alert: String? = null,

        @SerializedName("message")
        val message: String? = null,

        @SerializedName("list")
        val list: List? = null
) {
    data class List(

            @SerializedName("uid")
            val uid: String? = null,

            @SerializedName("password")
            val password: String? = null,

            @SerializedName("type")
            val type: String? = null,

            @SerializedName("email")
            val email: String? = null,

            @SerializedName("status")
            val status: String? = null
    )
}