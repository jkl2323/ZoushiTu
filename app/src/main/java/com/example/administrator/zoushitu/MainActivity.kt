package com.example.administrator.zoushitu

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,ScrollChangeCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scroll_content.setScrollViewListener(this)
    }

    override fun changeXScroll(left: Int) {
        scroll_content.scrollTo(left, scroll_content.getScrollY())
    }

    override fun changeYScoll(top: Int) {
        scroll_content.scrollTo(scroll_content.getScrollX(), top)
    }
}
