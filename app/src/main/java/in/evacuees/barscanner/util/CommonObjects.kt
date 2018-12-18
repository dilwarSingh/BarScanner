package `in`.evacuees.barscanner.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class CommonObjects(private val context: Context) {
    internal var editor: SharedPreferences? = null

    private val prefrances: SharedPreferences
        get() = context.getSharedPreferences("Bar_code_reader", MODE_PRIVATE)

    fun putString(key: String, value: String) {
        prefrances.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return prefrances.getString(key, "")!!
    }

}
