package link.example.solarsystem;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import link.example.solarsystem.core.Planet;

public class MainActivity extends AppCompatActivity {

    ImageView[] imageViews;
    Bitmap[] bitmaps;
    Planet[] planets;
    ObjectAnimator[] animations;
    Canvas[] canvases;
    int[] screenDimensions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenDimensions = getDisplayDimensions();
        int radius;
        float centerX=(float)screenDimensions[0]/2;
        float centerY=(float)screenDimensions[1]/2;
        if (centerX<centerY) {
            radius=screenDimensions[0]/42;
        } else {
                radius=screenDimensions[1]/42;
       }

        planets=new Planet[7];
        planets[0]=new Planet("Sun", centerX, centerY, Color.YELLOW,radius*2,0,1);

        planets[1]=new Planet("Mercury", centerX-4*radius, centerY,Color.GRAY,radius,2000,-1);

        planets[2]=new Planet("Venus", centerX-7*radius,centerY,Color.CYAN,radius,7000,-1);

        planets[3]=new Planet("Earth", centerX-10*radius, centerY,Color.BLUE,radius,10000,-1);

        planets[4]=new Planet("Mars", centerX-13*radius,centerY,Color.RED,radius,12000,-1);

        planets[5]=new Planet("Jupiter", centerX-16*radius,centerY, Color.GREEN,radius,30000,-1);

        planets[6]=new Planet("Halley", centerX-19*radius,centerY,Color.MAGENTA,radius,35000,1);


        bitmaps=new Bitmap[7];
        for (int i=0;i<bitmaps.length;i++){
            bitmaps[i]=Bitmap.createBitmap(screenDimensions[0],screenDimensions[1],Bitmap.Config.ARGB_8888);
        }

        canvases=new Canvas[7];

        animations=new ObjectAnimator[7];

        imageViews=new ImageView[7] ;

        imageViews[0] =  findViewById(R.id.imageView0);
        imageViews[1] =  findViewById(R.id.imageView1);
        imageViews[2] =  findViewById(R.id.imageView2);
        imageViews[3] =  findViewById(R.id.imageView3);
        imageViews[4] =  findViewById(R.id.imageView4);
        imageViews[5] =  findViewById(R.id.imageView5);
        imageViews[6] =  findViewById(R.id.imageView6);

        drawPlanets();

        animateSystem();

        AnimatorSet orbitRotation = new AnimatorSet();
        orbitRotation.playTogether(animations);
        orbitRotation.start();
    }

    private int[] getDisplayDimensions() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        return new int[]{width, height};

    }
    private void drawEllipse(Canvas canvas, Paint paint, float centerX, float centerY, float radius) {
        paint.reset();
        setPaintAttributes(Color.LTGRAY, Paint.Style.STROKE,2, paint);
        canvas.drawCircle(centerX,centerY,radius,paint);

    }
    private void setPaintAttributes(int colour, Paint.Style stroke, int strokeWidth, Paint paint) {
        paint.reset();
        paint.setColor(colour);
        paint.setStyle(stroke);
        paint.setAntiAlias(true);
        paint.setTextSize(40f);
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);

    }
    public void drawPlanets () {
        for (int i = 0; i < planets.length; i++) {
            canvases[i] = new Canvas(bitmaps[i]);
            Paint paint = new Paint();


            if (i < planets.length - 1) {
                setPaintAttributes(planets[i].getColor(),Paint.Style.STROKE,1,paint);
                drawEllipse(canvases[i],paint,planets[0].getPositionX(),planets[0].getPositionY(),planets[i].getPositionX() - planets[i].getRadius());
                setPaintAttributes(planets[i].getColor(),Paint.Style.FILL,1,paint);
                canvases[i].drawCircle(planets[i].getPositionX(), planets[i].getPositionY(), planets[i].getRadius(), paint);

            }
            else {
                setPaintAttributes(planets[i].getColor(),Paint.Style.STROKE,5,paint);
                canvases[i].drawCircle(planets[i].getPositionX(), planets[i].getPositionY(), planets[i].getRadius(), paint);
                canvases[i].save();

                paint.reset();
                canvases[i].restore();

                Canvas canvasText = new Canvas(bitmaps[i]);
                paint.setColor(Color.GRAY);
                paint.setTextSize(40f);
                canvasText.drawText(planets[i].getName(), planets[i].getPositionX(), planets[i].getPositionY(), paint);
        }

        imageViews[i].setImageBitmap(bitmaps[i]);
    }
        }

    public void animateSystem()
    {
        for (int i = 0; i <planets.length; i++) {

            ObjectAnimator planetAnimation = ObjectAnimator.ofFloat(imageViews[i], "rotation", planets[i].getDirection() * 360);
            animations[i]=planetAnimation;
            animations[i].setDuration(planets[i].getSpeed());
            animations[i].setRepeatCount(ValueAnimator.INFINITE);
            animations[i].setRepeatMode(ValueAnimator.RESTART);
            animations[i].setInterpolator(new LinearInterpolator());


        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {

        super.onSaveInstanceState(savedInstanceState);

    }


}

