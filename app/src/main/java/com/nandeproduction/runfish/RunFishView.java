package com.nandeproduction.runfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class RunFishView extends View {

    //private Bitmap fish;
    //Save fish type to this array
    private Bitmap fish[] = new Bitmap[2];
    //Background
    private Bitmap background;
    //Score Text for Print Score
    private Paint score = new Paint();
    //Life Text for Print Score
    private Paint lifeText = new Paint();
    //Score variable
    private int marks;
    //Heart Life
    private Bitmap life[] = new Bitmap[2];
    //Fish position and speed
    private int fishX = 10,fishY,fishSpeed;
    //Screen width & height
    private int canvasWidth , canvasHeight;
    //Touch the screen
    private boolean touch = false;
    //Yellow Ball
    private int yellowX, yellowY, yellowSpeed = 10;
    private Paint yellowBall = new Paint();
    //Green Ball
    private int greenX, greenY, greenSpeed = 20;
    private Paint greenBall = new Paint();
    //Red Ball
    private int redX, redY, redSpeed = 25;
    private Paint redBall = new Paint();
    //Life count of fish
    private int lifeCount;

    public RunFishView(Context context) {
        super(context);
         //fish = BitmapFactory.decodeResource(getResources(), R.drawable.fish_up);
         fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish_run);
         fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish_up);

         //dangourousFish = BitmapFactory.decodeResource(getResources(),R.drawable.jellyfish);

         background = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);

         life[0] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_red_resize);
         life[1] = BitmapFactory.decodeResource(getResources(),R.drawable.heart_white_resize);

         score.setColor(Color.WHITE);
         score.setTextSize(50);
         score.setTypeface(Typeface.DEFAULT);
         score.setAntiAlias(true);

        //lifeText.setColor(Color.WHITE);
        //lifeText.setTextSize(50);
        //lifeText.setTypeface(Typeface.DEFAULT);
        //lifeText.setAntiAlias(true);

         fishY = 550;
         marks = 0;
         lifeCount = 3;

         yellowBall.setColor(Color.YELLOW);
         yellowBall.setAntiAlias(false);

         greenBall.setColor(Color.GREEN);
         greenBall.setAntiAlias(false);

        redBall.setColor(Color.RED);
        redBall.setAntiAlias(false);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Get screen width & height
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();

        //Draw background
        canvas.drawBitmap(background,0,0,null);
        //canvas.drawBitmap(fish,0,0,null);

        //Draw Dangerous fish
        //canvas.drawBitmap(dangourousFish,0,0,null);

        //Initialized min & max fish Y axis range.
        int minFishY = fish[0].getHeight();
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        //Set fish Y axis position
        fishY = fishY + fishSpeed;

        if (fishY < minFishY){
            fishY = minFishY;
        }
        if (fishY > maxFishY){
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;

        //If touch you screen the fish will move to up,else move to down
        if (touch){
            canvas.drawBitmap(fish[1],fishX,fishY, null);
            touch = false;
        }else {
            canvas.drawBitmap(fish[0],fishX,fishY, null);
        }

        //Draw Yellow Ball Start
        yellowX = yellowX - yellowSpeed;

        //Set score when catch yellow ball
        if (hitBallCenter(yellowX,yellowY)){
            marks = marks + 10;
            yellowX = -100;
        }

        if (yellowX < 0){
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX,yellowY,10,yellowBall);
        //Draw Yellow Ball End

        //Draw Green ball Start
        greenX = greenX - greenSpeed;
        //Set score when catch green ball
        if (hitBallCenter(greenX,greenY)){
            marks = marks + 20;
            greenX = -100;
        }

        if(marks > 100){
            yellowSpeed = 13;
            greenSpeed = 23;
            redSpeed = 27;
        }
        if(marks > 300){
            yellowSpeed = 16;
            greenSpeed = 25;
            redSpeed = 28;
        }
        if(marks > 600){
            yellowSpeed = 19;
            greenSpeed = 28;
            redSpeed = 32;
        }
        if(marks > 900){
            yellowSpeed = 20;
            greenSpeed = 30;
            redSpeed = 34;
        }
        if(marks > 1200){
            yellowSpeed = 22;
            greenSpeed = 32;
            redSpeed = 36;
        }
        if(marks > 1500){
            yellowSpeed = 24;
            greenSpeed = 34;
            redSpeed = 36;
        }
        if(marks > 1800){
            yellowSpeed = 26;
            greenSpeed = 36;
            redSpeed = 38;
        }
        if(marks > 2000){
            yellowSpeed = 28;
            greenSpeed = 38;
            redSpeed = 40;
        }
        if(marks > 2100){
            yellowSpeed = 30;
            greenSpeed = 40;
            redSpeed = 42;
        }

        if (greenX < 0){
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX,greenY,20,greenBall);
        //Draw Green ball End

        //Draw Red ball Start
        redX = redX - redSpeed;
        //Set score when catch green ball
        if (hitBallCenter(redX,redY)){
            //marks = marks + 20;
            redX = -100;
            lifeCount--;
            if (lifeCount == 0) {
                Toast.makeText(getContext(), "Game Over.!", Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(),RunFishGameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                //Pass Score to Game Over View to Display
                gameOverIntent.putExtra("score",marks);

                //Start Game over View
                getContext().startActivity(gameOverIntent);
            }
        }

        if (redX < 0){
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(redX,redY,30,redBall);
        //Draw Red ball End

        //Draw Score Area
        canvas.drawText("Score : " + marks,20,60,score);

        //Life Text
        //canvas.drawText("Life ",20,60,lifeText);

        //Draw 3 Heart Life
        //canvas.drawBitmap(life[0],700,10,null);
        //canvas.drawBitmap(life[0],780,10,null);
        //canvas.drawBitmap(life[0],860,10,null);
        for (int i = 0; i < 3; i++){
            int x = (int) ((canvasWidth/2) + life[0].getWidth() * 1.5 * i);
            int y = 10;
            if (i < lifeCount){
                canvas.drawBitmap(life[0],x,y,null);
            }else{
                canvas.drawBitmap(life[1],x,y,null);
            }
        }

    }

    public boolean hitBallCenter(int x, int y){
        if (fishX < x && x < fishX + fish[0].getWidth() && fishY < y && y < fishY + fish[0].getHeight() ){
            return  true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;
            fishSpeed = -22;
        }
        return true;
    }
}
