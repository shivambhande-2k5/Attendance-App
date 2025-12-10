package com.example.attendanceapp;

public class ClassData {

    private int ID;            // Private Class ID (Getter -> getID ,Setter -> setID)
    private String className;  // Private Class Name (Getter -> getClassName ,Setter -> setClassName)
    public ClassData () {}     // Default Constructor

    public ClassData(int ID, String className){ // Paramerterized Constructor
        this.ID = ID;
        this.className = className;
    }

    public int getID () {        // data read
        return ID;
    }

    public void setID(int ID) { // Data write
        this.ID = ID;
    }

    public String getClassName () {
        return className;
    }
    public void setClassName (String className) {
        this.className = className ;
    }

}
