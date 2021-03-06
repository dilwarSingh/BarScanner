package `in`.evacuees.barscanner

import `in`.evacuees.barscanner.Response.ProductsAndStores
import `in`.evacuees.barscanner.database.AppDatabase
import `in`.evacuees.barscanner.database.tables.Product
import `in`.evacuees.barscanner.database.tables.Store
import `in`.evacuees.barscanner.util.Api.Api
import `in`.evacuees.barscanner.util.AppConstants
import `in`.evacuees.barscanner.util.CommonObjects
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_product_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListActivity : AppCompatActivity() {

    val dialog: ProgressDialog by lazy { ProgressDialog(this) }
    val co: CommonObjects by lazy { CommonObjects(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        dialog.apply {
            setTitle("Loading...")
            setMessage("Logging you in")
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            isIndeterminate = true
        }


        hitApi()

    }

    private fun hitApi() {
        dialog.show()

        Api.getApi().getData(co.getString(AppConstants.USER_ID)).enqueue(object : Callback<ProductsAndStores> {
            override fun onResponse(call: Call<ProductsAndStores>, response: Response<ProductsAndStores>) {
                try {
                    val data = response.body()

                    if (data?.message == "success") {
                        val products = data.products

                        val productList = emptyList<Product>().toMutableList()
                        val storeList = emptyList<Store>().toMutableList()

                        products.forEach {
                            val item = Product(it.code!!)
                            item.productName = it.name!!
                            item.status = "Product"

                            productList.add(item)
                        }
                        data.retailers!!.forEach {
                            val item = Product(it!!.code!!)
                            item.productName = it.name!!
                            item.status = "Retailers"

                            productList.add(item)


                            val store = Store(it.code!!)
                            store.storeName = it.name
                            storeList.add(store)
                        }

                        AppDatabase.getAppDatabase(this@ProductListActivity)
                                .daoAccess().insertAllProducts(productList)

                        AppDatabase.getAppDatabase(this@ProductListActivity)
                                .daoAccess().insertAllStores(storeList)


                        productListRecycler.adapter = AdapterProductList(this@ProductListActivity, productList)

                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ProductListActivity, e.message, Toast.LENGTH_SHORT).show()
                } finally {
                    dialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ProductsAndStores>, t: Throwable) {
                Toast.makeText(this@ProductListActivity, t.message, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }


        })
    }
}
