package com.example.niketest.feature.checkout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.example.niketest.R
import androidx.lifecycle.observe
import com.example.niketest.common.EXTRA_KEY_ID
import com.example.niketest.common.NikeActivity
import com.example.niketest.common.formatPrice
import com.example.niketest.feature.main.MainActivity
import com.example.niketest.feature.order.OrderHistoryActivity
import kotlinx.android.synthetic.main.activity_check_out.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : NikeActivity() {
    val viewModel: CheckOutViewModel by viewModel {
        val uri: Uri? = intent.data
        if (uri != null)
            parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        else parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        viewModel.checkoutLiveData.observe(this){
            purchaseStatusTv.text=if (it.purchase_success) "پرداخت با موفقیت انجام شد" else "خرید ناموفق"
            orderStatusTv.text= it.payment_status
            orderPriceTv.text= formatPrice(it.payable_price)
        }

        returnHomeBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        orderHistoryBtn.setOnClickListener {
            startActivity(Intent(this,OrderHistoryActivity::class.java))
        }

        checkOutToolbar.onBackButtonClickListener= View.OnClickListener {
            finish()
        }

    }
}