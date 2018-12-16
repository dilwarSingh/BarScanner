package `in`.evacuees.barscanner

import `in`.evacuees.barscanner.database.tables.SoldProduct

class ProductUtils {
    companion object {

        fun productScanned(id: String): SoldProduct {

            val pp = SoldProduct(id)
            pp.productName = "Name_$id"

            return pp
        }

    }

}