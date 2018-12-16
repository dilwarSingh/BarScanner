package `in`.evacuees.barscanner.database.tables

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Store(@PrimaryKey val storeId: String) {

    lateinit var storeName: String

}