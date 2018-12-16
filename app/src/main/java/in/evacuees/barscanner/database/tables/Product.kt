package `in`.evacuees.barscanner.database.tables

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Product(@PrimaryKey val productId: String) {
    lateinit var productName: String
}