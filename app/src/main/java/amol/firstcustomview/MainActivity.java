package amol.firstcustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    DrawPanel dp;
    Path path;
    private ArrayList<Path> pointsToDraw = new ArrayList<Path>();
    private Paint mPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        dp = new DrawPanel(this);
        dp.setOnTouchListener(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(30);

        FrameLayout fl = new FrameLayout(this);
        fl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        fl.addView(dp);
        setContentView(fl);

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        dp.pause();
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        dp.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent me) {
        // TODO Auto-generated method stub
        synchronized (pointsToDraw) {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                path = new Path();
                path.moveTo(me.getX(), me.getY());
                //path.lineTo(me.getX(), me.getY());
                pointsToDraw.add(path);
            } else if (me.getAction() == MotionEvent.ACTION_MOVE) {
                path.lineTo(me.getX(), me.getY());
            } else if (me.getAction() == MotionEvent.ACTION_UP) {
                //path.lineTo(me.getX(), me.getY());
            }
        }
        return true;

    }

    public class DrawPanel extends SurfaceView implements Runnable {

        Thread t = null;
        SurfaceHolder holder;
        boolean isItOk = false;

        public DrawPanel(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            holder = getHolder();
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (isItOk == true) {

                if (!holder.getSurface().isValid()) {
                    continue;
                }

                Canvas c = holder.lockCanvas();
                c.drawARGB(255, 0, 0, 0);
                onDraw(c);
                holder.unlockCanvasAndPost(c);
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            synchronized (pointsToDraw) {
                for (Path path : pointsToDraw) {
                    canvas.drawPath(path, mPaint);
                }
            }
        }

        public void pause() {
            isItOk = false;
            while (true) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            t = null;
        }

        public void resume() {
            isItOk = true;
            t = new Thread(this);
            t.start();

        }



    }

}
