package com.example.douda.objectmove;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.view.MenuItem;
import android.graphics.Bitmap;//import bitmap
import android.os.Handler;//執行序
import android.graphics.Canvas;//作畫的容器 帆布
import android.content.Context;//
import android.graphics.BitmapFactory;//
import android.widget.Button;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.ImageButton;

public class objectmove extends ActionBarActivity {

    Button button1;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new MovingPictureView(this));//產生物件
        //setContentView(R.layout.activity_objectmove);//預設

    }

    public void onClick(View v)
    {
        if(v==button1)
        {
            LayoutParams Ip=(LayoutParams) imageButton.getLayoutParams();
            Ip.x=Ip.x+1;
            imageButton.setLayoutParams(Ip);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_objectmove, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public class MovingPictureView extends View implements Runnable{
//用於顯示的圖片
        Bitmap bitmap;


//圖片座標轉化的執行緒是否運行，false，則停止運行
        boolean isRuning = true;


//圖片的Lfet，Top值
        int left = 0;
        int top = 0;


//用於同步執行緒
        Handler handler;


//向量，可以通過調節此兩個變數調節移動速度
        int dx = 1;
        int dy = 1;


        public MovingPictureView(Context coNtext) {


            super(coNtext);


            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.obelisk);


            handler = new Handler();


            new Thread(this).start();


        }


        @Override

//畫圖
        protected void onDraw(Canvas canvas) {

//將圖畫到畫板上
            canvas.drawBitmap(bitmap, left,top, null);


        }


        @Override


        public boolean onTouchEvent(MotionEvent event) {

            dx = dx*2;
            dy = dy*2;
            //isRuning = !isRuning;//當點擊螢幕，則將圖片浮動停止
            return true;


        }


        @Override


        public void run() {
            while(isRuning)
            {


//通過調節向量，來控制方向
                if(left < 0 || left > (getWidth() - bitmap.getWidth()))
                dx = -dx;
                if(top < 0 || top > (getHeight() - bitmap.getHeight()))
                dy = -dy;


                left = left+dx;
                top = top+dy;


                handler.post(new Runnable() {

                    @Override


                    public void run() {


                        invalidate();//一直執行onDraw()


                    }


                });


                try
                {
                    Thread.sleep(50);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }


        }



    }
}
