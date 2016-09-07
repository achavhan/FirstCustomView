package amol.firstcustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Amol on 06-09-2016.
 */
public class RandomDotsAnim extends SurfaceView implements Runnable {

    Thread thread = null;
    boolean canDraw = true;

    Canvas canvas;
    SurfaceHolder surfaceHolder;

    Paint ball1, ball2, ball3, ball4, ball5, ball6, ball7;

    float ballRadius = 10;

    float surfaceWidth = 0;
    float surfaceHeight = 0;

    float ballPositionY = 0;
    float ballPositionX = 0;

    DeviceDimensionsHelper deviceDimensionsHelper;

    float ballPositionX1 = 0;
    float ballPositionX2 = 0;
    float ballPositionX3 = 0;
    float ballPositionX4 = 0;
    float ballPositionX5 = 0;
    float ballPositionX6 = 0;
    float ballPositionX7 = 0;

    float maxWidth = 100;

    boolean ball1_moveToRigth = true;
    boolean ball2_moveToRigth = true;
    boolean ball3_moveToRigth = true;
    boolean ball4_moveToRigth = true;
    boolean ball5_moveToRigth = true;
    boolean ball6_moveToRigth = true;
    boolean ball7_moveToRigth = true;

    public RandomDotsAnim(Context context) {
        super(context);

        surfaceHolder = getHolder();

        ball1 = getBall("#ff005d");
        ball2 = getBall("#35ff99");
        ball3 = getBall("#008597");
        ball4 = getBall("#ffcc00");
        ball5 = getBall("#2d3443");
        ball6 = getBall("#ff7c35");
        ball7 = getBall("#4d407c");

        deviceDimensionsHelper = new DeviceDimensionsHelper(this.getContext());

        surfaceHeight = deviceDimensionsHelper.getDisplayHeight();
        surfaceWidth = deviceDimensionsHelper.getDisplayWidth();

        ballPositionY = (surfaceHeight / 2);
        ballPositionX = (surfaceWidth / 2);

        ballRadius = deviceDimensionsHelper.convertDpToPixel(ballRadius);

        ballPositionX1 = ballPositionX2 = ballPositionX3 = ballPositionX4 = ballPositionX5 = ballPositionX6 = ballPositionX7 = ballPositionX;
    }

    public RandomDotsAnim(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RandomDotsAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint getBall(String s) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(s));
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    public void run() {

        ballPositionX1 = -maxWidth;
        ballPositionX2 = +maxWidth;
        ballPositionX3 = -maxWidth;
        ballPositionX4 = +maxWidth;
        ballPositionX5 = -maxWidth;
        ballPositionX6 = +maxWidth;
        ballPositionX7 = -maxWidth;

        while (canDraw) {

            if (!surfaceHolder.getSurface().isValid())
                continue;

            if (ballPositionX1 < (ballPositionX - maxWidth)) ball1_moveToRigth = true;
            if (ballPositionX1 > (ballPositionX + maxWidth)) ball1_moveToRigth = false;
            if (ball1_moveToRigth) ballPositionX1 += 4.5;
            else ballPositionX1 -= 4.5;

            if (ballPositionX2 < (ballPositionX - maxWidth)) ball2_moveToRigth = true;
            if (ballPositionX2 > (ballPositionX + maxWidth)) ball2_moveToRigth = false;
            if (ball2_moveToRigth) ballPositionX2 += 4.7;
            else ballPositionX2 -= 4.7;

            if (ballPositionX3 < (ballPositionX - maxWidth)) ball3_moveToRigth = true;
            if (ballPositionX3 > (ballPositionX + maxWidth)) ball3_moveToRigth = false;
            if (ball3_moveToRigth) ballPositionX3 += 4.9;
            else ballPositionX3 -= 4.9;

            if (ballPositionX4 < (ballPositionX - maxWidth)) ball4_moveToRigth = true;
            if (ballPositionX4 > (ballPositionX + maxWidth)) ball4_moveToRigth = false;
            if (ball4_moveToRigth) ballPositionX4 += 5.1;
            else ballPositionX4 -= 5.1;

            if (ballPositionX5 < (ballPositionX - maxWidth)) ball5_moveToRigth = true;
            if (ballPositionX5 > (ballPositionX + maxWidth)) ball5_moveToRigth = false;
            if (ball5_moveToRigth) ballPositionX5 += 4.9;
            else ballPositionX5 -= 4.9;

            if (ballPositionX6 < (ballPositionX - maxWidth)) ball6_moveToRigth = true;
            if (ballPositionX6 > (ballPositionX + maxWidth)) ball6_moveToRigth = false;
            if (ball6_moveToRigth) ballPositionX6 += 4.7;
            else ballPositionX6 -= 4.7;

            if (ballPositionX7 < (ballPositionX - maxWidth)) ball7_moveToRigth = true;
            if (ballPositionX7 > (ballPositionX + maxWidth)) ball7_moveToRigth = false;
            if (ball7_moveToRigth) ballPositionX7 += 4.5;
            else ballPositionX7 -= 4.5;

            canvas = surfaceHolder.lockCanvas(null);
            // canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.drawColor(Color.parseColor("#0E1013"));
            drawCircle(ballPositionX1, ballPositionY - 240, ballRadius, ball1);
            drawCircle(ballPositionX2, ballPositionY - 160, ballRadius, ball2);
            drawCircle(ballPositionX3, ballPositionY - 80, ballRadius, ball3);
            drawCircle(ballPositionX4, ballPositionY, ballRadius, ball4);
            drawCircle(ballPositionX5, ballPositionY + 80, ballRadius, ball5);
            drawCircle(ballPositionX6, ballPositionY + 160, ballRadius, ball6);
            drawCircle(ballPositionX7, ballPositionY + 240, ballRadius, ball7);
            surfaceHolder.unlockCanvasAndPost(canvas);

        }

    }

    private void drawCircle(float ballPositionX, float y, float ballRadius, Paint ball) {
        canvas.drawCircle(ballPositionX, y, ballRadius, ball);
    }


    public void resume() {
        canDraw = true;
        thread = new Thread(this);
        thread.start();
    }


    public void pause() {
        canDraw = false;
        while (true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;
    }

}
