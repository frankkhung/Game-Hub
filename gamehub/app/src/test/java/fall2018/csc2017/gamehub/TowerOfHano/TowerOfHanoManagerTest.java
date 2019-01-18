package fall2018.csc2017.gamehub.TowerOfHano;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TowerOfHanoManagerTest {


    @Test
    public void setGameSize3() {
        TowerOfHanoManager brickTest = new TowerOfHanoManager(3);
        ArrayList<Integer> expected = new ArrayList();
        expected.add(3);
        expected.add(2);
        expected.add(1);
        assertEquals(expected, brickTest.getTower1());
    }

    @Test
    public void setGameSize4() {
        TowerOfHanoManager brickTest = new TowerOfHanoManager(4);
        ArrayList<Integer> expected = new ArrayList();
        expected.add(4);
        expected.add(3);
        expected.add(2);
        expected.add(1);
        assertEquals(expected, brickTest.getTower1());
    }

    @Test
    public void setGameSize5() {
        TowerOfHanoManager brickTest = new TowerOfHanoManager(5);
        ArrayList<Integer> expected = new ArrayList();
        expected.add(5);
        expected.add(4);
        expected.add(3);
        expected.add(2);
        expected.add(1);
        assertEquals(expected, brickTest.getTower1());
    }

    @Test
    public void getSizeOfGame() {
        TowerOfHanoManager brickTest = new TowerOfHanoManager(3);
        assertEquals(3, brickTest.getSizeOfGame());
        TowerOfHanoManager brickTest1 = new TowerOfHanoManager(4);
        assertEquals(4, brickTest1.getSizeOfGame());
        TowerOfHanoManager brickTest2 = new TowerOfHanoManager(5);
        assertEquals(5, brickTest2.getSizeOfGame());
    }


    @Test
    public void getTower2() {
        TowerOfHanoManager brickTest = new TowerOfHanoManager(5);
        ArrayList<Integer> expected = new ArrayList();
        expected.add(2);
        expected.add(1);
        brickTest.tower1Pop();
        brickTest.tower3Push(1);
        brickTest.tower1Pop();
        brickTest.tower2Push(1);
        brickTest.tower3Pop();
        brickTest.tower2Push(3);
        assertEquals(expected, brickTest.getTower2());
    }

    @Test
    public void getTower3() {
        TowerOfHanoManager brickTest = new TowerOfHanoManager(5);
        ArrayList<Integer> expected = new ArrayList();
        expected.add(2);
        expected.add(1);
        brickTest.tower1Pop();
        brickTest.tower2Push(1);
        brickTest.tower1Pop();
        brickTest.tower3Push(1);
        brickTest.tower2Pop();
        brickTest.tower3Push(2);
        assertEquals(5, brickTest.getSizeOfGame());
        assertEquals(expected, brickTest.getTower3());
    }


    @Test
    public void getStep() {
        TowerOfHanoManager brickTest = new TowerOfHanoManager(5);
        brickTest.addoneStep();
        brickTest.addoneStep();
        assertEquals(2, brickTest.getStep());
    }

    // ** This is making sure that 3 consecutive undo moves functions.
    @Test
    public void undo() {
        TowerOfHanoManager brickTest = new TowerOfHanoManager(5);
        ArrayList<Integer> expectedTower2 = new ArrayList();
        ArrayList<Integer> expectedTowerAfterUndo = new ArrayList();
        expectedTower2.add(3);
        expectedTower2.add(1);
        expectedTowerAfterUndo.add(1);
        brickTest.tower1Pop();
        brickTest.addStep(1);
        brickTest.tower2Push(1);
        brickTest.addStep(2);
        brickTest.tower1Pop();
        brickTest.addStep(1);
        brickTest.tower3Push(1);
        brickTest.addStep(3);
        brickTest.tower2Pop();
        brickTest.addStep(2);
        brickTest.tower3Push(2);
        brickTest.addStep(3);
        brickTest.tower1Pop();
        brickTest.addStep(1);
        brickTest.tower2Push(1);
        brickTest.addStep(2);
        brickTest.tower3Pop();
        brickTest.addStep(3);
        brickTest.tower2Push(3);
        brickTest.addStep(2);
        assertEquals(expectedTower2, brickTest.getTower2());
        brickTest.undo();
        brickTest.undo();
        brickTest.undo();
        assertEquals(expectedTowerAfterUndo, brickTest.getTower2());
        // Attempting a fourth undo, but final TowerOfHanoManager should not be
        // be different to that in Undo1 from above.
        brickTest.undo();
        assertEquals(expectedTowerAfterUndo, brickTest.getTower2());

        // Attempting to use undo when brick has not been placed yet
        TowerOfHanoManager hanoi = new TowerOfHanoManager(3);
        hanoi.tower1Pop();
        hanoi.addStep(1);
        hanoi.tower2Push(1);
        hanoi.addStep(2);
        hanoi.tower1Pop();
        hanoi.addStep(1);
        assertEquals("odd size of stack", hanoi.undo());

        // Attempting to use undo when there stepStep size is 0
        TowerOfHanoManager Bangkok = new TowerOfHanoManager(4);
        Bangkok.tower1Pop();
        Bangkok.addStep(1);
        Bangkok.tower2Push(1);
        Bangkok.addStep(2);
        Bangkok.tower1Pop();
        Bangkok.addStep(1);
        Bangkok.tower3Push(1);
        Bangkok.addStep(3);
        Bangkok.tower2Pop();
        Bangkok.addStep(2);
        Bangkok.tower3Push(2);
        Bangkok.addStep(3);
        Bangkok.tower1Pop();
        Bangkok.addStep(1);
        Bangkok.tower2Push(1);
        Bangkok.tower2Push(1);
        Bangkok.addStep(2);
        Bangkok.undo();
        Bangkok.undo();
        Bangkok.undo();
        assertEquals("stack is empty", Bangkok.undo());

    }

    @Test
    public void SolveTOMHThree() {
        TowerOfHanoManager brickTest = new TowerOfHanoManager(3);
        brickTest.SolveTOHM(3, 1, 3, 2);
        ArrayList<Integer> expected1 = new ArrayList();
        ArrayList<Integer> expected2 = new ArrayList();
        ArrayList<Integer> expected3 = new ArrayList();
        expected3.add(3);
        expected3.add(2);
        expected3.add(1);

        assertEquals(expected1, brickTest.getTower1());
        assertEquals(expected2, brickTest.getTower2());
        assertEquals(expected3, brickTest.getTower3());
        System.out.println(brickTest.getStep());
    }


        @Test
        public void SolveTOMHFour() {
            TowerOfHanoManager brickTest = new TowerOfHanoManager(4);
            brickTest.SolveTOHM(4, 1, 3, 2);
            ArrayList<Integer> expected1 = new ArrayList();
            ArrayList<Integer> expected2 = new ArrayList();
            ArrayList<Integer> expected3 = new ArrayList();
            expected3.add(4);
            expected3.add(3);
            expected3.add(2);
            expected3.add(1);

            assertEquals(expected1, brickTest.getTower1());
            assertEquals(expected2, brickTest.getTower2());
            assertEquals(expected3, brickTest.getTower3());
            System.out.println(brickTest.getStep());
    }

        @Test
        public void SolveTOMHFive() {
            TowerOfHanoManager brickTest = new TowerOfHanoManager(5);
            brickTest.SolveTOHM(5, 1, 3, 2);
            ArrayList<Integer> expected1 = new ArrayList();
            ArrayList<Integer> expected2 = new ArrayList();
            ArrayList<Integer> expected3 = new ArrayList();
            expected3.add(5);
            expected3.add(4);
            expected3.add(3);
            expected3.add(2);
            expected3.add(1);

            assertEquals(expected1, brickTest.getTower1());
            assertEquals(expected2, brickTest.getTower2());
            assertEquals(expected3, brickTest.getTower3());
            System.out.println(brickTest.getStep());
        }

    @Test
    public void checkGameOver3() {

        TowerOfHanoManager brickTest = new TowerOfHanoManager(3);
        brickTest.SolveTOHM(3,1,3,2);
        boolean SolveOrNot = brickTest.checkGameOver();
        assertEquals(true, SolveOrNot);

    }

    @Test
    public void checkGameOver4() {

        TowerOfHanoManager brickTest = new TowerOfHanoManager(4);
        brickTest.SolveTOHM(4,1,3,2);
        boolean SolveOrNot = brickTest.checkGameOver();
        assertEquals(true, SolveOrNot);

    }

    @Test
    public void checkGameOver5() {

        TowerOfHanoManager brickTest = new TowerOfHanoManager(5);
        brickTest.SolveTOHM(5,1,3,2);
        boolean SolveOrNot = brickTest.checkGameOver();
        assertEquals(true, SolveOrNot);

    }


    TowerOfHanoManager EmptyTower  = new TowerOfHanoManager(3);

    // line 100
    @Test
    public void tower1popTest(){
        EmptyTower.tower1Pop();
        EmptyTower.tower3Push(1);
        EmptyTower.tower1Pop();
        EmptyTower.tower2Push(1);
        EmptyTower.tower3Pop();
        EmptyTower.tower2Push(3);
        EmptyTower.tower1Pop();
        EmptyTower.tower3Push(1);
        assertFalse(EmptyTower.tower1Pop());
    }

    // line 118
    @Test
    public void tower2popTest() {
        assertFalse(EmptyTower.tower2Pop());
    }

    // line 131
    @Test
    public void tower3PopTest(){
        assertFalse(EmptyTower.tower3Pop());

    }

    // line 144
    @Test
    public void tower3PushTest(){
        TowerOfHanoManager EmptyHano1  = new TowerOfHanoManager(3);
        EmptyHano1.tower1Pop();
        EmptyHano1.tower3Push(1);
        EmptyHano1.tower1Pop();
        EmptyHano1.tower2Push(1);
        EmptyHano1.tower1Pop();
        assertFalse(EmptyHano1.tower3Push(1));
    }

    // line 157
    @Test
    public void tower2PushTest(){
        TowerOfHanoManager EmptyHano  = new TowerOfHanoManager(3);
        EmptyHano.tower1Pop();
        EmptyHano.tower3Push(1);
        EmptyHano.tower1Pop();
        EmptyHano.tower2Push(1);
        EmptyHano.tower1Pop();
        assertFalse(EmptyHano.tower2Push(1));
    }
    @Test
    public void getBricks(){
        TowerOfHanoManager hanoi  = new TowerOfHanoManager(3);
        ArrayList<Integer> testingBricks = new ArrayList(3);
        testingBricks.add(0);
        testingBricks.add(0);
        testingBricks.add(0);
        assertArrayEquals(hanoi.getBricks().toArray(),testingBricks.toArray());

    }

}