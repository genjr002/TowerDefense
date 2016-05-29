package com.unisa.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;


/**
 * Created by Nick on 19/05/2016.
 */
public class Save implements Serializable{

    public static SaveData sd;


    public static void save(){

        FileHandle file = Gdx.files.local("SaveGame.dat");
        OutputStream out = null;

        try{

            file.writeBytes(serialize(sd), false);

            //FileOutputStream fos = context.openFileOutput("Savedata.txt", Context.MODE_PRIVATE);
//            ObjectOutputStream out = new ObjectOutputStream(
//                    new FileOutputStream("assets/SaveData.txt")
//            );
//            out.writeObject(sd);
//            out.close();

        }catch (Exception e){
            e.printStackTrace();
            Gdx.app.exit();
        } finally {
            if (out != null) try{
                out.close();
            } catch(Exception e){

            }
            Gdx.app.log("Saved!", "pls work");
        }

    }

    public static void load(){

        if (Gdx.files.local("SaveGame.dat").exists()){
            Gdx.app.log("In load -- ", " file exists!..");
            try{
                FileHandle file = Gdx.files.local("SaveGame.dat");
                sd = (SaveData) deserialize(file.readBytes());
            } catch (Exception e){
                e.printStackTrace();
                Gdx.app.exit();
            }

        } else {
            Gdx.app.log("in load -- ", " file doesn't exist!..running init()");
            init();
            return;
        }
    }


//    public static boolean saveFileExists(){
//
//        boolean exists;
//
//        //File f = new File("SaveData.txt");
//
//        exists = Gdx.files.local("SaveData.txt").exists();
//        Gdx.app.log("does file exist?", "..." + exists);
//        if (!exists){
//            Gdx.app.log("no file", "creating..");
//            if (Gdx.files.isLocalStorageAvailable()){
//                Gdx.app.log("storage: ", " Avaliable!..");
//                FileHandle handle = Gdx.files.local("SaveData.txt");
//                handle.writeString("", false);
//                //String text = handle.readString();
//                //Gdx.app.log("text file string is: ", text);
//                exists = Gdx.files.local("SaveData.txt").exists();
//                if (exists){
//                    Gdx.app.log("File created!", " -- yay");
//                }
//            }else {
//                Gdx.app.log("storage:", " Unavailable..");
//            }
//
//        } else {
//            Gdx.app.log("File already exists!", "...");
//        }
//        exists = Gdx.files.local("SaveData.txt").exists();
//
//        return exists;
//    }

    public static void init(){
        sd = new SaveData();
        save();
    }

    public String toString(){

        String temep = "";

//        for (int x = 0; x < sd.listOfTypes.length; x++){
//            temep += sd.defenses.get(x).getName();
//            temep += "1";
//        }

        return temep;
    }

    private static byte[] serialize(Object ob) throws IOException{
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(b);
        os.writeObject(ob);
        return b.toByteArray();
    }

    private static Object deserialize(byte[] byteArray) throws IOException, ClassNotFoundException{
        ByteArrayInputStream b = new ByteArrayInputStream(byteArray);
        ObjectInputStream os = new ObjectInputStream(b);
        return os.readObject();
    }

//    public static void save(){
//
//
//        try{
//
//            //FileOutputStream fos = context.openFileOutput("Savedata.txt", Context.MODE_PRIVATE);
//            ObjectOutputStream out = new ObjectOutputStream(
//                    new FileOutputStream("assets/SaveData.txt")
//            );
//            out.writeObject(sd);
//            out.close();
//
//        }catch (Exception e) {
//            e.printStackTrace();
//            Gdx.app.exit();
//        }
//
//    }

//    public static void load(){
//
//        try{
//            if (!saveFileExists()){
//                Gdx.app.log("in load -- ", " file doesn't exist!..running init()");
//                init();
//                return;
//            } else {
//                Gdx.app.log("In load -- ", " file exists!..");
//            }
//            String path = Gdx.files.getLocalStoragePath();
//            Gdx.app.log("local storage path is: ", path);
//            Gdx.app.log("local storage path ---- does the file exist here?: ", " " + Gdx.files.local("SaveData.txt").exists());
//            ObjectInputStream in = new ObjectInputStream(
//                    new FileInputStream("/data/data/com.unisa.game/files/SaveData.txt")
//            );
//            sd = (SaveData) in.readObject();
//            in.close();
//
//        }catch(Exception e){
//            e.printStackTrace();
//            Gdx.app.exit();
//        }
//
//    }
}
