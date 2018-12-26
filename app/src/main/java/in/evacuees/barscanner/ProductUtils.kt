package `in`.evacuees.barscanner

import `in`.evacuees.barscanner.database.tables.Product
import `in`.evacuees.barscanner.database.tables.SoldProduct

class ProductUtils {
    companion object {

        fun productScanned(product: Product, storeId: String): SoldProduct {

            val pp = SoldProduct(product.productId)
            pp.productName = "Name_${product.productName}"
            pp.storeId = storeId
            return pp
        }

    }

}