package fall2018.csc2017.gamehub.TowerOfHano;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class TowerOfHanoManager implements Serializable {
    private ArrayList<Integer> tower1;
    private ArrayList<Integer> tower2;
    private ArrayList<Integer> tower3;
    private ArrayList<Integer> bricks;
    private int sizeOfGame;
    private ArrayList<Integer> stepStack;
    private int step;

    /**
     * init
     */
    TowerOfHanoManager(int sizeOfGame){
        tower1 = new ArrayList();
        for(int i = sizeOfGame; i>0; i--){
            tower1.add(i);
        }
        tower2 = new ArrayList();
        tower3 = new ArrayList();
        bricks = new ArrayList();
        stepStack = new ArrayList<>();
        bricks.add(0);
        bricks.add(0);
        bricks.add(0);
        step = 0;
        this.sizeOfGame = sizeOfGame;
    }

    /**
     * return brack
     * @return brack
     */
    public ArrayList<Integer> getBricks() {
        return bricks;
    }

    /**
     * get the size of getSizeofGame
     * @return the size of the game
     */
    public int getSizeOfGame() { return sizeOfGame; }

    /**
     * get tower1
     * @return tower1
     */
    public ArrayList<Integer> getTower1() {
        return tower1;
    }

    /**
     * get tower2
     * @return tower2
     */
    public ArrayList<Integer> getTower2() {
        return tower2;
    }

    /**
     * get tower3
     * @return tower3
     */
    public ArrayList<Integer> getTower3() {
        return tower3;
    }


    /**
     * add step
     */
    public void addoneStep(){
        step += 1;
    }



    /**
     * get step
     */
    public int getStep(){
        return step;
    }

    /**
     * pop last ring out of tower1 and send it to brick0
     * @reutrn success or not
     */
    public boolean tower1Pop(){
        if (tower1.size()>0 && bricks.get(0) == 0){
            bricks.set(0,tower1.get(tower1.size()-1));
            tower1.remove(tower1.size()-1);
            return true;
        }
        return false;
    }

    /**
     * pop last ring out of tower2 and send it to brick1
     * @reutrn success or not
     */
    public boolean tower2Pop(){
        if (tower2.size()>0 && bricks.get(1) == 0){
            bricks.set(1,tower2.get(tower2.size()-1));
            tower2.remove(tower2.size()-1);
            return true;
        }
        return false;
    }

    /**
     * pop last ring out of tower3 and send it to brick2
     * @reutrn success or not
     */
    public boolean tower3Pop(){
        if (tower3.size()>0 && bricks.get(2) == 0){
            bricks.set(2,tower3.get(tower3.size()-1));
            tower3.remove(tower3.size()-1);
            return true;
        }
        return false;
    }

    /**
     * add a ring into tower1
     * @return success or not
     */
    public boolean tower1Push(int brickId){
        if (tower1.size() == 0 || bricks.get(brickId-1) < tower1.get(tower1.size()-1)){
            tower1.add(bricks.get(brickId-1));
            bricks.set(brickId-1,0);
            return true;
        }
        return false;
    }

    /**
     * add a ring into tower2
     * @return success or not
     */
    public boolean tower2Push(int brickId){
        if (tower2.size() == 0 || bricks.get(brickId-1) < tower2.get(tower2.size()-1)){
            tower2.add(bricks.get(brickId-1));
            bricks.set(brickId-1,0);
            return true;
        }
        return false;
    }

    /**
     * add a ring into tower3
     * @return success or not
     */
    public boolean tower3Push(int brickId){
        if (tower3.size() == 0 || bricks.get(brickId-1) < tower3.get(tower3.size()-1)) {
            tower3.add(bricks.get(brickId - 1));
            bricks.set(brickId - 1, 0);
            return true;
        }
        return false;
    }

    /**
     * add step into stepStack
     */
    public void addStep(int a){
        if (stepStack.size() == 7){
            stepStack.remove(0);
            stepStack.remove(0);
        }
            stepStack.add(a);
    }

    /**
     * implement the undo
     * @return success or not
     */
    public String undo(){
        if (stepStack.size() == 0){
            System.out.println("can't undo anymore!");
            return "stack is empty";
        }
        else if (stepStack.size()%2 != 0){
            System.out.println("you have to land the ring first");
            return "odd size of stack";
        }
        else{
            int a = stepStack.get(stepStack.size()-1);
            stepStack.remove(stepStack.size()-1);
            int b = stepStack.get(stepStack.size()-1);
            stepStack.remove(stepStack.size()-1);
            moveRing(a,b);
            return "success";
        }
    }

    /**
     * move the ring from given tower
     * @param a: tower where ring pop out from
     * @param b: tower where ring be pushed into
     */
    public void moveRing(int a,int b){
        //pop
        if (a == 1){ tower1Pop(); }
        else if (a == 2){ tower2Pop(); }
        else{ tower3Pop(); }

        // push
        if (b == 1){ tower1Push(a); }
        else if (b == 2){ tower2Push(a); }
        else{ tower3Push(a); }
    }


    /**
     * tower of hanoi solver
     * @param sizeOfGame: game size
     * @param startTower: tower with rings at beginning moment
     * @param finalTower: tower with rings at ending momnet
     * @param midTower: middle tower
     */
    public void SolveTOHM(int sizeOfGame, int startTower, int finalTower, int midTower) {
        if (sizeOfGame == 1) {
            // INSTEAD WE WANT TO IMPLEMENT PHYSICAL MOVES.
            System.out.println("Disk 1 from tower " + Integer.toString(startTower) + " to tower " + Integer.toString(finalTower));
            this.moveRing(startTower, finalTower);
            this.addoneStep();

        }
        else {
            SolveTOHM(sizeOfGame - 1, startTower, midTower, finalTower);
            System.out.println("Disk " + Integer.toString(sizeOfGame) + " from tower " + Integer.toString(startTower)  + " to tower " + Integer.toString(midTower));
            this.moveRing(startTower, finalTower);
            this.addoneStep();
            SolveTOHM( sizeOfGame - 1, midTower, finalTower, startTower);
        }
    }

    /**
     * the check if game is over
     * @return boolean value indicate if game is over
     */
    public boolean checkGameOver(){
        if (tower3.size() != sizeOfGame){ return false;}
        for(int i = tower3.size()-1; i >= 0; i--){
            if (tower3.get(i) != tower3.size()-i){ return false;}
        }
        return true;
    }
}
