package com.example.attendanceapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Session {

    private int ID ;

    private ArrayList<Student> students ;
    private String subject;
    private String date;

    public Session () {
        students = new ArrayList<>();
    }

    public int getID (){     // GETTER
        return ID;

    }

    public void setID (int ID){     // SETTER
        this.ID = ID ;
    }

    public ArrayList<Student> getStudents {
        return students;
    }

    public void setStudents (ArrayList<Student> students){
        this.students = students ;
    }

    public String getSubject (){ // SUBJECT GETTER
        return subject;
    }

    public void setSubject (String subject){    // SUBJECT SETTER
        this.subject = subject;

    }

    public String getDate (){     // DATE GETTER
    return date;

    }

    public void setDate (String date){    //  DATE SETTER
        this.date = date ;
    }


}
