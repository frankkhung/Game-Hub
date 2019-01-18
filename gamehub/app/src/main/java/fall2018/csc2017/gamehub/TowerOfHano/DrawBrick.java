package fall2018.csc2017.gamehub.TowerOfHano;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import java.util.ArrayList;

public class DrawBrick extends View {
    private int brickId;
    private ArrayList<Integer> bricks;
    private int gameSize;

    public DrawBrick(Context context, int brickId, ArrayList<Integer> bricks, int gameSize){
        super(context);
        this.brickId = brickId;
        this.bricks = bricks;
        this.gameSize = gameSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bricks.get(brickId-1) == 0){
            return;
        }

        // configure painter pen
        Paint p = new Paint();
        p.setColor(Color.rgb(150,150,150));
        p.setStyle(Paint.Style.FILL);

        int width = DrawRingsAndBackground.LARGEST_WIDTH-(gameSize-bricks.get(brickId-1))*(DrawRingsAndBackground.LARGEST_WIDTH-DrawRingsAndBackground.SMALLEST_WIDTH )/(gameSize-1);
        canvas.drawRect(DrawRingsAndBackground.FIX_MID-width/2, 0, DrawRingsAndBackground.FIX_MID+width/2, DrawRingsAndBackground.HEIGHT, p);
    }
}
