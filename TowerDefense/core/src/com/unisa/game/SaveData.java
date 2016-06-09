package com.unisa.game;

import com.badlogic.gdx.math.Rectangle;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Nick on 19/05/2016.
 */
public class SaveData implements Serializable {

    private static final long serialVersionUID = 1;


    public int[] listOfTypes;
    public Rectangle[] listOfDefBounds;
    public float[] spriteXPos;
    public float[] spriteYPos;
    public int[][] gridOccupiedCells;


    public SaveData(){
        init();
    }

    public ArrayList<Defense> getDefenseList(){

        ArrayList<Defense> theDefenses = new ArrayList<Defense>();

        for (int x = 0; x < listOfTypes.length; x++){
            Defense temp = new Defense(listOfTypes[x]);
            temp.setDefenseBounds(listOfDefBounds[x]);
            temp.getSprite().setPosition(spriteXPos[x], spriteYPos[x]);
            theDefenses.add(temp);
        }

        return theDefenses;

    }

    public void setDefenses(ArrayList<Defense> list){

        listOfTypes = new int[list.size()];
        listOfDefBounds = new Rectangle[list.size()];
        spriteXPos = new float[list.size()];
        spriteYPos = new float[list.size()];

        for (int x = 0; x < list.size(); x++){


            listOfTypes[x] = list.get(x).getType();


            listOfDefBounds[x] = list.get(x).getDefenseBounds();


            spriteXPos[x] = list.get(x).getSprite().getX();


            spriteYPos[x] = list.get(x).getSprite().getY();


        }

    }

    /**
     * Rememberes which cells in the screenGrid are occupied
     * @param theGrid
     */
    public void setScreenGrid(Cell[][] theGrid){

        //screenGrid = new Cell[theGrid[0].length][theGrid[1].length];
        //screenGrid = new Cell[15][10];
        gridOccupiedCells = new int[GameScreen.MAX_CELLS_X][GameScreen.MAX_CELLS_Y];

        for (int r = 0; r < GameScreen.MAX_CELLS_X; r++){
            for (int c = 0; c < GameScreen.MAX_CELLS_Y; c++){
                //screenGrid[r][c] = theGrid[r][c];

                //if the cell is occupied (true) set to 1
                if (theGrid[r][c].isCellOccupied()){
                    gridOccupiedCells[r][c] = 1;
                } else {
                    gridOccupiedCells[r][c] = 0;
                }
            }
        }

    }

    /**
     * Determines from gridOccupiedCells which cells were occupied
     * in the save. Returns a
     * @return
     */
    public int[][] getScreenGrid(){


        return gridOccupiedCells;
    }

    public void init(){
        Defense test1 = new Defense(1);
        test1.setDefenseBounds(new Rectangle(200,200, 20, 20));

        Defense test2 = new Defense(1);
        test2.setDefenseBounds(new Rectangle(400,400, 50, 50));

        GameScreen.savedDefenses.add(0, test1);
        GameScreen.savedDefenses.add(0, test2);

        setDefenses(GameScreen.savedDefenses);


    }


    public String toString(){

        String temep = "";

        return temep;
    }

}
