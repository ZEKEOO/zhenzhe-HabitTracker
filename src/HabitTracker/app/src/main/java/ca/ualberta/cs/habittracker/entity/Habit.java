package ca.ualberta.cs.habittracker.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZEKE_XU on 9/25/16.
 */

public class Habit implements Serializable {

    private int id;
    private Date date;
    private String name;
    private ArrayList<WEEK> weeks;

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int count = 0;
    private Boolean isValid = true;

    public Habit(String name, Date date) {
        this.date = date;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<WEEK> getWeeks() {
        return weeks;
    }

    public void setWeeks(ArrayList<WEEK> weeks) {
        this.weeks = weeks;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
