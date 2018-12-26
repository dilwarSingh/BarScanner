package `in`.evacuees.barscanner.database

import android.arch.persistence.room.Room
import android.content.Context

class DatabaseClient private constructor(private val mCtx: Context) {

    val appDatabase: AppDatabase = Room.databaseBuilder(mCtx, AppDatabase::class.java, "BAR_CODE_SCANNER_DB").build()

    companion object {

        private var mInstance: DatabaseClient? = null

        @Synchronized
        fun getInstance(mCtx: Context): DatabaseClient {
            if (mInstance == null) {
                mInstance = DatabaseClient(mCtx)
            }
            return mInstance as DatabaseClient
        }
    }
}