package `in`.evacuees.barscanner

import `in`.evacuees.barscanner.database.AppDatabase
import `in`.evacuees.barscanner.database.tables.Product
import `in`.evacuees.barscanner.database.tables.SoldProduct

class ProductUtils {
    companion object {

        fun productScanned(product: Product, storeId: String): SoldProduct {
            val sp = SoldProduct(product.productId)
            sp.productName = product.productName
            sp.storeId = storeId
            sp.sycn = false



            return sp
        }


    }

}