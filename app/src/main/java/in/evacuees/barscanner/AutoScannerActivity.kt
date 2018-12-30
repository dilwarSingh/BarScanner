package `in`.evacuees.barscanner

import `in`.evacuees.barscanner.database.AppDatabase
import `in`.evacuees.barscanner.database.tables.Product
import `in`.evacuees.barscanner.database.tables.SoldProduct
import `in`.evacuees.barscanner.util.Api.Api
import `in`.evacuees.barscanner.util.AppConstants
import `in`.evacuees.barscanner.util.CommonObjects
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_auto_scanner.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AutoScannerActivity : AppCompatActivity() {


    private val list = emptyList<SoldProduct>().toMutableList()
    private val adapter: AdapterSoldProductList
            by lazy { AdapterSoldProductList(list = list, context = this@AutoScannerActivity) }
    lateinit var appDatabase: AppDatabase
    val dialog: ProgressDialog by lazy { ProgressDialog(this) }
    val co: CommonObjects by lazy { CommonObjects(this) }

    var storeSelected: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_scanner)

        //    barCode.showSoftInputOnFocus = false
        barCode.isFocusable = true
        barCode.requestFocus()
        productList.adapter = adapter

        appDatabase = AppDatabase.getAppDatabase(this)

        dialog.apply {
            setTitle("Loading...")
            setMessage("Logging you in")
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            isIndeterminate = true
        }

        barCode.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                //do what you want on the press of 'done'
                val str = barCode.text.toString().trim()
                productScanned13(str)
            }
            if (EditorInfo.IME_FLAG_NO_ENTER_ACTION == actionId) {

            }
            false
        }
        productListBt.setOnClickListener {
            startActivity(Intent(this@AutoScannerActivity, ProductListActivity::class.java))
        }

    }

    override fun dispatchKeyEvent(KEvent: KeyEvent): Boolean {

        val TAG = "AutoSacnnerNew_"
        Log.d(TAG, KEvent.keyCode.toString() + " dispatchKeyEvent: scanOrder ")

        val keycode = KEvent.keyCode
        if (KEvent.action == KeyEvent.ACTION_UP && keycode != 59) {
            if (KEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
                // Toast.makeText(this, "Enter Pressed", Toast.LENGTH_SHORT).show()
                val str = barCode.text.toString().trim()
                productScanned13(str)
            } else {
                Toast.makeText(this, "Non-Enter Pressed", Toast.LENGTH_SHORT).show()
            }
        }

        return super.dispatchKeyEvent(KEvent)
    }

    private fun productScanned13(id: String) {
        if (id.isEmpty()) return

        val c = id[0]

        when (c.toLowerCase()) {
            'r' -> {
                storeEntry(id)
            }
            'p' -> {
                productEntry(id)
            }
            else -> {
                Toast.makeText(this, "Invalid Code", Toast.LENGTH_SHORT).show()
            }
        }
        barCode.setEmpty()
    }

    private fun productEntry(id: String) {

        if (storeSelected.isNullOrBlank()) {
            myToast("Please Select store first")
            return
        }

        val product = appDatabase.daoAccess().getProduct(id)

        if (product != null) {
            productScanned(product, storeSelected!!)
        } else {
            myToast("Invalid Product")
        }

    }

    private fun storeEntry(id: String) {

        val store = appDatabase.daoAccess().getStore(id)

        if (store != null) {
            storeSelected = store.storeId
            storeTv.text = storeSelected.toString()
            myToast("Selected Store ${store.storeName}")
        } else {
            myToast("Invalid Store")
        }

    }

    private fun productScanned(product: Product, storeId: String) {
        val productScanned = ProductUtils.productScanned(product, storeId)

        hitOrderApi(productScanned)


    }

    private fun hitOrderApi(productScanned: SoldProduct) {
        dialog.show()

        Api.getApi().placeOrder(co.getString(AppConstants.USER_ID), productScanned.productId, productScanned.storeId)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                        productScanned.sycn = true
                        appDatabase.daoAccess().insertSoldProduct(productScanned)
                        list.add(productScanned)
                        adapter.listUpdated()
                        dialog.dismiss()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        appDatabase.daoAccess().insertSoldProduct(productScanned)
                        dialog.dismiss()
                        list.add(productScanned)
                        adapter.listUpdated()

                        Log.e("ResponseFailure: ", t.message)
                        t.printStackTrace()
                    }

                })

    }

    fun myToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

fun EditText.setEmpty() {
    setText("")
}
