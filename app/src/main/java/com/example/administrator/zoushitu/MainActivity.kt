package com.example.administrator.zoushitu

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() ,ScrollChangeCallback {
    var list:ArrayList<OpenNumber> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scroll_content.setScrollViewListener(this)
        initdata()
        setlistener()
    }

    private fun setlistener() {

        btn_wan.setOnClickListener {
            chatview.setIndex(0)

        }
        btn_qian.setOnClickListener {
            chatview.setIndex(1)
        }
        btn_hide.setOnClickListener {
            chatview.isCombileLine=false;
            chatview.updateUI()
        }
        btn_show.setOnClickListener {
            chatview.isCombileLine=true
            chatview.updateUI()
        }
    }

    private fun initdata() {

        for (i in 0 until 120){
            var number=OpenNumber()
            if (i==50||i==52||i==69||i==72)number.opennumber="等待开奖"
            else number.opennumber=chatview.testData
            println("打印开奖号码:"+number.opennumber)
            number.qihao=i
            list.add(number)
        }
        chatview.setData(list)
    }

    override fun changeXScroll(left: Int) {
        scroll_content.scrollTo(left, scroll_content.getScrollY())
    }

    override fun changeYScoll(top: Int) {
        scroll_content.scrollTo(scroll_content.getScrollX(), top)
    }
}
