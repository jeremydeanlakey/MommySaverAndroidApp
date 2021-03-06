package com.mommysaverapp.toy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by jeremy on 1/21/14.
 */
public class CircleToy extends Toy {

    private float speedScale;
    private float offsetX;
    private float offsetY;
    private float vX;
    private float vY;
    private float orbitCenterX;
    private float orbitCenterY;

    public CircleToy(int color){
        speedScale = 1.0f / 75;
        defaultColor = color;
        currentColor = color;

        orbitCenterX = random.nextFloat();
        orbitCenterY = random.nextFloat();
        offsetX = random.nextFloat() / 2;
        offsetY = random.nextFloat() / 2;
        // start with speeds ranging from -speedScale to +speedScale
        vX = 2 * (random.nextFloat() - 0.5f) * speedScale;
        vY = 2 *(random.nextFloat() - 0.5f) * speedScale;

        colorTimer = 0;
        highlightColors = new ArrayList<Integer>();
        highlightColors.add(Color.RED);
        highlightColors.add(Color.CYAN);
        highlightColors.add(Color.YELLOW);
    }

    public void move(){
        offsetX += vX;
        offsetY += vY;
    }

    public void accelerate(){
        float denominator = (float) Math.sqrt((offsetX * offsetX) + (offsetY * offsetY));
        float g = 1.0f / 20 / 100;
        vX += - g * (offsetX / denominator);
        vY += - g * (offsetY / denominator);
    }

    public void draw(Canvas canvas){
        fadeColor();
        move();
        accelerate();
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        int scale = Math.min(h,w);
        float x = w * orbitCenterX + scale * offsetX;
        float y = h * orbitCenterY + scale * offsetY;
        float r = scale / 10.0f;
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(currentColor);
        canvas.drawCircle(x, y, r, paint);
    }

   public void onTouch(float touchX, float touchY, int w, int h){
        int scale = Math.min(h,w);
        float x = w * orbitCenterX + scale * offsetX;
        float y = h * orbitCenterY + scale * offsetY;
        float impactRadius = scale / 2.0f;
        float distance = (float) Math.sqrt((x - touchX) * (x - touchX) + (y - touchY) * (y - touchY));
        if (distance < impactRadius){
            highlight();
            vX = (x - touchX) / distance * speedScale;
            vY = (y - touchY) / distance * speedScale;
        }
    }

}
