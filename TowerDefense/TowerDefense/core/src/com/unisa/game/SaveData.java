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

    //public ArrayList<ArrayList<Integer, Rectangle>> defenses = new ArrayList<ArrayList<Integer, Rectangle>>();
    public int[] listOfTypes;
    public Rectangle[] listOfDefBounds;
    public float[] spriteXPos;
    public float[] spriteYPos;

    //public List<int[][]> spritePos;

    //public Map<Integer, Rectangle> e
    //public ArrayList<ArrayList< Rectangle>> defenses;
    //public List<Object> defenses = new ArrayList <Object>();

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

//        ArrayList<Defense> loadedDefenses = new ArrayList<Defense>();
//
//        for (int x = 0; x < defenses.size(); x++){
//
//            HashMap<Integer, Rectangle> test = defenses.get(x);
//            Defense temp = new Defense(test.);
//
//            test.put(temp.getType(), temp.getDefenseBounds());
//            loadedDefenses.add(x, new Defense(defenses.get(x).));
//        }

//        return defenses;
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

//            Defense temp = list.get(x);
//            HashMap<Integer, Rectangle> test = new HashMap<Integer, Rectangle>();
//            test.put(temp.getType(), temp.getDefenseBounds());
//            defenses.add(x, test);
        }

        //this.defenses = list;
    }

    public void init(){
        Defense test1 = new Defense(1);
        test1.setDefenseBounds(new Rectangle(200,200, 20, 20));

        Defense test2 = new Defense(1);
        test2.setDefenseBounds(new Rectangle(400,400, 50, 50));

        GameScreen.savedDefenses.add(0, test1);
        GameScreen.savedDefenses.add(0, test2);

        setDefenses(GameScreen.savedDefenses);

//      defenses.put(1,new Rectangle(200,200, 20, 20));
//      defenses.put(2,new Rectangle(200,200, 70, 70));
        //defenses = new ArrayList<Defense>(2);
        //defenses.add(new Defense(1));
        //defenses.add((new Defense(2)));
    }


    public String toString(){

        String temep = "";

//        for (int x = 0; x < defenses.size(); x++){
//            temep += defenses.
//            temep += "1";
//        }

        return temep;
    }

}
