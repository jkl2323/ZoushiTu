package com.example.administrator.zoushitu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class ChartView extends View {
    private int mBallWH;
    //红球个数:33个
    private int mRedNum = 10;

    //蓝球个数:16个
    private int mBlueNum = 16;
    //网格的水平间距
    private float mDeltaX;
    //网格垂直间距
    private float mDeltaY;
    //当前View的宽度
    private int mWidth;

    //当前View的高度
    private int mHeight;
    private Paint mPaintLine;
    private Paint mPaintBall;
    private Paint mPaintText;
    private Paint mPainTextBall;
    private Paint mPaintLinkLine;
    private Paint mpainLayoutCommon;
    private Paint mpainLayoutSpec;
    private ArrayList<OpenNumber> openlist;
    private int index = 0;// 号码格式如下 1,2,3,2,4  0表示万位 1表示千位 以此类推

    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mBallWH = getResources().getDimensionPixelSize(R.dimen.trend_ball_wh);
        //设置单个网格的水平和垂直间距
        this.mDeltaY = mBallWH * 2f;
        Log.i("delta", "deltay:" + mDeltaY);//高度;50
        this.mDeltaX = this.mDeltaY;
        initLinePaint();
    }

    private void initLinePaint() {
        int dpValue = getScreenDenisty();
        //网格线画笔
        mPaintLine = new Paint();
        mPaintLine.setColor(Color.GRAY);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStrokeWidth(dpValue * 0.6f / 160);

        mPaintBall = new Paint();
        mPaintBall.setColor(Color.parseColor("#BE944E"));
        mPaintBall.setAntiAlias(true);


        //普通文字画笔
        mPaintText = new Paint();
        mPaintText.setColor(Color.BLACK);
        mPaintText.setTextSize((dpValue * 12 / 160));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(2f);
        mPaintText.setTextAlign(Paint.Align.CENTER);

        //小球上面的文字画笔
        mPainTextBall = new Paint();
        mPainTextBall.setColor(Color.WHITE);
        mPainTextBall.setTextSize((dpValue * 12 / 160));
        mPainTextBall.setAntiAlias(true);
        mPainTextBall.setStrokeWidth(2f);
        mPainTextBall.setTextAlign(Paint.Align.CENTER);

        mPaintLinkLine = new Paint();
        mPaintLinkLine.setColor(Color.parseColor("#E7D7A1"));
        mPaintLinkLine.setTextSize((dpValue * 12 / 160));
        mPaintLinkLine.setAntiAlias(true);
        mPaintLinkLine.setStrokeWidth(12f);
        mPaintLinkLine.setTextAlign(Paint.Align.CENTER);


        mpainLayoutCommon = new Paint();
        mpainLayoutCommon.setColor(Color.WHITE);

        mpainLayoutSpec = new Paint();
        mpainLayoutSpec.setColor(Color.parseColor("#F3EFE2"));

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) ((mRedNum) * mDeltaX), (int) (120 * mDeltaY));
        //取得测量之后当前View的宽度
        this.mWidth = getMeasuredWidth();
        //取得测量之后当前View的高度
        this.mHeight = getMeasuredHeight();
        //重新绘制,不重绘,不会生效;
        invalidate();
    }

    public void setData(ArrayList<OpenNumber> openlist) {
        this.openlist = openlist;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == openlist) return;
        drawXY(canvas);
        drawLin(canvas);
        drawText(canvas);
    }

    private void drawLin(Canvas canvas) {
        float[] lastOpen = null;
      tag :  for (int i = 0; i < openlist.size(); i++) {
             float y = mDeltaY * i + (mDeltaY / 2);
            if (openlist.get(i).getOpennumber().equals("等待开奖")) {
                if (openlist.get(i).getOpennumber().equals("等待开奖")) {
                    Paint.FontMetrics fontMetrics = mPaintText.getFontMetrics();
                    float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
                    float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
                    int baseLineY = (int) (y - top / 2 - bottom / 2);//基线中间点的y轴计算公式
                    canvas.drawRect(0, mDeltaY * i, mWidth, mDeltaY * i + mDeltaY, i % 2 == 0 ? mpainLayoutCommon : mpainLayoutSpec);
                    canvas.drawText("等待开奖", mWidth/2, baseLineY, mPaintText);
                    continue tag;
                }
            }
            Integer choseNumber = Integer.valueOf(openlist.get(i).getOpennumber().split(",")[index]);
            for (int j = 0; j < 10; j++) {
                float x = mDeltaX * j + (mDeltaX / 2);

                if (choseNumber == j) {
                    if (null != lastOpen) {
                        canvas.drawLine(lastOpen[0], lastOpen[1], x, y, mPaintLinkLine);
                    }
                    lastOpen = new float[2];
                    lastOpen[0] = x;
                    lastOpen[1] = y;
                }
            }
        }
    }

    private void drawText(Canvas canvas) {
        tag:
        for (int i = 0; i < openlist.size(); i++) {
            for (int j = 0; j < 10; j++) {
                float x = mDeltaX * j + (mDeltaX / 2);
                float y = mDeltaY * i + (mDeltaY / 2);
                if (openlist.get(i).getOpennumber().equals("等待开奖")) {
                    continue tag;
                }
                Integer choseNumber = Integer.valueOf(openlist.get(i).getOpennumber().split(",")[index]);
                if (choseNumber == j) {
                    canvas.drawCircle(x, y, mDeltaX / 2, mPaintBall);
                }
                Paint.FontMetrics fontMetrics = (choseNumber == j ? mPainTextBall : mPaintText).getFontMetrics();
                float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
                float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
                int baseLineY = (int) (y - top / 2 - bottom / 2);//基线中间点的y轴计算公式
                canvas.drawText(String.valueOf(j), x, baseLineY, choseNumber == j ? mPainTextBall : mPaintText);
            }
        }
    }


    private void drawXY(Canvas canvas) {

        for (int i = 0; i < openlist.size(); i++) {//画x轴
            canvas.drawRect(0, mDeltaY * i, mWidth, mDeltaY * i + mDeltaY, i % 2 == 0 ? mpainLayoutCommon : mpainLayoutSpec);
            canvas.drawLine(0, mDeltaY * i, mWidth, mDeltaY * i, mPaintLine);
        }
        for (int i = 0; i < 10; i++) {//画y轴
            canvas.drawLine(mDeltaX * i, 0, mDeltaX * i, mHeight, mPaintLine);
        }

    }
    public void setIndex(int index){
        this.index=index;
        invalidate();
    }
    /**
     * 获取当前屏幕的密度
     *
     * @return
     */
    public int getScreenDenisty() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return dm.densityDpi;
    }


    public  String getTestData() {
        String str="";
        Random ran = new Random();
        while (!IsLen(str)){
            int data=ran.nextInt(10);
            if (!IsContain(str,data)){
                str+=data+",";
            }

        }
        return str;
    }
    private  boolean IsContain(String str,int data){
        if ("".equals(str))return false;
        String[] split = str.split(",");
        for (int i = 0; i < split.length; i++) {
            if (Integer.valueOf(split[i])==data)return true;
        }
        return false;
    }
    private static boolean IsLen(String data){
        if (data.split(",").length>=5)return true;
            return false;
    }
}
