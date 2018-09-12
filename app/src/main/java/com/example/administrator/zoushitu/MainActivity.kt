package com.example.administrator.zoushitu

import android.content.res.Resources
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.*
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.administrator.zoushitu.ui.FragTest
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : FragmentActivity() {
    var list: ArrayList<OpenNumber> = ArrayList()
    var qihaoList: ArrayList<String> = ArrayList()
    var tishi: ArrayList<String> = ArrayList()
    private var mLeftItemH: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initdata()
        setlistener()
        initUI()
    }

    private fun initUI() {

        mpager.adapter=SsAdapter(supportFragmentManager)

        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024/1024).toInt()
        Log.d(ChartView.Tag, "Max memory is " + maxMemory + "M");


    }

    private fun setlistener() {
        btn1.setOnClickListener {
            mpager.setCurrentItem(1)
        }
        btn2.setOnClickListener {
            mpager.setCurrentItem(2)
        }
        btn3.setOnClickListener {
            mpager.setCurrentItem(3)
        }
        btn4.setOnClickListener {
            mpager.setCurrentItem(8)
        }
        bt5.setOnClickListener {
            mpager.setCurrentItem(5)
        }
        btn_wan.setOnClickListener {
            chatview.setIndex(0)

        }
        btn_qian.setOnClickListener {
            chatview.setIndex(1)
        }
        btn_hide.setOnClickListener {
            chatview.isCombileLine = false
            chatview.updateUI()
        }
        btn_show.setOnClickListener {
            chatview.isCombileLine = true
            chatview.updateUI()
        }
        btn_heji_hide.setOnClickListener {
            chatview.setHeji(0)
            chatview.requestLayout()
            chatview.updateUI()
        }
        btn_heji_show.setOnClickListener {
            chatview.setHeji(3)
            chatview.requestLayout()
            chatview.updateUI()
        }
        scroll_content.viewTreeObserver.addOnGlobalLayoutListener {
            scroll_content.fullScrollV(View.FOCUS_DOWN)
        }

    }

    private fun initdata() {
        mLeftItemH = resources.getDimensionPixelSize(R.dimen.item_wh)
        for (i in 0 until 120) {
            var number = OpenNumber()
            if (i == 50 || i == 52 || i == 69 || i == 72) number.opennumber = "等待开奖"
            else number.opennumber = chatview.testData
            println("打印开奖号码:" + number.opennumber)
            number.qihao = 2018911 + i
            list.add(number)
            qihaoList.add("201811" + i)
        }
        chatview.setData(list)
    }


    open class SsAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return FragTest(position)
        }

        override fun getCount(): Int {
            return 8
        }

    }

}
