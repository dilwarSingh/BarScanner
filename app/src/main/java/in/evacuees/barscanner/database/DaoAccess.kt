package `in`.evacuees.barscanner.database

import `in`.evacuees.barscanner.database.tables.Product
import `in`.evacuees.barscanner.database.tables.SoldProduct
import `in`.evacuees.barscanner.database.tables.Store
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface DaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProducts(list: List<Product>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStores(list: List<Store>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSoldProducts(list: List<SoldProduct>)

    @Query("SELECT * from Store where storeId = :storeId")
    fun getStore(storeId: String): Store?

    @Query("SELECT * from Product where productId = :productId")
    fun getProduct(productId: String): Product?

}