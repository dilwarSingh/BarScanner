package `in`.evacuees.barscanner

import `in`.evacuees.barscanner.database.tables.AdapterSoldProductList
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
    private val adapter: AdapterSoldProductList by lazy { AdapterSoldProductList(list = list, context = this@AutoScannerActivity) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_scanner)

        //    barCode.showSoftInputOnFocus = false
        barCode.isFocusable = true
        barCode.requestFocus()
        productList.adapter = adapter

        barCode.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                //do what you want on the press of 'done'
                val str = barCode.text.toString().trim()
                productScanned(str)
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
                productScanned(str)
            } else {
                Toast.makeText(this, "Non-Enter Pressed", Toast.LENGTH_SHORT).show()
            }
        }

        return super.dispatchKeyEvent(KEvent)
    }

    private fun productScanned(id: String) {
        val productScanned = ProductUtils.productScanned(id)
        list.add(productScanned)
        adapter.listUpdated()
        barCode.setEmpty()
    }

    fun myToast(message: String): Unit {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}

private fun EditText.setEmpty() {
    setText("")
}
