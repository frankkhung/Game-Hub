package fall2018.csc2017.gamehub.MatchingGame;

import android.support.annotation.NonNull;

import java.io.Serializable;

import fall2018.csc2017.gamehub.Board;
import fall2018.csc2017.gamehub.R;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Card implements Comparable<Card>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;
    private int display;

    /**
     * The unique id.
     */
    private int id;

    private boolean open;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the display id.
     *
     * @return the display id
     */
    public int getDisplay() {
        return display;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A Tile with id and background. The background may not have a corresponding image.
     *
     * @param id         the id
     * @param background the background
     */
    public Card(int id, int background) {
        this.id = id;
        this.background = background;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId
     */
    public Card(int backgroundId) {
        open = false;
        id = backgroundId + 1;
        // This looks so ugly.
        switch (id) {
            case 1:
                background = R.drawable.tile_1;
                break;
            case 2:
                background = R.drawable.tile_1;
                break;
            case 3:
                background = R.drawable.tile_2;
                break;
            case 4:
                background = R.drawable.tile_2;
                break;
            case 5:
                background = R.drawable.tile_3;
                break;
            case 6:
                background = R.drawable.tile_3;
                break;
            case 7:
                background = R.drawable.tile_4;
                break;
            case 8:
                background = R.drawable.tile_4;
                break;
            case 9:
                background = R.drawable.tile_5;
                break;
            case 10:
                background = R.drawable.tile_5;
                break;
            case 11:
                background = R.drawable.tile_6;
                break;
            case 12:
                background = R.drawable.tile_6;
                break;
            case 13:
                background = R.drawable.tile_7;
                break;
            case 14:
                background = R.drawable.tile_7;
                break;
            case 15:
                background = R.drawable.tile_8;
                break;
            case 16:
                background = R.drawable.tile_8;
                break;
            case 17:
                background = R.drawable.tile_9;
                break;
            case 18:
                background = R.drawable.tile_9;
                break;
            case 19:
                background = R.drawable.tile_10;
                break;
            case 20:
                background = R.drawable.tile_10;
                break;
            case 21:
                background = R.drawable.tile_11;
                break;
            case 22:
                background = R.drawable.tile_11;
                break;
            case 23:
                background = R.drawable.tile_12;
                break;
            case 24:
                background = R.drawable.tile_12;
                break;
            case 25:
                background = R.drawable.tile_13;
                break;
            case 26:
                background = R.drawable.tile_13;
                break;
            case 27:
                background = R.drawable.tile_14;
                break;
            case 28:
                background = R.drawable.tile_14;
                break;
            case 29:
                background = R.drawable.tile_15;
                break;
            case 30:
                background = R.drawable.tile_15;
                break;
            case 31:
                background = R.drawable.tile_16;
                break;
            case 32:
                background = R.drawable.tile_16;
                break;
            case 33:
                background = R.drawable.tile_17;
                break;
            case 34:
                background = R.drawable.tile_17;
                break;
            case 35:
                background = R.drawable.tile_18;
                break;
            case 36:
                background = R.drawable.tile_18;
                break;
        }
        display = R.drawable.card_back;
        }

    /**
     * the getter method for the state weather the card is open or not
     *
     * @return the boolean state
     */
    boolean getState(){
        return open;
    }

    @Override
    public int compareTo(@NonNull Card o) {
        return o.id - this.id;
    }

    /**
     * The method to flip the card to the front
     */
    void flipFront(){
        display = background;
        open = true;
    }

    /**
     * The method to flip the card to the card back
     */
    void flipBack(){
        open = false;
        display = R.drawable.card_back;
    }
}





