package `in`.evacuees.barscanner.database

import `in`.evacuees.barscanner.database.tables.Product
import `in`.evacuees.barscanner.database.tables.SoldProduct
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = [Product::class, SoldProduct::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun daoAccess(): DaoAccess

}