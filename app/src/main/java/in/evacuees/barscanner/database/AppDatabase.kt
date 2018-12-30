package `in`.evacuees.barscanner.database

import `in`.evacuees.barscanner.database.tables.Product
import `in`.evacuees.barscanner.database.tables.SoldProduct
import `in`.evacuees.barscanner.database.tables.Store
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(entities = [Product::class, SoldProduct::class, Store::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun daoAccess(): DaoAccess


    companion object {
        private var INSTANCE: AppDatabase? = null
        private val DATABASE_NAME = "BarCodeScanner_Db"

        fun getAppDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME)
                        // allow queries on the main thread.
                        // Don't do this on a real app! See PersistenceBasicSample for an example.
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as AppDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}