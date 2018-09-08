package com.example.administrator.zoushitu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class ChartView extends View {
    private int mBallWH;
    //红球个数:33个
    private int mRedNum=10;

    //蓝球个数:16个
    private int mBlueNum=16;
    //网格的水平间距
    private float mDeltaX;
    //网格垂直间距
    private float mDeltaY;
    //当前View的宽度
    private int mWidth;

    //当前View的高度
    private int mHeight;
    ArrayList<String[]> ballList=new ArrayList<>();
    ArrayList<Integer> choseList=new ArrayList<>();
    private Paint mPaintLine;
    private Paint mPaintBall;
    private Paint mPaintText;
    private Paint mPainTextBall;
    private Paint mPaintLinkLine;
    private Paint mpainLayoutCommon;
    private Paint mpainLayoutSpec;

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
    public void init(){
        mBallWH=getResources().getDimensionPixelSize(R.dimen.trend_ball_wh);
        //设置单个网格的水平和垂直间距
        this.mDeltaY=mBallWH*2f;
        Log.i("delta","deltay:"+mDeltaY);//高度;50
        this.mDeltaX=this.mDeltaY;
        ballList.clear();
        initLinePaint();
        for (int i = 0; i < 40; i++) {
            String[] str=new String[10];
            for (int j = 0; j < 10; j++) {
                str[j]=String.valueOf(j);
            }
            ballList.add(str);
        }
        choseList.add(1);
        choseList.add(9);
        choseList.add(2);
        choseList.add(4);
        choseList.add(6);
        choseList.add(9);
        choseList.add(8);
        choseList.add(3);
        choseList.add(4);
        choseList.add(3);
        choseList.add(8);
        choseList.add(3);
        choseList.add(9);
        choseList.add(3);
        choseList.add(3);
        choseList.add(5);
        for (int i = 0; i < 10; i++) {
            choseList.add(i);
        }
        for (int i = 0; i < 10; i++) {
            choseList.add(i);
        }
        for (int i = 0; i < 10; i++) {
            choseList.add(i);
        }
        for (int i = 0; i < 10; i++) {
            choseList.add(i);
        }
        for (int i = 0; i < 10; i++) {
            choseList.add(i);
        }
        for (int i = 0; i < 10; i++) {
            choseList.add(i);
        }
    }

    private void initLinePaint() {
        int dpValue=getScreenDenisty();
        //网格线画笔
        mPaintLine=new Paint();
        mPaintLine.setColor(Color.GRAY);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStrokeWidth(dpValue*0.6f/160);

        mPaintBall = new Paint();
        mPaintBall.setColor(Color.parseColor("#BE944E"));
        mPaintBall.setAntiAlias(true);


        //普通文字画笔
        mPaintText=new Paint();
        mPaintText.setColor(Color.BLACK);
        mPaintText.setTextSize((dpValue*12/160));
        mPaintText.setAntiAlias(true);
        mPaintText.setStrokeWidth(2f);
        mPaintText.setTextAlign(Paint.Align.CENTER);

        //小球上面的文字画笔
        mPainTextBall=new Paint();
        mPainTextBall.setColor(Color.WHITE);
        mPainTextBall.setTextSize((dpValue*12/160));
        mPainTextBall.setAntiAlias(true);
        mPainTextBall.setStrokeWidth(2f);
        mPainTextBall.setTextAlign(Paint.Align.CENTER);

        mPaintLinkLine=new Paint();
        mPaintLinkLine.setColor(Color.parseColor("#E7D7A1"));
        mPaintLinkLine.setTextSize((dpValue*12/160));
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
        setMeasuredDimension((int) ((mRedNum) * mDeltaX), (int) (40 * mDeltaY));
        //取得测量之后当前View的宽度
        this.mWidth = getMeasuredWidth();
        //取得测量之后当前View的高度
        this.mHeight = getMeasuredHeight();
        //重新绘制,不重绘,不会生效;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawXY(canvas);
        drawLin(canvas);
        drawText(canvas); }

    private void drawLin(Canvas canvas) {
        for (int i = 0; i < choseList.size(); i++) {
            Integer number = choseList.get(i);
            if (i<choseList.size()-1) {
                Integer next_number = choseList.get(i + 1);
                float x = number * mDeltaX + (mDeltaX / 2);
                float y = i * mDeltaX + (mDeltaY / 2);
                float next_x = next_number * mDeltaX + (mDeltaX / 2);
                float next_y = (i+1) * mDeltaX + (mDeltaY / 2);
                canvas.drawLine(x, y, next_x, next_y, mPaintLinkLine);
            }
        }
    }
    private void drawText(Canvas canvas) {
        for (int i = 0; i < 40; i++) {
            Integer choseNumber = choseList.get(i);
            for (int j = 0; j < 10; j++) {
                float x=mDeltaX*j+(mDeltaX/2);
                float y=mDeltaY*i+(mDeltaY/2);
                if (choseNumber==j){
                canvas.drawCircle(x,y,mDeltaX/2,mPaintBall);
                }
                float text_y = mPaintText.measureText(String.valueOf(i));
                canvas.drawText(String.valueOf(j),x,y+(text_y/2),choseNumber==j?mPainTextBall:mPaintText);
            }
        }
    }


    private void drawXY(Canvas canvas) {

        for (int i = 0; i < 40; i++) {
            canvas.drawRect(0,mDeltaY*i,mWidth,mDeltaY*i+mDeltaY,i%2==0?mpainLayoutCommon:mpainLayoutSpec);
        }

        for (int i = 0; i < 10; i++) {//画y轴
            canvas.drawLine(mDeltaX*i,0,mDeltaX*i,mHeight,mPaintLine);
        }
        for (int i = 0; i < 40; i++) {//画x轴
            canvas.drawLine(0,mDeltaY*i,mWidth,mDeltaY*i,mPaintLine);
        }

    }

    /**
     * 获取当前屏幕的密度
     * @return
     */
    public int getScreenDenisty(){
        DisplayMetrics dm=getResources().getDisplayMetrics();
        return dm.densityDpi;
    }
}
