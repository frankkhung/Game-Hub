package fall2018.csc2017.gamehub.TowerOfHano;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

public class DrawRingsAndBackground extends View {
    // background configure
    public static final int FIX_WIDTH = 40;
    final public static int FIX_MID = 165;
    final public static int FIX_BOTTOM = 365;

    // rings configure
    final public static int SMALLEST_WIDTH = 80;
    final public static int LARGEST_WIDTH = 300;
    final public static int HEIGHT = 50;

    private ArrayList<Integer> tower;
    private int gameSize;

    //constructor
    public DrawRingsAndBackground(Context context, ArrayList<Integer> tower,int gameSize){
        super(context);
        this.tower = tower;
        this.gameSize = gameSize;
    }

    public int getWidth(int index){
        return LARGEST_WIDTH - (gameSize-tower.get(index))*(LARGEST_WIDTH-SMALLEST_WIDTH)/(gameSize-1);
}

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // setting the pen
        Paint p = new Paint();
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);

        // draw background
        canvas.drawRect(0, FIX_BOTTOM, FIX_MID*2, FIX_BOTTOM+FIX_WIDTH, p);
        canvas.drawRect(FIX_MID-FIX_WIDTH/2, 0, FIX_MID+FIX_WIDTH/2, FIX_BOTTOM, p);


        if (tower.size() == 0){
            return;
        }

        // resetting pen
        p.setColor(Color.rgb(150,150,150));
        p.setStyle(Paint.Style.FILL);

        // draw rings
        for(int i = 0; i < tower.size(); i++){
            canvas.drawRect(FIX_MID-getWidth(i)/2, FIX_BOTTOM-HEIGHT*(i+1), FIX_MID+getWidth(i)/2, FIX_BOTTOM-i*HEIGHT, p);
        }
    }
}