package `in`.evacuees.barscanner

import `in`.evacuees.barscanner.Response.LoginResponse
import `in`.evacuees.barscanner.util.Api.Api
import `in`.evacuees.barscanner.util.AppConstants
import `in`.evacuees.barscanner.util.CommonObjects
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    val dialog: ProgressDialog by lazy { ProgressDialog(this) }
    val co: CommonObjects by lazy { CommonObjects(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dialog.apply {
            setTitle("Loading...")
            setMessage("Logging you in")
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            isIndeterminate = true
        }


        loginBt.setOnClickListener { hitLoginApi() }
    }

    private fun hitLoginApi() {
        dialog.show()

        Api.getApi().login(email.text.toString().trim(), password.text.toString().trim()).enqueue(
                object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                        try {
                            val loginData = response.body()

                            Toast.makeText(this@LoginActivity, loginData?.alert, Toast.LENGTH_SHORT).show()
                            if (loginData?.message == "success") {
                                co.putString(AppConstants.USER_ID, loginData.list!!.uid!!)
                                startActivity(Intent(this@LoginActivity, AutoScannerActivity::class.java))
                            }
                        } catch (e: Exception) {
                            Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                        } finally {
                            dialog.dismiss()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
        )


    }
}
