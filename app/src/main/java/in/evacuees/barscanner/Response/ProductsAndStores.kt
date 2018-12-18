package `in`.evacuees.barscanner.Response

data class ProductsAndStores(
        val retailers: List<RetailersItem?>? = null,
        val alert: String? = null,
        val message: String? = null,
        val products: MutableList<ProductsItem> = emptyList<ProductsItem>().toMutableList()
)
