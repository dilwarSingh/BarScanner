package `in`.evacuees.barscanner

import `in`.evacuees.barscanner.database.AppDatabase
import `in`.evacuees.barscanner.database.DatabaseClient
import `in`.evacuees.barscanner.database.tables.Product
import `in`.evacuees.barscanner.database.tables.SoldProduct
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_auto_scanner.*

class AutoScannerActivity : AppCompatActivity() {


    private val list = emptyList<SoldProduct>().toMutableList()
    private val adapter: AdapterSoldProductList
            by lazy { AdapterSoldProductList(list = list, context = this@AutoScannerActivity) }
    lateinit var appDatabase: AppDatabase

    var storeSelected: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_scanner)

        //    barCode.showSoftInputOnFocus = false
        barCode.isFocusable = true
        barCode.requestFocus()
        productList.adapter = adapter

        appDatabase = DatabaseClient.getInstance(this).appDatabase

        barCode.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                //do what you want on the press of 'done'
                val str = barCode.text.toString().trim()
                productScanned13(str)
                Toast.makeText(this@AutoScannerActivity, str, Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this, "Enter Pressed", Toast.LENGTH_SHORT).show()
                val str = barCode.text.toString().trim()
                productScanned13(str)
            } else {
                Toast.makeText(this, "Non-Enter Pressed", Toast.LENGTH_SHORT).show()
            }
        }

        return super.dispatchKeyEvent(KEvent)
    }

    private fun productScanned13(id: String) {
        if (id.isEmpty()) {
            return
        }

        val c = id[0]

        when (c.toLowerCase()) {
            's' -> {
                storeEntry(id)
            }
            'p' -> {
                productEntry(id)
            }
            else -> {
                Toast.makeText(this, "Invalid Code", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun productEntry(id: String) {

        if (storeSelected != null) {
            myToast("Please Select store first")
            return
        }

        val product = appDatabase.daoAccess().getProduct(id)

        if (product != null) {
            productScanned(product, storeSelected!!)
        } else {
            myToast("Invalid Store")
        }
        barCode.setEmpty()

    }

    private fun storeEntry(id: String) {

        val store = appDatabase.daoAccess().getStore(id)

        if (store != null) {
            storeSelected = store.storeId
            myToast("Selected Store ${store.storeName}")
        } else {
            myToast("Invalid Store")
        }

        barCode.setEmpty()
    }

    private fun productScanned(product: Product, storeId: String) {
        val productScanned = ProductUtils.productScanned(product, storeId)
        list.add(productScanned)
        adapter.listUpdated()
        barCode.setEmpty()
    }

    fun myToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

fun EditText.setEmpty() {
    setText("")
}
