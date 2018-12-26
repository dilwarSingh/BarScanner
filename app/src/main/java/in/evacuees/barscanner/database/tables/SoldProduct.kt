package `in`.evacuees.barscanner.database.tables

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity
data class SoldProduct(val productId: String) {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var sycn: Boolean = false
    lateinit var productName: String
    lateinit var storeId: String
}