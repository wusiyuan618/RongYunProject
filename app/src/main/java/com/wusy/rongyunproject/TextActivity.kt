package com.wusy.rongyunproject

import android.content.Intent
import com.wusy.wusylibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_noti.*

class TextActivity : BaseActivity() {
    override fun getContentViewId(): Int {
        return R.layout.activity_noti
    }

    override fun findView() {

    }

    override fun init() {
        tv.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}
