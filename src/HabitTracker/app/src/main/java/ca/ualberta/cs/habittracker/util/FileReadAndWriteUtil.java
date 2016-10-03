package ca.ualberta.cs.habittracker.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import ca.ualberta.cs.habittracker.entity.Habit;

/**
 * Created by ZEKE_XU on 27/09/2016.
 */

public class FileReadAndWriteUtil {

    /* File path */
    private static final String DATA_SAVE_PATH = "/data/data/ca.ualberta.cs.habittracker/files/habit_data.txt";

    private FileInputStream fis;
    private BufferedReader reader;
    private FileOutputStream fos;
    private OutputStreamWriter writer;

    /* To see if the file exits */
    public Boolean checkDataFileExists() {
        File file = new File(DATA_SAVE_PATH);
        return file.exists();
    }

    /* Write */
    public Boolean writeData(ArrayList<Habit> habits) {
        try {
            fos = new FileOutputStream(DATA_SAVE_PATH, false);
            writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(habits, writer);
            writer.flush();

            fos.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /* Read */
    public ArrayList<Habit> readData() {
        ArrayList<Habit> habits = null;
        try {
            fis = new FileInputStream(DATA_SAVE_PATH);
            reader = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            //Code taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt Sept.22,2016
            Type listType = new TypeToken<ArrayList<Habit>>(){}.getType();
            habits = gson.fromJson(reader, listType);

            fis.close();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return habits;
    }
}
